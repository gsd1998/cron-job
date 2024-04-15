package com.diveintodev.cronjob.controller;

import com.diveintodev.cronjob.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CronController {

    /*
    @Autowired
    private CronService cronService;

    @PostMapping("/send")
    public String sendMail() {
        return cronService.generateReportAndSendMail();
    }
    */

}
