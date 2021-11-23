package com.jelaniak.twittercloneproject.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User addUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findUserById(String id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
