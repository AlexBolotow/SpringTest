package com.bolotov.service;

import com.bolotov.entity.Promotion;

public interface PromotionService {
    double countSale(Promotion promotion, double price);
}
