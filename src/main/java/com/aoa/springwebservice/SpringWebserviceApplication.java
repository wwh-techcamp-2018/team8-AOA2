package com.aoa.springwebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringWebserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringWebserviceApplication.class, args);
    }
}
