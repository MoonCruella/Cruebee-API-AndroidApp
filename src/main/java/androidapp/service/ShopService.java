package androidapp.service;

import androidapp.entity.ShopEntity;
import androidapp.model.ResponseShop;

import java.util.List;
import java.util.Map;

public interface ShopService {
    List<ResponseShop> findShopIn10km(double uLong, double uLat);
}
