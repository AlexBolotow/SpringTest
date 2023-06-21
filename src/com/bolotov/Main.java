package com.bolotov;

import com.bolotov.entity.Bike;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import com.bolotov.service.BikeService;
import com.bolotov.service.CarService;
import com.bolotov.service.KitItemService;
import com.bolotov.service.PromotionService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.CustomPostProcessor;

public class Main {
    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory();
        beanFactory.instantiate("com.bolotov.service");

        beanFactory.populateProperties();
        beanFactory.injectBeanNames();
        beanFactory.injectBeanFactory();
        beanFactory.addPostProcessor(new CustomPostProcessor());
        beanFactory.initializeBeans();


        PromotionService promotionService = (PromotionService) beanFactory.getBean("promotionService");
        CarService carService = (CarService) beanFactory.getBean("carService");
        KitItemService kitItemService = (KitItemService) beanFactory.getBean("kitItemService");
        BikeService bikeService = (BikeService) beanFactory.getBean("bikeService");

        DB db = new DB();
        Car car1 = new Car(7000, "BMW", "black", 4, "V8");
        Car car2 = new Car(5000, "Lada", "grey", 4, "V8");
        Bike bike1 = new Bike(2000, "Merida", "blue", 21);
        Bike bike2 = new Bike(1000, "Stels", "red", 18);
        db.addProduct(car1);
        db.addProduct(car2);
        db.addProduct(bike1);
        db.addProduct(bike2);

        db.getProducts().forEach(System.out::println);

        carService.sale(car1, new Promotion(10), db);
        bikeService.sale(bike1, db);
        db.getProducts().forEach(System.out::println);
    }
}
