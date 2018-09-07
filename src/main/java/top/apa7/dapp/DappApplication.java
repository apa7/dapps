package top.apa7.dapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
//@EnableDiscoveryClient
@EnableAsync
@EnableScheduling
public class DappApplication {

    public static void main(String[] args) {
        SpringApplication.run(DappApplication.class, args);
    }
}
