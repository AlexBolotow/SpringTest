package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class CarServiceImpl implements CarService, BeanFactoryAware{
    private BeanFactory beanFactory;

    @Autowired
    private PromotionService promotionService;
    private PromotionService promotionService2;
    private PromotionService promotionService3;

    public CarServiceImpl(){}

    public PromotionService getPromotionService() {
        System.out.print("Get promotionService: ");
        return promotionService;
    }

    @Autowired
    public void setPromotionService2(PromotionService promotionService2) {
        this.promotionService2 = promotionService2;
    }

    public PromotionService getPromotionService2() {
        System.out.print("Get promotionService2: ");
        return promotionService2;
    }

    @Autowired
    public CarServiceImpl(PromotionService promotionService3) {
        this.promotionService3 = promotionService3;
    }


    public PromotionService getPromotionService3() {
        System.out.print("Get promotionService3: ");
        return promotionService3;
    }


    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public double getPrice(Car car, Promotion promotion) {
        return car.getPrice() - promotionService.countSale(promotion, car.getPrice());
    }

    public void sale(Car car, Promotion promotion, DB db) {
        //для краствого output
        try {
            Thread.sleep((long)(Math.random()* 10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (db.isProductInStock(car)) {
            car.setPrice(getPrice(car, promotion));
            //getPrice(car, promotion);
            db.deleteProduct(car);
            System.out.println("sale: " + car);
        } else {
            System.out.println("no product");
        }
    }
}
