package androidapp.controller;

import androidapp.entity.PaymentEntity;
import androidapp.model.LoginModel;
import androidapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody PaymentEntity payment) {
        return new ResponseEntity<>(paymentService.placeOrder(payment), HttpStatus.OK);
    }
}
