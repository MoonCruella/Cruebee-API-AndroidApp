package androidapp.controller;

import androidapp.entity.CartItemsEntity;
import androidapp.model.CartItemsModel;
import androidapp.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartItemsController {
    @Autowired
    private CartItemsService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<String> addToCart(@RequestBody CartItemsModel cartItem) {
        cartService.addToCart(cartItem);
        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng!");
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<String> deleteToCart(@RequestBody CartItemsModel cartItem) {
        cartService.deleteToCart(cartItem);
        return ResponseEntity.ok("Sản phẩm đã được xoa khoi giỏ hàng!");
    }


    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<CartItemsEntity>> getUserCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

}
