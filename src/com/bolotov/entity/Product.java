package com.bolotov.entity;

import com.bolotov.DB;

public abstract class Product {
    double price;
    String model;
    String color;


    public Product(double price, String model, String color) {
        this.price = price;
        this.model = model;
        this.color = color;
    }

    public Product(double price, String model) {
        this.price = price;
        this.model = model;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Product{" +
                "price=" + price +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
