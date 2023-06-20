package com.bolotov.car;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class PromotionService implements BeanNameAware {
    private String beanName;

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    public String getBeanName() {
        return beanName;
    }
}
