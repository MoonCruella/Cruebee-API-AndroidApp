package androidapp.service.Impl;

import androidapp.entity.PaymentEntity;
import androidapp.entity.PaymentProductEntity;
import androidapp.entity.ProductEntity;
import androidapp.repository.PaymentProductRepository;
import androidapp.repository.PaymentRepository;
import androidapp.repository.ProductRepository;
import androidapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PaymentProductRepository paymentProductRepository;

    @Override
    public String placeOrder(PaymentEntity payment) {
        payment.setOrderDate(LocalDateTime.now());
        paymentRepository.save(payment);

        List<PaymentProductEntity> paymentProducts = payment.getProducts().stream().map(productOrder -> {
            PaymentProductEntity paymentProduct = new PaymentProductEntity();
            paymentProduct.setPayment(payment);
            paymentProduct.setProduct(productRepository.findById(productOrder.getProduct().getId()).orElseThrow());
            paymentProduct.setQuantity(productOrder.getQuantity());
            return paymentProduct;
        }).collect(Collectors.toList());

        paymentProductRepository.saveAll(paymentProducts);

        return "Đặt hàng thành công!";
    }
}
