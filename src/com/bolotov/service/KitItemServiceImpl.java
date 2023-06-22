package com.bolotov.service;

import com.bolotov.entity.KitItem;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KitItemServiceImpl implements KitItemService, BeanNameAware {
    private String beanName;

    @Override
    public void setBeanName(String name) {
        beanName = name;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public List<KitItem> setSimpleKit() {
        List<KitItem> kitItems = new ArrayList<>();
        kitItems.add(new KitItem("bottle"));
        kitItems.add(new KitItem("pump"));
        return kitItems;
    }

    @Override
    public List<KitItem> setExtendedKit() {
        List<KitItem> kitItems = setSimpleKit();
        kitItems.add(new KitItem("helmet"));
        return kitItems;
    }
}
