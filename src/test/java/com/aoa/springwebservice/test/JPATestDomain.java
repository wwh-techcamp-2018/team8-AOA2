package com.aoa.springwebservice.test;

import javax.persistence.*;

@Entity
public class JPATestDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private boolean flag = true;

    public long getId() {
        return id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}