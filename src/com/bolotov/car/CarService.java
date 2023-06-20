package com.bolotov.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.stereotype.Service;

@Service
public class CarService {
    @Autowired
    public PromotionService promotionService;

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }
}
