package com.aoa.springwebservice.service;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(User user) {
        return userRepository.save(user);
    }

    public User login(User user) {
        return userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new UnAuthorizedException("로그인이 필요합니다"));
    }

    public User logout(User user) {
        return userRepository.findByUserId(user.getUserId()).orElseThrow(() -> new EntityNotFoundException());
    }
}
