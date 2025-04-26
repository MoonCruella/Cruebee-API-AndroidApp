package androidapp.service.Impl;

import androidapp.entity.CartItemsEntity;
import androidapp.entity.ProductEntity;
import androidapp.entity.UserEntity;
import androidapp.model.CartItemsModel;
import androidapp.repository.CartItemRepository;
import androidapp.repository.ProductRepository;
import androidapp.repository.UserRepository;
import androidapp.service.CartItemsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemsServiceImpl  implements CartItemsService {
    @Autowired
    private CartItemRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addToCart(CartItemsModel cartItemsModel) {
        int userId = cartItemsModel.getUserId();
        int productId = cartItemsModel.getProductId();
        int quantity = cartItemsModel.getQuantity();
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User không tồn tại!"));

        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại!"));

        // Kiểm tra sản phẩm có tồn tại trong giỏ hàng của user hay không
        CartItemsEntity cartItem = cartRepository.findByUserIdAndProductId(userId, productId);

        if (cartItem != null) {

            // Nếu sản phẩm đã có trong giỏ hàng => Cộng dồn số lượng
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {

            // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
            cartItem = new CartItemsEntity();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
        }

        cartRepository.save(cartItem);
    }



    // Lấy danh sách giỏ hàng của người dùng
    @Override
    public List<CartItemsEntity> getUserCart(int userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public void deleteToCart(CartItemsModel cartItemsModel) {
        int userId = cartItemsModel.getUserId();
        int productId = cartItemsModel.getProductId();
        int quantity = cartItemsModel.getQuantity();
        // Kiểm tra sản phẩm có tồn tại trong giỏ hàng của user hay không
        CartItemsEntity cartItem = cartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItem != null) {
            cartRepository.delete(cartItem);
        }
    }

    @Override
    @Transactional
    public void clearCart(int userId) {
        Optional<UserEntity> user = userRepository.findById(userId);
        if(user.isPresent()) {
            UserEntity userEntity = user.get();
            cartRepository.deleteAllByUser(userEntity);
        }
    }
}
