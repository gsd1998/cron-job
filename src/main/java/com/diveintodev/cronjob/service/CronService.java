package com.diveintodev.cronjob.service;

import com.diveintodev.cronjob.mail.MailSenderService;
import com.diveintodev.cronjob.report.ReportService;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@Slf4j
public class CronService {

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private ReportService reportService;

    //@Scheduled(cron = "0 0/2 * * * ?")
    public void generateReportAndSendMail() {

        String output = "";
        log.info("Generating report and sending mail");

        byte[] report = null;

        try {
            report = reportService.generateReport();
        }catch (IOException e) {
            log.error("Failed to generate report");
        }
        try {
            if(report != null && report.length > 0) {
                output = mailSenderService.sendMail(report);
            }
        } catch (MessagingException e){
            log.error("Failed to send mail");
        }
        log.info("report generation and mail sending successfully completed");
        //return output;
    }

}
