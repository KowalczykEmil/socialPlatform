package com.example.socialplatform.service;

import com.example.socialplatform.exception.SocialPlatformException;
import com.example.socialplatform.schema.NotificationEmail;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
class MailService {

    private final JavaMailSender mailSender;
    private final MailBuilder mailBuilder;

    @Async                                                                                          // Zdecydowanie szybciej wykonuje się żądanie wysyłki.
    void sendMail(NotificationEmail notificationEmail) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@socialplatform.pl");                                     // ToDo Zamiana później, na adres e-mail spod domeny serwisu.
            messageHelper.setTo(notificationEmail.getRecipient());
            messageHelper.setSubject(notificationEmail.getSubject());
            messageHelper.setText(notificationEmail.getBody());
        };
        try {
            mailSender.send(messagePreparator);
            log.info("Mail aktywacyjny, zostal wyslany");
        } catch (MailException e) {
            log.error("Wystapil wyjatek podczas wysylki e-maila", e);
            throw new SocialPlatformException("Wyjatek przechwycony, podczas wysylania e-maila do:  " + notificationEmail.getRecipient(), e);
        }
    }

}
