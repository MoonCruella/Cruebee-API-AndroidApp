package androidapp.controller;

import androidapp.entity.ProductEntity;
import androidapp.entity.ShopEntity;
import androidapp.model.APIResponse;
import androidapp.model.ResponseShop;
import androidapp.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ShopController {

    @Autowired
    private ShopService shopService;

    @GetMapping("/shops/shop-in-10-km")
    public List<ResponseShop> getShopIn10Km(@RequestParam double lat, @RequestParam double lng){
         return shopService.findShopIn10km(lat,lng);
    }
}
