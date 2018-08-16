package com.aoa.springwebservice;

import com.aoa.springwebservice.test.JPATestDomain;
import com.aoa.springwebservice.test.JPATestDomainRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SpringWebserviceApplicationTests {

    @Autowired
    JPATestDomainRepository jpaTestDomainRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void JPAEnvironmentLoad(){
        JPATestDomain domain = new JPATestDomain();
        domain.setFlag(true);
        domain = jpaTestDomainRepository.save(domain);
        log.debug("domain : {}", domain);
        assertThat(domain.isFlag()).isEqualTo(jpaTestDomainRepository.findById(domain.getId()).get().isFlag());
    }

}
