package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.*;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.model.UserRole;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username, '" + username + "' not found"));
    }

    public User createUser(User user) throws UserAlreadyExistsException {
        boolean userExists = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExists) {
            throw new UserAlreadyExistsException("Username, '" + user.getUsername() + "' already taken");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());

        user.setUserId(user.getUserId());
        user.setUsername(user.getUsername());
        user.setPassword(encodedPassword);
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getUsername());
        user.setUserHandleName("@" + user.getUsername());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioAboutText(user.getBioAboutText());
        user.setUserRole(UserRole.USER);
        user.setDateOfCreation(LocalDateTime.now());
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        user.setUsersYouFollow(new HashSet<>());
        user.setUsersFollowingYou(new HashSet<>());
        user.setMutualFollowers(new HashSet<>());
        user.setTweets(new HashSet<>());
        user.setTweetCount(0);
        user.setTweetQuoteCount(0);
        user.setFollowing(false);
        user.setVerified(false);
        user.setLocked(false);
        user.setEnabled(false);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setUser(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return userRepository.save(user);
    }

    public User logUserIn(User user) throws BadCredentialsException {
        User tempUser;

        String tempEmail = user.getEmail();
        String tempUsername = user.getUsername();
        String tempPassword = user.getPassword();

        if (tempEmail != null && tempPassword != null) {
            tempUser = userRepository.findByEmailAndPassword(tempEmail, tempPassword);
            return tempUser;
        }

        if (tempUsername != null && tempPassword != null) {
            tempUser = userRepository.findByUsernameAndPassword(tempUsername, tempPassword);
            return tempUser;
        }

        throw new BadCredentialsException("Failed to login. Bad credentials");
    }

    @Transactional
    public String confirmToken(ObjectId tokenId) throws ConfirmationTokenNotFoundException, EmailAlreadyConfirmedException, ConfirmationTokenExpiredException {
        ConfirmationToken confirmationToken = confirmationTokenService.findConfirmationToken(tokenId);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new EmailAlreadyConfirmedException("Email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new ConfirmationTokenExpiredException("Token expired");
        }

        confirmationTokenService.setConfirmedAt(tokenId);

        return "Email confirmed";
    }
}
