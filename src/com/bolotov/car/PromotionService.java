package com.bolotov.car;

import org.springframework.beans.factory.stereotype.Service;

@Service
public class PromotionService {
    private PromotionService promotionService;

    public PromotionService getPromotionService() {
        return promotionService;
    }

    public void setPromotionService(PromotionService promotionService) {
        this.promotionService = promotionService;
    }
}
