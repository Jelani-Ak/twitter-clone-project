package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.RegisterRequestDTO;
import com.jelaniak.twittercloneproject.email.EmailSender;
import com.jelaniak.twittercloneproject.exception.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.exception.UserIdNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.model.UserRole;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final EmailSender emailSender;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserService(
            EmailSender emailSender,
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ConfirmationTokenService confirmationTokenService
    ) {
        this.emailSender = emailSender;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username, '" + username + "' not found"));
    }

    public User findByUserId(ObjectId userId) throws UserIdNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserIdNotFoundException("Id of " + userId + " was not found"));
    }

    public void register(RegisterRequestDTO registerRequest) throws UserAlreadyExistsException {
        LOGGER.info("Creating new user, '" + registerRequest.getUsername() + "'");
        boolean userExists = userRepository.existsByUsername(registerRequest.getUsername());

        if (userExists) {
            String errorMessage = "Failed to create user. Username, '" + registerRequest.getUsername() + "' already taken";
            LOGGER.error(errorMessage);
            throw new UserAlreadyExistsException(errorMessage);
        }

        User user = new User();

        user.setUserId(new ObjectId());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
//        user.setDisplayName(user.getUsername());
//        user.setUserHandleName("@" + user.getUsername());
//        user.setBioLocation(user.getBioLocation());
//        user.setBioExternalLink(user.getBioExternalLink());
//        user.setBioAboutText(user.getBioAboutText());
        user.setUserRole(UserRole.USER);
        user.setDateOfCreation(LocalDateTime.now());
//        user.setPictureAvatarUrl(user.getPictureAvatarUrl());
//        user.setPictureBackgroundUrl(user.getPictureBackgroundUrl());
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

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setConfirmedAt(null);
        confirmationToken.setUser(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);

        LOGGER.info("Sending confirmation E-mail");
        String link = "http://localhost:8080/api/v1/authentication/confirm?token=" + token;
        emailSender.send(user.getEmail(), buildEmail(user.getUsername(), link));
        userRepository.save(user);
        LOGGER.info("User created successfully");
    }

    // TODO: 03/12/2022 - To be remade and put in an html file
    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
