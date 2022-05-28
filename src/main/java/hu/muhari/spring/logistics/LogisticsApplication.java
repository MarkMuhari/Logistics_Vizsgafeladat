package hu.muhari.spring.logistics;

import hu.muhari.spring.logistics.config.LogisticsConfigProperties;
import hu.muhari.spring.logistics.service.InitDbService;
import hu.muhari.spring.logistics.service.TransportPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogisticsApplication  implements CommandLineRunner {

    @Autowired
    InitDbService initDbService;

    @Autowired
    LogisticsConfigProperties config;

    @Autowired
    TransportPlanService service;

    public static void main(String[] args) {
        SpringApplication.run(LogisticsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        initDbService.init();
    }
}
