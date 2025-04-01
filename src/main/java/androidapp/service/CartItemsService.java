package androidapp.service;

import androidapp.entity.CartItemsEntity;
import androidapp.model.CartItemsModel;

import java.util.List;

public interface CartItemsService {
    public void addToCart(CartItemsModel cartItemsModel);
    public List<CartItemsEntity> getUserCart(int userId);
    void deleteToCart(CartItemsModel cartItem);
}
