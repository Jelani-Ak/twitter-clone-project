package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.request.SignInRequestDTO;
import com.jelaniak.twittercloneproject.dto.response.JwtResponseDTO;
import com.jelaniak.twittercloneproject.exception.confirmation.ConfirmationTokenExpiredException;
import com.jelaniak.twittercloneproject.exception.confirmation.ConfirmationTokenNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.EmailAlreadyConfirmedException;
import com.jelaniak.twittercloneproject.exception.user.EmailNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.security.jwt.JwtUtils;
import com.jelaniak.twittercloneproject.service.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.jelaniak.twittercloneproject.utils.Helper.getTimeNow;

@Slf4j
@Service
public class AuthenticationService {
    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AuthenticationService(
            JwtUtils jwtUtils,
            UserService userService,
            AuthenticationManager authenticationManager,
            ConfirmationTokenService confirmationTokenService
    ) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.confirmationTokenService = confirmationTokenService;
    }

    public JwtResponseDTO signIn(SignInRequestDTO signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getUsername(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        var response = new JwtResponseDTO(
                userDetails.getId(),
                userDetails.getEmail(),
                userDetails.getUsername(),
                jwt,
                roles
        );

        return response;
    }

    public void signOut() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(null);
    }

    public void confirmToken(String token) throws
            ConfirmationTokenNotFoundException,
            ConfirmationTokenExpiredException,
            EmailAlreadyConfirmedException,
            EmailNotFoundException {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            log.error(getTimeNow() + "Failed to confirm token. Token expired");
            throw new ConfirmationTokenExpiredException("Token expired");
        }

        try {
            confirmationTokenService.updateConfirmedAt(confirmationToken.getToken());
            enableUser(confirmationToken.getUser().getEmail());
            log.info(getTimeNow() + "Successfully confirmed token");
        } catch (Exception e) {
            log.error(getTimeNow() + "Failed to confirm token. Reason unknown");
            throw new RuntimeException(e);
        }

        enableUser(confirmationToken.getUser().getEmail());
    }

    public void deleteToken(String token) throws ConfirmationTokenNotFoundException {
        confirmationTokenService.deleteToken(token);
    }

    public void enableUser(String email) throws EmailNotFoundException {
        User user = userService.findByEmail(email);

        user.setEnabled(true);

        userService.saveUser(user);
    }
}
