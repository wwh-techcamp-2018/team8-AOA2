package com.aoa.springwebservice.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class DateTest {

    @Test
    public void timeStringToLocalDateTime(){

        int hour = 11;
        int minute = 0;

        LocalDateTime actualLocalDateTime = LocalDate.now().atTime(hour, minute);

        log.debug("actualLocalDateTime : {}", actualLocalDateTime);

    }

}
