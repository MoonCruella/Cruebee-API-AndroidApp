package androidapp.controller;

import androidapp.entity.CartItemsEntity;
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
    public ResponseEntity<String> addToCart(@RequestParam int userId, @RequestParam int productId, @RequestParam int quantity) {
        cartService.addToCart(userId, productId, quantity);
        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng!");
    }

    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<CartItemsEntity>> getUserCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

}
