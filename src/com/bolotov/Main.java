package com.bolotov;

import com.bolotov.car.CarService;
import com.bolotov.car.PromotionService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.CustomPostProcessor;

public class Main {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.bolotov.car");

        beanFactory.populateProperties();
        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        beanFactory.addPostProcessor(new CustomPostProcessor());
        beanFactory.initializeBeans();


        PromotionService promotionService = (PromotionService) beanFactory.getBean("promotionService");
        System.out.println(promotionService.getBeanName());
        CarService carService = (CarService) beanFactory.getBean("carService");
        System.out.println(carService.getBeanFactory());



    }
}
