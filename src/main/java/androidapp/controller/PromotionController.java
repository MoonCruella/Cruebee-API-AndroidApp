package androidapp.controller;

import androidapp.entity.ProductEntity;
import androidapp.entity.PromotionEntity;
import androidapp.service.ProductService;
import androidapp.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    @Autowired
    private PromotionService promotionService;

    @GetMapping
    public List<PromotionEntity> getPromotions(){
        List<PromotionEntity> promotionList = promotionService.findAll();
        return promotionList;

    }
}
