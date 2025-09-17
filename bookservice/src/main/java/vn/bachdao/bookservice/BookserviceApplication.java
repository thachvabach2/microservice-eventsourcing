package vn.bachdao.bookservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import vn.bachdao.commonservice.services.TestService;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"vn.bachdao.bookservice", "vn.bachdao.commonservice"})
public class BookserviceApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BookserviceApplication.class, args);

		TestService testService = context.getBean(TestService.class);
		System.out.println("TestService: " + testService);
	}


}
