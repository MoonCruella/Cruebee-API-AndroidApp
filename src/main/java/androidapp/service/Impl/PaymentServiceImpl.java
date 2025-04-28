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

    @Autowired
    private PaymentProductRepository paymentProductRepository;

    @Override
    public String placeOrder(PaymentEntity payment) {

        payment.setOrderDate(LocalDateTime.now());
        UserEntity user = userRepository.findById(payment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        payment.setUser(user);

        List<PaymentProductEntity> paymentProducts = payment.getProducts().stream().map(productOrder -> {
            PaymentProductEntity paymentProduct = new PaymentProductEntity();

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

}
