package com.bolotov.entity;

import org.springframework.beans.factory.stereotype.Component;

import java.util.List;

@Component
public class Bike extends Product {
    int countSpeeds;
    List<KitItem> kit;

    public Bike(double price, String model, String color, int countSpeeds) {
        super(price, model, color);
        this.color = color;
        this.model = model;
        this.countSpeeds = countSpeeds;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "countSpeeds=" + countSpeeds +
                ", kit=" + kit +
                ", price=" + price +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public List<KitItem> getKit() {
        return kit;
    }

    public void setKit(List<KitItem> kit) {
        this.kit = kit;
    }

    public int getCountSpeeds() {
        return countSpeeds;
    }

    public void setCountSpeeds(int countSpeeds) {
        this.countSpeeds = countSpeeds;
    }
}
