package androidapp.config;

import androidapp.entity.PaymentEntity;
import androidapp.repository.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PaymentStatusScheduler {

    private final PaymentRepository paymentRepository;

    public PaymentStatusScheduler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Chạy mỗi 5 phút
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void updateOrderStatuses() {
        LocalDateTime now = LocalDateTime.now();

        // Cập nhật đơn hàng từ PREPARING → SHIPPING (sau 5 phút)
        List<PaymentEntity> preparingOrders = paymentRepository
                .findByStatusAndOrderDateBefore("PREPARING", now.minusMinutes(5));
        for (PaymentEntity payment : preparingOrders) {
            payment.setStatus("SHIPPING");
            paymentRepository.save(payment);
        }

        // Cập nhật đơn hàng từ SHIPPING → DELIVERED (sau 20 phút từ lúc tạo đơn)
        List<PaymentEntity> shippingOrders = paymentRepository
                .findByStatusAndOrderDateBefore("SHIPPING", now.minusMinutes(15));
        for (PaymentEntity payment : shippingOrders) {
            payment.setStatus("DELIVERED");
            paymentRepository.save(payment);
        }
    }
}

