package com.aoa.springwebservice.support;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateTest {

    @Test
    public void timeStringToLocalDateTime(){

        int hour = 11;
        int minute = 0;

        LocalDateTime actualLocalDateTime = LocalDate.now().atTime(hour, minute);

        log.debug("actualLocalDateTime : {}", actualLocalDateTime);

    }
    @Test
    public void timeRoundTo30Mins(){
        DateTimeFormatter defualtFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDateTime test1 = LocalDateTime.of(LocalDate.now(), LocalTime.now());
        LocalTime test2 = LocalTime.parse("05:14", defualtFormatter);
        LocalTime test3 = LocalTime.parse("05:49", defualtFormatter);

//        LocalTime converted = LocalTime.of(test1.getHour(), ((test1.getMinute()/ 30)+1) * 30);
//        log.debug("converted {} ", converted);
//
//        converted = LocalTime.of(test1.getHour(), ((test2.getMinute()/ 30)) * 30).plusMinutes(30);
//        log.debug("converted {} ", converted);
//
//        converted = LocalTime.of(test1.getHour(), ((test3.getMinute()/ 30)) * 30).plusMinutes(30);
//        log.debug("converted {} ", converted);
//
//        converted = LocalTime.of(test1.getHour(), 0).plusMinutes((test1.getMinute()%30 + 1) * 30).withSecond(0).withNano(0);
//        log.debug("converted {} ", converted);
    }

}
