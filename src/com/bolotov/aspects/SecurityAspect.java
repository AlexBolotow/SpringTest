package com.bolotov.aspects;

import org.springframework.beans.factory.annotation.Aspect;
import org.springframework.beans.factory.annotation.Before;

import java.util.logging.Logger;

@Aspect
public class SecurityAspect {

    @Before("sale")
    public void beforeSellingAdvice() {
        System.out.println("Security check...");
    }
}
