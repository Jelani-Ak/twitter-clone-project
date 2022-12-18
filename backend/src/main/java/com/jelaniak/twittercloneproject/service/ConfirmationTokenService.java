package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.comment.ConfirmationTokenNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken getToken(String token) throws ConfirmationTokenNotFoundException {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Confirmation Token not found"));
    }

    public void updateConfirmedAt(String token) throws ConfirmationTokenNotFoundException {
        ConfirmationToken foundToken = getToken(token);
        foundToken.setConfirmedAt(LocalDateTime.now());

        confirmationTokenRepository.save(foundToken);
    }

    public void deleteToken(String token) throws ConfirmationTokenNotFoundException {
        ConfirmationToken foundToken = getToken(token);

        confirmationTokenRepository.delete(foundToken);
    }
}
