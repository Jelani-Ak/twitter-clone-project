package com.jelaniak.twittercloneproject.service;

import com.jelaniak.twittercloneproject.exception.SpringTwitterException;
import com.jelaniak.twittercloneproject.model.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Slf4j
@Service
@AllArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final MailContentService mailContentService;

    public void sendMail(NotificationEmail notificationEmail) throws SpringTwitterException {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setFrom("springtwitter@email.com");
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(mailContentService.build(notificationEmail.getBody()));
        };

        try {
            javaMailSender.send(mimeMessagePreparator);
            log.info("Activation email sent!");
        } catch (MailException e) {
            throw new SpringTwitterException("Exception occured when sending email to "
                    + notificationEmail.getRecipient());
        }
    }
}
