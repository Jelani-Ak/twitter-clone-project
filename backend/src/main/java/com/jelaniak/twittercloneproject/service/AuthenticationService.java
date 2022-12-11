package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.request.LoginRequestDTO;
import com.jelaniak.twittercloneproject.dto.response.JwtResponseDTO;
import com.jelaniak.twittercloneproject.exception.*;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import com.jelaniak.twittercloneproject.security.jwt.JwtUtils;
import com.jelaniak.twittercloneproject.service.security.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AuthenticationService(
            JwtUtils jwtUtils,
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            ConfirmationTokenService confirmationTokenService
    ) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.confirmationTokenService = confirmationTokenService;
    }

    public JwtResponseDTO logUserIn(LoginRequestDTO loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();

        var response = new JwtResponseDTO(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );

        return response;
    }

    public void confirmToken(String token)
            throws ConfirmationTokenNotFoundException,
                    EmailAlreadyConfirmedException,
                    ConfirmationTokenExpiredException,
                    EmailNotFoundException {
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            LOGGER.error("Failed to confirm token. Token expired");
            throw new ConfirmationTokenExpiredException("Token expired");
        }

        try {
            confirmationTokenService.updateConfirmedAt(confirmationToken.getToken());
            enableUser(confirmationToken.getUser().getEmail());
            LOGGER.info("Successfully confirmed token");
        } catch (Exception e) {
            LOGGER.error("Failed to confirm token. Reason unknown");
            throw new RuntimeException(e);
        }

        enableUser(confirmationToken.getUser().getEmail());
    }

    public void enableUser(String email) throws EmailNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("User with username, " + email + "' not found"));

        user.setEnabled(true);

        userRepository.save(user);
    }
}
