package com.aoa.springwebservice.web;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.exception.UnAuthorizedException;
import com.aoa.springwebservice.service.UserService;
import com.aoa.springwebservice.support.test.AcceptanceTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;


@Slf4j
public class ApiUserAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserService userService;

    @Test
    public void login_성공() {
        User user = userService.login(defaultUser());
        ResponseEntity<Void> response = template().postForEntity("/api/users/login", user, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test(expected = UnAuthorizedException.class)
    public void login_실패() {
        User user = userService.login(new User("failed_password", "fail", "fail@fail.com", "010-1111-1111"));
        ResponseEntity<Void> response = template().postForEntity("/api/users/login", user, Void.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
