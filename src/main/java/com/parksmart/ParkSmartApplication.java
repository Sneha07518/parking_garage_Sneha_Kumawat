package com.parksmart;

import org.springframework.boot.SpringApplication; import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication @EnableScheduling
public class ParkSmartApplication {
    public static void main(String[] args) {
        SpringApplication.run(ParkSmartApplication.class, args);
    }
}
