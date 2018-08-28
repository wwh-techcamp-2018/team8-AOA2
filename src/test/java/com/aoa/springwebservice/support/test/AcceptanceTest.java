package com.aoa.springwebservice.support.test;

import com.aoa.springwebservice.domain.User;
import com.aoa.springwebservice.domain.UserRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final String DEFAULT_LOGIN_USER_ID = "905405911";
    private static final String DEFAULT_LOGIN_USER_PW = "NONE";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;


    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }

    public TestRestTemplate basicAuthTemplate(User loginUser) {
        return template.withBasicAuth(loginUser.getUuid(), DEFAULT_LOGIN_USER_PW);
    }

    protected User defaultUser() {
        return findByUuid(DEFAULT_LOGIN_USER_ID);
    }

    protected User findByUuid(String uuid) {
        return userRepository.findByUuid(uuid).get();
    }
}