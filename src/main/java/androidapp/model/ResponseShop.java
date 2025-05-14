package androidapp.model;

import androidapp.entity.ShopEntity;

public class ResponseShop {
    private double distance;
    private ShopEntity shop;

    public ResponseShop(double distance, ShopEntity shop) {
        this.distance = distance;
        this.shop = shop;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public ShopEntity getShop() {
        return shop;
    }

    public void setShop(ShopEntity shop) {
        this.shop = shop;
    }
}
