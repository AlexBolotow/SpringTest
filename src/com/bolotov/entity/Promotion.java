package com.bolotov.entity;

import org.springframework.beans.factory.stereotype.Component;

@Component
public class Promotion {
    int percent;

    public Promotion(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "percent=" + percent +
                '}';
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
