package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import com.aoa.springwebservice.dto.UserInputDTO;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(UserInputDTO user) {
        log.debug("create : {}", user);
        return userRepository.save(user.toEntity());
    }

    public User login(UserInputDTO user){
        log.debug("login : {}", user);
        return userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new UnAuthorizedException("로그인이 필요합니다"));
    }

    public User logout(UserInputDTO user) {
        return userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new EntityNotFoundException());
    }
}
