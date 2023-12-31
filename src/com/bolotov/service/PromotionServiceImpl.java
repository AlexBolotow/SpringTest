package com.bolotov.service;

import com.bolotov.entity.Promotion;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService, BeanNameAware {
    private String beanName;

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public double countSale(Promotion promotion, double price) {
        return promotion.getPercent() > 0 ? price * promotion.getPercent() / 100. : 0;
    }
}
