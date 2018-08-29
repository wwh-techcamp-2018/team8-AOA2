package com.aoa.springwebservice.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizedStore {
    boolean required() default true;

    boolean notOpen() default false;

    boolean notClosed() default false;
}
