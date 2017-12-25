package com.obss.Utils;

import com.obss.Model.Entities.Extras.ApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by arnold on 7/24/2017.
 * Email class makes use of JavaMailSender class to send a simple email.
 *
 */
@Service
public class EmailUtil {


    private JavaMailSenderImpl mailSender;

    public EmailUtil() {
        this.mailSender = new JavaMailSenderImpl();
        this.mailSender.setHost("smtp.gmail.com");
        this.mailSender.setPort(587);

        //set it according to your username and password
        this.mailSender.setUsername("johndoe@gmail.com");
        this.mailSender.setPassword("secretPasss123");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
    }

    @Async("threadPoolTaskExecutor")
    public void sendMail(String to, String subject, String text) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        simpleMailMessage.setFrom(mailSender.getUsername());
        MimeMessage message = mailSender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(simpleMailMessage.getFrom());
            helper.setTo(simpleMailMessage.getTo());
            helper.setSubject(simpleMailMessage.getSubject());
            helper.setText(simpleMailMessage.getText());


            //Attachments
            FileSystemResource CV = new FileSystemResource("C:\\Users\\arnold\\Downloads\\CV.pdf");
            //FileSystemResource CoverLetter = new FileSystemResource("C:\Users\arnold\Downloads\Cover.pdf");

            helper.addAttachment("CV.pdf", CV);
            //helper.addAttachment("Cover Letter",CoverLetter);


        }catch (Exception e) {
            e.printStackTrace();
        }
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

