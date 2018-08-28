package com.aoa.springwebservice.web;

import com.aoa.springwebservice.RestResponse;
import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.UserInputDTO;
import com.aoa.springwebservice.security.HttpSessionUtils;
import com.aoa.springwebservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;


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
