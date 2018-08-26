package com.aoa.springwebservice.web;

import com.aoa.springwebservice.RestResponse;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import com.aoa.springwebservice.dto.UserInputDTO;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.security.HttpSessionUtils;
import com.aoa.springwebservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.net.URI;


@Slf4j
@RestController
@RequestMapping("/api/users")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public RestResponse create(@RequestBody UserInputDTO user, HttpSession session) {
        log.debug("userInputDTO : {}", user);
        HttpSessionUtils.setUserSession(session, userService.create(user));

        return RestResponse.ofRedirectResponse("/admin", "");

    }

    @PostMapping("/signin")
    public RestResponse login(@RequestBody User user, HttpSession session) {
        log.debug("user : {}", user);
        HttpSessionUtils.setUserSession(session, userService.login(user));

        return RestResponse.ofRedirectResponse("/admin", "");
    }

    @PostMapping("/signout")
    public RestResponse logout(@RequestBody User user, HttpSession session) {
        log.debug("user : {}", user);
        userService.logout(user);
        HttpSessionUtils.removeUserSession(session);

        return RestResponse.ofRedirectResponse("/", "");
    }

}
