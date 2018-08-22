package com.aoa.springwebservice.domain;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;

public class MaxCountTest {

    SoftAssertions softly;

    @Before
    public void setUp(){
        softly = new SoftAssertions();
    }

    @Test
    public void construct_MaxCount(){
        int maxCount = 10;

        softly.assertThatThrownBy(() -> new MaxCount(maxCount, maxCount + 1))
                .as("maxCount < personalMAxCount")
                .isInstanceOf(IllegalArgumentException.class);

        softly.assertThatThrownBy(() -> new MaxCount(maxCount, 0))
                .as("personalCount < 1")
                .isInstanceOf(IllegalArgumentException.class);

        softly.assertAll();
    }
}
