package com.aoa.springwebservice.web;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@AllArgsConstructor
public class DeploymentConfigurationApi {

    private Environment environment;

    @GetMapping("/profile")
    public String getProfile(){
        return Arrays.stream(environment.getActiveProfiles())
                .findFirst()
                .orElse("default profile");
    }
}
