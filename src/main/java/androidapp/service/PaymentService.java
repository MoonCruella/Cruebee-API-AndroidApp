package androidapp.service;

import androidapp.entity.PaymentEntity;

public interface PaymentService {
    public String placeOrder(PaymentEntity payment);
}
