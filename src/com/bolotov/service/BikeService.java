package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Bike;
import com.bolotov.entity.Car;
import com.bolotov.entity.Promotion;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class BikeService implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Autowired
    public KitItemService kitItemService;

    public KitItemService getKitItemService() {
        return kitItemService;
    }

    public void setKitItemService(KitItemService kitItemService) {
        this.kitItemService = kitItemService;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setKit(Bike bike) {
        if (bike.getModel().equals("Merida")) {
            bike.setKit(kitItemService.setExtendedKit());
        }
    }

    public void sale(Bike bike, DB db) {
        if (db.isProductInStock(bike)) {
            setKit(bike);
            db.deleteProduct(bike);
            System.out.println("sale: " + bike);
        } else {
            System.out.println("no product");
        }
    }

}
