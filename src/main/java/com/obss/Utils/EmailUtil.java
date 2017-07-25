package com.obss.Utils;

import com.obss.Model.Entities.Extras.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


import java.util.Properties;

/**
 * Created by arnold on 7/24/2017.
 * Email calss makes use of JavaMailSender class to send a simple email.
 *
 */
@Service
public class EmailUtil {


    private JavaMailSenderImpl mailSender;

    public EmailUtil() {
        this.mailSender = new JavaMailSenderImpl();
        this.mailSender.setHost("smtp.gmail.com");
        this.mailSender.setPort(587);
        this.mailSender.setUsername("asllani94@gmail.com");
        this.mailSender.setPassword("lowhfzzzzyvwhquq");
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    @Async("threadPoolTaskExecutor")
    private void sendMail(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void notifyStatusChange(String emailTo, int status, int adCode) {
        String message = "";
        String subject = "Application status notification";

        switch (status) {
            case ApplicationStatus.ON_PROCESS:
                message = "Application made to advert no:" + adCode + " is currently on pending";
                break;
            case ApplicationStatus.ACCEPTED:
                message = "Application made to advert no:" + adCode + " was successfully accepted by HR expert";
                break;

            case ApplicationStatus.REJECTED:
                message = "Application made to advert no:" + adCode + " was rejected by HR expert";
                break;
        }

        this.sendMail(emailTo, subject, message);
    }

    public void notifyCandidateBlacklisted(String emailTo, String reason) {
        String message = "You have been blacklisted by HR department for the following reason: \""
                + reason + "\". All your applications have been rejected.";
        String subject = "Blacklisted";

        this.sendMail(emailTo, subject, message);
    }
}
