package com.bolotov.service;

import com.bolotov.DB;
import com.bolotov.entity.Bike;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

public interface BikeService {
    KitItemServiceImpl getKitItemService();
    void setKitItemService(KitItemServiceImpl kitItemServiceImpl);
    void setBeanFactory(BeanFactory beanFactory);
    BeanFactory getBeanFactory();
    void setKit(Bike bike);
    void sale(Bike bike, DB db);
}
