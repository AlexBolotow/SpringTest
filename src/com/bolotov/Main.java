package com.bolotov;

import com.bolotov.entity.Car;
import com.bolotov.service.CarService;
import com.bolotov.service.PromotionService;
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


        /*PromotionService promotionService = (PromotionService) beanFactory.getBean("promotionService");
        System.out.println(promotionService.getBeanName());
        CarService carService = (CarService) beanFactory.getBean("carService");
        System.out.println(carService.getBeanFactory());*/

        DB db = new DB();
        db.addProduct(new Car(7000, "BMW", "black", 4, "V8"));
        db.addProduct(new Car(5000, "BMW", "black", 4, "V8"));
        db.addProduct(new Car(1000, "BMW", "black", 4, "V8"));
        db.getProducts().forEach(x -> System.out.println((Car) x));
    }
}
