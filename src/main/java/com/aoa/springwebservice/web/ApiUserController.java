package com.aoa.springwebservice.web;

import com.aoa.springwebservice.RestResponse;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.security.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/users")
public class ApiUserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public RestResponse create(@RequestBody User user, HttpSession session) {
        log.debug("user : {}", user);
        User loginUser = userRepository.save(user);
        HttpSessionUtils.setUserSession(session, loginUser);

        return new RestResponse("/result/success");

    }

    @PostMapping("/login")
    public RestResponse login(@RequestBody User user, HttpSession session) {
        log.debug("user : {}", user);
        User loginUser = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new UnAuthorizedException("로그인이 필요합니다"));
        HttpSessionUtils.setUserSession(session, loginUser);

        return new RestResponse("/result/success");
    }

}
