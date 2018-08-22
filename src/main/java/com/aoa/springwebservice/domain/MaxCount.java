package com.aoa.springwebservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter @NoArgsConstructor
public class MaxCount {

    private Integer maxCount;

    private Integer personalMaxCount;

    public MaxCount(int maxCount, int personalMaxCount) {
        if (maxCount < personalMaxCount) {
            throw new IllegalArgumentException("illegal maxCount & personalMaxCount");
        }
        this.maxCount = maxCount;
        this.personalMaxCount = maxCount;
    }
}
