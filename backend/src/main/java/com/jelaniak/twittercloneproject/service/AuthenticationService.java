package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.LoginRequestDTO;
import com.jelaniak.twittercloneproject.exception.*;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public AuthenticationService(
            UserRepository userRepository,
            ConfirmationTokenService confirmationTokenService
    ) {
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
    }

    public User logUserIn(LoginRequestDTO loginRequest) throws BadCredentialsException {
        final User tempUser;
        final String regexPattern = "^(.+)@(\\S+)$";
        final boolean loginByEmail = loginRequest.getUsername().matches(regexPattern);

        String errorMessage = "Failed to login. Bad credentials";
        if (loginByEmail) {
            boolean existsByEmail = userRepository.existsByEmail(loginRequest.getUsername());
            if (!existsByEmail) {
                throw new BadCredentialsException(errorMessage);
            }
        }

        boolean existsByUsername = userRepository.existsByUsername(loginRequest.getUsername());
        if (!existsByUsername) {
            throw new BadCredentialsException(errorMessage);
        }

        final String tempUsername = loginRequest.getUsername();
        final String tempPassword = loginRequest.getPassword();

        if (loginByEmail) {
            tempUser = userRepository.findByEmailAndPassword(tempUsername, tempPassword);
            return tempUser;
        }

        tempUser = userRepository.findByUsernameAndPassword(tempUsername, tempPassword);
        return tempUser;
    }

    public void confirmToken(String token) throws ConfirmationTokenNotFoundException, EmailAlreadyConfirmedException, ConfirmationTokenExpiredException, EmailNotFoundException {
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
