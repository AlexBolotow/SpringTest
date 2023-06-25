package com.bolotov.aspects;

import org.springframework.beans.factory.annotation.After;
import org.springframework.beans.factory.annotation.Around;
import org.springframework.beans.factory.annotation.Aspect;
import org.springframework.beans.factory.annotation.Before;
import org.springframework.beans.factory.stereotype.Component;

import java.util.logging.Logger;

@Aspect
public class LoggingAspectImpl implements LoggingAspect {
    public static final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Override
    @Before("sale")
    public void beforeSellingAdvice() {
        logger.info("before selling advice");
    }

    @Override
    @After("sale")
    public void afterSellingAdvice() {
        logger.info("after selling advice");
    }

    @Override
    @Around("sale")
    public void aroundSellingAdvice() {
        logger.info("around selling advice");
    }
}
