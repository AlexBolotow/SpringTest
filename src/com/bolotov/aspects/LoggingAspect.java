package com.bolotov.aspects;

import org.springframework.beans.factory.annotation.After;
import org.springframework.beans.factory.annotation.Around;
import org.springframework.beans.factory.annotation.Aspect;
import org.springframework.beans.factory.annotation.Before;

import java.util.logging.Logger;

@Aspect
public class LoggingAspect {
    public static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Before("sale")
    public void beforeSellingAdvice() {
        logger.info("before selling advice");
    }

    @After("sale")
    public void afterSellingAdvice() {
        logger.info("after selling advice");
    }

    @Around("sale")
    public void aroundSellingAdvice() {
        logger.info("around selling advice");
    }
}
