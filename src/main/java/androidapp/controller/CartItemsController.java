package androidapp.controller;

import androidapp.entity.CartItemsEntity;
import androidapp.model.CartItemsModel;
import androidapp.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CartItemsController {
    @Autowired
    private CartItemsService cartService;

    @PostMapping("/cart/add")
    public ResponseEntity<Map<String, String>> addToCart(@RequestBody CartItemsModel cartItem) {
        cartService.addToCart(cartItem);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sản phẩm đã được thêm vào giỏ hàng");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/cart/delete")
    public ResponseEntity<Map<String, String>> deleteToCart(@RequestBody CartItemsModel cartItem) {
        cartService.deleteToCart(cartItem);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Sản phẩm đã được xóa khỏi giỏ hàng");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/cart/{userId}")
    public ResponseEntity<List<CartItemsEntity>> getUserCart(@PathVariable int userId) {
        return ResponseEntity.ok(cartService.getUserCart(userId));
    }

}
