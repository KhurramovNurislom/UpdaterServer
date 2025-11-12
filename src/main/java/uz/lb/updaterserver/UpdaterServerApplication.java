package uz.lb.updaterserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UpdaterServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpdaterServerApplication.class, args);
        System.out.println("Assalom aleykum... UpdaterServer running... ");
    }
}
