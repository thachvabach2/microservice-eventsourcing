package vn.bachdao.borrowingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import vn.bachdao.commonservice.services.EmailService;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"vn.bachdao.borrowingservice", "vn.bachdao.commonservice"})
public class BorrowingserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BorrowingserviceApplication.class, args);
    }
}