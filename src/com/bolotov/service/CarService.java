package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Car;
import com.bolotov.entity.Product;
import com.bolotov.entity.Promotion;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class CarService implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Autowired
    public PromotionService promotionService;

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
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
