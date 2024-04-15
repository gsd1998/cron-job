package com.diveintodev.cronjob.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MailSenderService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromMail;

    @Value("${spring.mail.toMail}")
    private String toMail;

    public String sendMail(byte[] report) throws MessagingException {

        log.info("Inside mail notification class");

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        //System.out.println(report.length);

        helper.setTo(toMail);
        helper.setFrom(fromMail);
        helper.setSubject("User Report - " + new Date().getTime());
        helper.setText("Hi Everyone,\nPlease find the attached document containing list of users\n\nThank you");

        ByteArrayResource resource = new ByteArrayResource(report);
        helper.addAttachment(new Date() + "_user.xlsx" , resource);

        javaMailSender.send(mimeMessage);
        return "mail send successfully";

    }

    //@Scheduled(cron = "0 0/2 * * * ?")
    public void scheduler1(){
        System.out.println("Scheduled 1: " + new Date().getTime());
    }

    //@Scheduled(initialDelay = 10000 , fixedRate = 60000)
    public void scheduler2(){
        System.out.println("Scheduled 2: " + new Date().getTime());
    }

}
