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
        beanFactory.initializeProxies();

        DB db = new DB();
        Car car = new Car(7000, "BMW", "black", 4, "V8");
        Bike bike = new Bike(2000, "Merida", "blue", 21);
        db.addProduct(car);
        db.addProduct(bike);

        Object carServiceProxy = beanFactory.getProxy("carServiceImpl");
        Object bikeServiceProxy = beanFactory.getProxy("bikeServiceImpl");

        ((CarService) carServiceProxy).sale(car, new Promotion(10), db);
        ((BikeService) bikeServiceProxy).sale(bike, db);
    }
}
