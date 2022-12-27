package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.dto.request.SignUpRequestDTO;
import com.jelaniak.twittercloneproject.email.EmailSender;
import com.jelaniak.twittercloneproject.exception.user.EmailAlreadyExistsException;
import com.jelaniak.twittercloneproject.exception.user.EmailNotFoundException;
import com.jelaniak.twittercloneproject.exception.user.UserAlreadyExistsException;
import com.jelaniak.twittercloneproject.exception.user.UserNotFoundException;
import com.jelaniak.twittercloneproject.model.ConfirmationToken;
import com.jelaniak.twittercloneproject.model.Role;
import com.jelaniak.twittercloneproject.model.RoleType;
import com.jelaniak.twittercloneproject.model.User;
import com.jelaniak.twittercloneproject.repository.RoleRepository;
import com.jelaniak.twittercloneproject.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.jelaniak.twittercloneproject.utils.Helper.getTimeNow;

@Slf4j
@Service
public class UserService {
    private final EmailSender emailSender;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public UserService(
            EmailSender emailSender,
            RoleRepository roleRepository,
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder,
            ConfirmationTokenService confirmationTokenService) {
        this.emailSender = emailSender;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public User findByUserId(ObjectId userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User by Id: [" + userId + "] was not found"));

        return user;
    }

    public User findByEmail(String email) throws EmailNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("User by Id: [" + email + "] was not found"));

        return user;
    }

    public void signUp(SignUpRequestDTO registerRequest) throws UserAlreadyExistsException, EmailAlreadyExistsException {
        log.info(getTimeNow() + "Creating new user, '" + registerRequest.getUsername() + "'..");

        checkUsernameExists(registerRequest);
        checkEmailExists(registerRequest);

        User user = createUser(registerRequest);

        ConfirmationToken token = createToken(user);

        log.info(getTimeNow() + "Sending confirmation E-mail");
        sendConfirmationToken(user, token);

        log.info(getTimeNow() + "User created successfully");
    }

    private void sendConfirmationToken(User user, ConfirmationToken token) {
        String link = "http://localhost:8080/api/v1/authentication/confirm?token=" + token;
        emailSender.send(user.getEmail(), buildEmail(user.getUsername(), link));
        userRepository.save(user);
    }

    private ConfirmationToken createToken(User user) {
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        confirmationToken.setConfirmedAt(null);
        confirmationToken.setUser(user);

        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return confirmationToken;
    }

    private User createUser(SignUpRequestDTO signUpRequest) {
        User user = new User();

        user.setUserId(new ObjectId());
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(signUpRequest.getPassword()));
        user.setEmail(signUpRequest.getEmail());
        user.setUserHandleName("@" + signUpRequest.getUsername()); // TODO: 26/12/2022 - Remove the @ symbol from all set requests
        user.setRoles(assignRoles(signUpRequest.getRoles()));
        user.setDateOfCreation(LocalDateTime.now());
        user.setUsersYouFollow(new HashSet<>());
        user.setUsersFollowingYou(new HashSet<>());
        user.setMutualFollowers(new HashSet<>());
        user.setTweets(new HashSet<>());
        user.setLikedTweets(new HashSet<>());
        user.setComments(new HashSet<>());
        user.setLikedComments(new HashSet<>());
        user.setUsersYouFollowCount(0);
        user.setUsersFollowingYouCount(0);
        user.setMutualFollowersCount(0);
        user.setTweetCount(0);
        user.setCommentCount(0);
        user.setFollowing(false);
        user.setVerified(false);
        user.setLocked(false);
        user.setEnabled(false);

        return userRepository.save(user);
    }

    private Set<Role> assignRoles(Set<String> roles) {
        Set<Role> rolesToAssign = new HashSet<>();

        if (roles == null) {
            Role userRole = roleRepository.findByRole(RoleType.User)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            rolesToAssign.add(userRole);
        } else {
            roles.forEach(role -> {
                String errorMessage = "Error: '" + role + "'is not found.";

                switch (role) {
                    case "Admin" -> {
                        Role adminRole = roleRepository.findByRole(RoleType.Admin)
                                .orElseThrow(() -> new RuntimeException(errorMessage));
                        rolesToAssign.add(adminRole);
                    }
                    case "Moderator" -> {
                        Role moderatorRole = roleRepository.findByRole(RoleType.Moderator)
                                .orElseThrow(() -> new RuntimeException(errorMessage));
                        rolesToAssign.add(moderatorRole);
                    }
                    default -> {
                        Role userRole = roleRepository.findByRole(RoleType.User)
                                .orElseThrow(() -> new RuntimeException(errorMessage));
                        rolesToAssign.add(userRole);
                    }
                }
            });
        }

        return rolesToAssign;
    }

    private void checkUsernameExists(SignUpRequestDTO registerRequest) throws UserAlreadyExistsException {
        boolean userExists = userRepository.existsByUsername(registerRequest.getUsername());

        if (userExists) {
            String errorMessage = getTimeNow() + "Failed to create user. Username, '" + registerRequest.getUsername() + "' is already taken";
            log.error(errorMessage);
            throw new UserAlreadyExistsException(errorMessage);
        }
    }

    private void checkEmailExists(SignUpRequestDTO registerRequest) throws EmailAlreadyExistsException {
        boolean emailExists = userRepository.existsByEmail(registerRequest.getEmail());

        if (emailExists) {
            String errorMessage = "Failed to create user. Email, '" + registerRequest.getEmail() + "' is already taken";
            log.error(errorMessage);
            throw new EmailAlreadyExistsException(errorMessage);
        }
    }

    // TODO: 03/12/2022 - To be remade and put in an HTML file
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

    public void deleteByUserId(ObjectId userId) {
        userRepository.deleteByUserId(userId);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public void saveAllUsers(List<User> users) {
        userRepository.saveAll(users);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
