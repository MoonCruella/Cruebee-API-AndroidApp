package androidapp.service.Impl;

import androidapp.entity.PaymentEntity;
import androidapp.entity.PaymentProductEntity;
import androidapp.entity.ProductEntity;
import androidapp.entity.UserEntity;
import androidapp.repository.PaymentProductRepository;
import androidapp.repository.PaymentRepository;
import androidapp.repository.ProductRepository;
import androidapp.repository.UserRepository;
import androidapp.service.PaymentService;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public String placeOrder(PaymentEntity payment) {

        payment.setOrderDate(LocalDateTime.now());
        UserEntity user = userRepository.findById(payment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        payment.setUser(user);

        List<PaymentProductEntity> paymentProducts = payment.getProducts().stream().map(productOrder -> {

            // cập nhật số lượng bán ra của từng món ăn khi thanh toán thành công
            PaymentProductEntity paymentProduct = new PaymentProductEntity();
            Optional<ProductEntity> productEntity = productRepository.findById(productOrder.getProduct().getId());
            if (productEntity.isPresent()) {
                ProductEntity product = productEntity.get();
                updateProductPayment(product,productOrder.getQuantity());
            }

            // Khi đặt hàng sẽ set Status là đang chuẩn bị
            payment.setStatus("PREPARING");
            paymentProduct.setPayment(payment);
            paymentProduct.setProduct(productRepository.findById(productOrder.getProduct().getId()).orElseThrow());
            paymentProduct.setQuantity(productOrder.getQuantity());
            return paymentProduct;
        }).collect(Collectors.toList());

        payment.setProducts(paymentProducts);

        // Lưu và trả về ID đơn hàng
        JSONObject outputJsonObj = new JSONObject();
        outputJsonObj.put("paymentId",paymentRepository.save(payment).getId());
        return outputJsonObj.toString();
    }


    @Override
    public Optional<PaymentEntity> findByPaymentId(int paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Transactional
    public List<PaymentEntity> findByUserId(int userId) {
        return paymentRepository.findByUserId(userId);
    }

    @Override
    public List<PaymentEntity> findPaymentsByIds(List<Integer> paymentIds) {
        return paymentRepository.findAllByIdIn(paymentIds);
    }

    @Override
    public void updateProductPayment(ProductEntity product, int quantity) {
        product.setSoldCount(product.getSoldCount() + quantity);
        productRepository.save(product);
    }


    public Page<PaymentEntity> findByUserId(int userId, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return paymentRepository.findByUserId(userId, pageable);
    }

    public Page<PaymentEntity> findPaymentsByIds(List<Integer> paymentIds, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return paymentRepository.findByIdIn(paymentIds, pageable);
    }

    @Override
    public String cancelOrder(int id) {
        Optional<PaymentEntity> payment = paymentRepository.findById(id);
        if (payment.isPresent()) {
            payment.get().setStatus("CANCELED");
            payment.get().setOrderDate(LocalDateTime.now());
            paymentRepository.save(payment.get());
            return "Cancel successfully";
        }
        else {
            return "Order not found";
        }
    }
}
