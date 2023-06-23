package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface CarService {
    PromotionService getPromotionService();

    @Autowired
    void setPromotionService2(PromotionService promotionService2);

    PromotionService getPromotionService2();

    PromotionService getPromotionService3();

    void setBeanFactory(BeanFactory beanFactory);

    BeanFactory getBeanFactory();

    double getPrice(Car car, Promotion promotion);

    void sale(Car car, Promotion promotion, DB db);
}
