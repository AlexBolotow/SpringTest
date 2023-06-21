package com.bolotov.entity;

import org.springframework.beans.factory.stereotype.Component;

@Component
public class KitItem {
    String name;

    public KitItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "KitItem{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
