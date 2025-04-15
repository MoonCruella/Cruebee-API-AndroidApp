package androidapp.controller;

import androidapp.entity.PaymentEntity;
import androidapp.entity.ProductEntity;
import androidapp.model.LoginModel;
import androidapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@RequestBody PaymentEntity payment) {
        return new ResponseEntity<>(paymentService.placeOrder(payment), HttpStatus.OK);
    }
    @GetMapping("")
    public List<PaymentEntity> getPaymentByUser(@RequestParam int userId) {
        List<PaymentEntity> payments = paymentService.findByUserId(userId);
        return payments;
    }
    @GetMapping("/detail")
    public Optional<PaymentEntity> getPaymentById(@RequestParam int paymentId) {
        Optional<PaymentEntity> payment = paymentService.findByPaymentId(paymentId);
        return payment;   
    }
}
