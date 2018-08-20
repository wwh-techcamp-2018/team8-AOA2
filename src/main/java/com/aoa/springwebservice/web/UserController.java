package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.security.HttpSessionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public String create(@RequestBody User user) {
        log.debug("user : {}", user);
        userRepository.save(user);
        return "redirect:/result/success";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user, HttpSession session) {
        log.debug("user : {}", user);
        User loginUser = userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new UnAuthorizedException("로그인이 필요합니다"));
        HttpSessionUtils.setUserSession(session, loginUser);
        return "redirect:/result/success";
    }
}
