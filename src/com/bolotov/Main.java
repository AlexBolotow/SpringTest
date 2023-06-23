package com.bolotov;

import com.bolotov.entity.Bike;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import com.bolotov.service.*;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.CustomPostProcessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException,
            InstantiationException {

        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.bolotov.service");

        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        beanFactory.addPostProcessor(new CustomPostProcessor());
        beanFactory.initializeBeans();

        //PromotionService promotionService = (PromotionService) beanFactory.getBean("promotionService");
        Object carService = beanFactory.getBean(CarService.class);
        if (carService instanceof Proxy) {
            System.out.println("y");
        }


        DB db = new DB();
        Car car1 = new Car(7000, "BMW", "black", 4, "V8");
        db.addProduct(car1);
        ((CarService) carService).sale(car1, new Promotion(10), db);

        //System.out.println(carService);
        //KitItemService kitItemService = (KitItemService) beanFactory.getBean("kitItemService");
        //BikeService bikeService = (BikeService) beanFactory.getBean("bikeService");

/*        DB db = new DB();
        Car car1 = new Car(7000, "BMW", "black", 4, "V8");
        Car car2 = new Car(5000, "Lada", "grey", 4, "V8");
        Bike bike1 = new Bike(2000, "Merida", "blue", 21);
        Bike bike2 = new Bike(1000, "Stels", "red", 18);
        db.addProduct(car1);
        db.addProduct(car2);*/
        //db.addProduct(bike1);
        //db.addProduct(bike2);

  /*      db.getProducts().forEach(System.out::println);

        //carService.sale(car1, new Promotion(10), db);
        //bikeService.sale(bike1, db);
        db.getProducts().forEach(System.out::println);
        int x = 1;
        x++;*/
        //CarService carService = (CarService) beanFactory.getBean("carService");

        //System.out.println(beanFactory.getSingletons());

        /*System.out.println(((PromotionServiceImpl) carService.getPromotionService()).getBeanName());
        System.out.println(((PromotionServiceImpl) carService.getPromotionService2()).getBeanName());
        System.out.println(((PromotionServiceImpl) carService.getPromotionService3()).getBeanName());*/


    }
}
