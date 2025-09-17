package vn.bachdao.commonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import vn.bachdao.commonservice.services.EmailService;

@SpringBootApplication
public class CommonserviceApplication {

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(CommonserviceApplication.class, args);

		EmailService emailService = context.getBean(EmailService.class);
		System.out.println("EmailService: " + emailService);
	}

}
