package com.bolotov.aspects;

import org.springframework.beans.factory.annotation.Aspect;
import org.springframework.beans.factory.annotation.Before;
import org.springframework.beans.factory.stereotype.Component;

@Aspect
//@Component
public class LoggingAspectImpl implements LoggingAspect {

    @Override
    @Before("sale")
    public void beforeSelling() {
        System.out.println("before selling");
    }
}
