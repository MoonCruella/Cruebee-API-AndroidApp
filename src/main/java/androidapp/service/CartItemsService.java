package androidapp.service;

import androidapp.entity.CartItemsEntity;
import androidapp.model.CartItemsModel;

import java.util.List;

public interface CartItemsService {
    void addToCart(CartItemsModel cartItemsModel);
    List<CartItemsEntity> getUserCart(int userId);
    void deleteToCart(CartItemsModel cartItem);
    void clearCart(int userId);
}
