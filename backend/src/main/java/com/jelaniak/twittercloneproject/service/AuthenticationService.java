package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.RegisterRequest;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.model.VerificationToken;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    public void signup(RegisterRequest registerRequest) {
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setCreatedDate(user.getCreatedDate());
        user.setEnabled(false);
        userRepository.save(user);
    }

    private void generateVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();

    }
}
