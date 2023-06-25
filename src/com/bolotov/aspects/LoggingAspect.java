package com.bolotov.aspects;

import org.springframework.beans.factory.annotation.After;
import org.springframework.beans.factory.annotation.Around;
import org.springframework.beans.factory.annotation.Before;

public interface LoggingAspect {

    @Before("sale")
    void beforeSellingAdvice();

    @After("sale")
    void afterSellingAdvice();

    @Around("sale")
    void aroundSellingAdvice();
}
