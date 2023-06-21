package com.bolotov.entity;

import org.springframework.beans.factory.stereotype.Component;

@Component
public class Bike extends Product {
    int countSpeeds;

    public Bike(double price, String model, String color, int countSpeeds) {
        super(price, model, color);
        this.color = color;
        this.model = model;
        this.countSpeeds = countSpeeds;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "color='" + color + '\'' +
                ", model='" + model + '\'' +
                ", countSpeeds=" + countSpeeds +
                '}';
    }

    public int getCountSpeeds() {
        return countSpeeds;
    }

    public void setCountSpeeds(int countSpeeds) {
        this.countSpeeds = countSpeeds;
    }
}
