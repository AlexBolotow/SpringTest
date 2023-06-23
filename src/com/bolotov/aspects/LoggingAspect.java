package com.bolotov.aspects;

import org.springframework.beans.factory.annotation.Before;

public interface LoggingAspect {

    @Before("sale")
    void beforeSelling();
}
