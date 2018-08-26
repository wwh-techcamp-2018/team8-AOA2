package com.aoa.springwebservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringWebserviceApplication {

    private static final String APPLICATION_LOCATIONS =
            "spring.config.location=" +
                    "classpath:application.yml," +
                    "classpath:application.properties," +
                    "/app/config/spring-webservice/real-application.yml";

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringWebserviceApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }
}
