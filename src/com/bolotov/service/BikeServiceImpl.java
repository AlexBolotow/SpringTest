package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Bike;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class BikeServiceImpl implements BikeService, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Autowired
    public KitItemService kitItemService;

    public KitItemServiceImpl getKitItemService() {
        return (KitItemServiceImpl) kitItemService;
    }

    public void setKitItemService(KitItemServiceImpl kitItemServiceImpl) {
        this.kitItemService = kitItemServiceImpl;
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
        //для краствого output
        try {
            Thread.sleep((long)(Math.random()* 10000));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (db.isProductInStock(bike)) {
            setKit(bike);
            db.deleteProduct(bike);
            System.out.println("sale: " + bike);
        } else {
            System.out.println("no product");
        }
    }

}
