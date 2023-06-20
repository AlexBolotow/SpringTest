package com.bolotov;

import com.bolotov.car.CarService;
import com.bolotov.car.PromotionService;
import org.springframework.beans.factory.BeanFactory;

public class Main {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.bolotov.car");
        CarService carService = (CarService) beanFactory.getBean("carService");
        PromotionService promotionService = (PromotionService) beanFactory.getBean("promotionService");
        System.out.println(carService);
        System.out.println(promotionService);
    }
}
