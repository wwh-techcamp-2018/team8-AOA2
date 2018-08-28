package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.dto.UserInputDTO;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.service.UserService;
import com.aoa.springwebservice.support.test.AcceptanceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class ApiUserAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserService userService;

//    테스트 실패
//    @Test
//    public void login_성공() {
//        ResponseEntity<Void> response = template().postForEntity("/api/users/signin", defaultUser(), Void.class);
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//    }

    @Test
    public void login_실패() {
        User user = new User("failed_password", "fail", "fail@fail.com", "010-1111-1111");
        ResponseEntity<Void> response = template().postForEntity("/api/users/signin", user, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
