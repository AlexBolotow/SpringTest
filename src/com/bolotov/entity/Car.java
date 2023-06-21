package com.bolotov.entity;

import org.springframework.beans.factory.stereotype.Component;

@Component
public class Car extends Product {

    int doorCount;
    String engine;

    public Car(double price, String model, String color, int doorCount, String engine) {
        super(price, model, color);
        this.color = color;
        this.model = model;
        this.doorCount = doorCount;
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Car{" +
                "color='" + color + '\'' +
                ", model='" + model + '\'' +
                ", doorCount=" + doorCount +
                ", engine='" + engine + '\'' +
                '}';
    }

    public int getDoorCount() {
        return doorCount;
    }

    public void setDoorCount(int doorCount) {
        this.doorCount = doorCount;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
