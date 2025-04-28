package androidapp.service;

import androidapp.entity.PaymentEntity;

import java.util.List;
import java.util.Optional;

public interface PaymentService {
    public String placeOrder(PaymentEntity payment);
    public Optional<PaymentEntity> findByPaymentId(int paymentId);
    public List<PaymentEntity> findByUserId(int userId);
    List<PaymentEntity> findPaymentsByIds(List<Integer> paymentIds);
}
