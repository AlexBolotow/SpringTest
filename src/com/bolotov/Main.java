package com.bolotov;

import com.bolotov.entity.Bike;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import com.bolotov.service.*;
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

        DB db = new DB();
        Car car1 = new Car(7000, "BMW", "black", 4, "V8");
        Car car2 = new Car(4000, "Lada", "blue", 4, "1.6");
        db.addProduct(car1);
        db.addProduct(car2);

        Object carService1 = beanFactory.getBean(CarService.class);
        Object carService2 = beanFactory.getBean(CarServiceImpl.class);


        int x = 798;



        ((CarService) carService1).sale(car1, new Promotion(10), db);
        ((CarServiceImpl) carService2).sale(car2, new Promotion(50), db);


    }
}
