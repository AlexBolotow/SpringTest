package com.bolotov;

import com.bolotov.entity.Bike;
import com.bolotov.entity.Car;

public class Test {
    public static void main(String[] args) {
        Car car = new Car(7000, "BMW", "black", 4, "V8");
        Object object = car;

        System.out.println(object.getClass());

    }
}
