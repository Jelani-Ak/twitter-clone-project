package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.RegisterRequest;
import com.jelaniak.twittercloneproject.model.NotificationEmail;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.model.VerificationToken;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import com.jelaniak.twittercloneproject.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private MailService mailService;

    public void signUp(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(user.getEmail());
        user.setCreatedDate(user.getCreatedDate());
        user.setEnabled(false);

        userRepository.save(user);

        String token = generateVerificationToken(user);
        mailService.sendMail(new NotificationEmail("Please Activate your Account.",
                user.getEmail(), "Thank you for signing up to Spring Twitter!, " +
                "Please click on the below url to activate your account : " +
                "http://localhost:8080/api/auth/accountVerification/" + token));
    }

    private String generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);

        verificationTokenRepository.save(verificationToken);
        return token;
    }
}
