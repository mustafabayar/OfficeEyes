package com.mbcoder.officeeyes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OfficeEyesApplication {

    public static void main(String[] args) {
        SpringApplication.run(OfficeEyesApplication.class, args);
    }

}
