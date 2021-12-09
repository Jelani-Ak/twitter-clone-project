package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.Role;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.RoleRepository;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User createUserDebug(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setUserHandle(user.getUserHandle());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioText(user.getBioText());
        user.setCreatedDate(user.getCreatedDate());
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        user.setFollow(user.isFollow());
        user.setFollowing(user.getFollowing());
        user.setFollowers(user.getFollowers());
        user.setFollowersMutual(user.getFollowersMutual());
        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweetCount());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
        user.setFollow(user.isFollow());
        user.setEnabled(user.isEnabled());
        return userRepository.save(user);
    }

    public User createUser(User user) {
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCreatedDate(user.getCreatedDate());
        user.setFollow(false);
        user.setEnabled(false);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User by id: [" + id + "] was not found."));
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User updateUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEmail(user.getEmail());
        user.setDisplayName(user.getDisplayName());
        user.setUserHandle(user.getUserHandle());
        user.setBioLocation(user.getBioLocation());
        user.setBioExternalLink(user.getBioExternalLink());
        user.setBioText(user.getBioText());
        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
        return userRepository.save(user);
    }

    public void followUser(User user) {
        user.setFollow(user.isFollow());
        userRepository.save(user);
    }

    public void getFollowData(User user) {
        user.setFollowing(user.getFollowing());
        user.setFollowers(user.getFollowers());
        user.setFollowersMutual(user.getFollowersMutual());
    }

    public void getTweetData(User user) {
        user.setTweets(user.getTweets());
        user.setTweetCount(user.getTweetCount());
        user.setTweetQuoteCount(user.getTweetQuoteCount());
    }

    public void validateUser(User user) {
        user.setEnabled(user.isEnabled());
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findByEmail(email);
        if (user != null) {
            List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
            return buildUserForAuthentication(user, authorities);
        } else {
            throw new UsernameNotFoundException("username not found");
        }
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<>();
        userRoles.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority(role.getRole().toString()));
        });

        return new ArrayList<>(roles);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
