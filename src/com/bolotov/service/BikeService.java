package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Bike;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class BikeService implements BeanFactoryAware {
    private BeanFactory beanFactory;

    @Autowired
    public KitItemServiceImpl kitItemServiceImpl;

    public KitItemServiceImpl getKitItemService() {
        return kitItemServiceImpl;
    }

    public void setKitItemService(KitItemServiceImpl kitItemServiceImpl) {
        this.kitItemServiceImpl = kitItemServiceImpl;
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
            bike.setKit(kitItemServiceImpl.setExtendedKit());
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
