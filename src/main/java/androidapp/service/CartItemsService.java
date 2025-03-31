package androidapp.service;

import androidapp.entity.CartItemsEntity;

import java.util.List;

public interface CartItemsService {
    public void addToCart(int userId, int productId, int quantity);
    public List<CartItemsEntity> getUserCart(int userId);
}
