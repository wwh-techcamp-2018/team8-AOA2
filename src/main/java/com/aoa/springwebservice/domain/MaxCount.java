package com.aoa.springwebservice.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
@ToString
public class MaxCount {

    private Integer maxCount;

    private Integer personalMaxCount;

    public MaxCount(int maxCount, int personalMaxCount) {
        if (personalMaxCount < 1 || maxCount < personalMaxCount) {
            throw new IllegalArgumentException("illegal maxCount & personalMaxCount");
        }
        this.maxCount = maxCount;
        this.personalMaxCount = personalMaxCount;
    }
}
