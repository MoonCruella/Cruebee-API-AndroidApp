package androidapp.service.Impl;

import androidapp.entity.CartItemsEntity;
import androidapp.entity.ProductEntity;
import androidapp.entity.UserEntity;
import androidapp.repository.CartItemRepository;
import androidapp.repository.ProductRepository;
import androidapp.repository.UserRepository;
import androidapp.service.CartItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemsServiceImpl  implements CartItemsService {
    @Autowired
    private CartItemRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Thêm sản phẩm vào giỏ hàng
    @Override
    public void addToCart(int userId, int productId, int quantity) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));

        CartItemsEntity cartItem = new CartItemsEntity();
        cartItem.setUser(user);
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);

        cartRepository.save(cartItem);
    }

    // Lấy danh sách giỏ hàng của người dùng
    @Override
    public List<CartItemsEntity> getUserCart(int userId) {
        return cartRepository.findByUserId(userId);
    }
}
