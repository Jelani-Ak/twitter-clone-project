package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.ConfirmationTokenNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.repository.ConfirmationTokenRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenService {

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public ConfirmationToken findConfirmationToken(ObjectId confirmationTokenId) throws ConfirmationTokenNotFoundException {
        return confirmationTokenRepository.findById(confirmationTokenId)
                .orElseThrow(() -> new ConfirmationTokenNotFoundException("Confirmation Token not found"));
    }

    public int setConfirmedAt(ObjectId token) {
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
