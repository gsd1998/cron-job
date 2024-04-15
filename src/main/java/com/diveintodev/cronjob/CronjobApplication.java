package com.diveintodev.cronjob;

import com.diveintodev.cronjob.service.CronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CronjobApplication implements CommandLineRunner {

	@Autowired
	private CronService cronService;

	public static void main(String[] args) {
		SpringApplication.run(CronjobApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		cronService.generateReportAndSendMail();
	}
}
