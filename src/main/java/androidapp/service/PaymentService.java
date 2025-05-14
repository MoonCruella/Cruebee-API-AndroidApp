package androidapp.service;

import androidapp.entity.PaymentEntity;
import androidapp.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    String placeOrder(PaymentEntity payment);
    Optional<PaymentEntity> findByPaymentId(int paymentId);
    List<PaymentEntity> findByUserId(int userId);
    List<PaymentEntity> findPaymentsByIds(List<Integer> paymentIds);
    void updateProductPayment(ProductEntity product, int quantity);
    Page<PaymentEntity> findByUserId(int userId, int page, int pageSize);
    Page<PaymentEntity> findPaymentsByIds(List<Integer> paymentIds, int page, int pageSize);
    String cancelOrder(int id);
}
