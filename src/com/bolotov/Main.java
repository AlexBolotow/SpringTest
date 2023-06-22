package com.bolotov;

import com.bolotov.service.CarService;
import com.bolotov.service.PromotionServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.CustomPostProcessor;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException,
            InstantiationException {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.bolotov.service");

        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        beanFactory.addPostProcessor(new CustomPostProcessor());
        beanFactory.initializeBeans();

        CarService carService = (CarService) beanFactory.getBean("carService");

        System.out.println(beanFactory.getSingletons());

        System.out.println(((PromotionServiceImpl) carService.getPromotionService()).getBeanName());
        System.out.println(((PromotionServiceImpl) carService.getPromotionService2()).getBeanName());
        System.out.println(((PromotionServiceImpl) carService.getPromotionService3()).getBeanName());
    }
}
