package androidapp.controller;

import androidapp.entity.PaymentEntity;
import androidapp.entity.ProductEntity;
import androidapp.model.LoginModel;
import androidapp.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/order")
    public String placeOrder(@RequestBody PaymentEntity payment) {
        // Gọi service để xử lý đặt hàng và trả về ID của đơn hàng
        return paymentService.placeOrder(payment);
    }
//    @GetMapping("")
//    public List<PaymentEntity> getPaymentByUser(@RequestParam int userId) {
//        List<PaymentEntity> payments = paymentService.findByUserId(userId);
//        return payments;
//    }
    @GetMapping("/detail")
    public Optional<PaymentEntity> getPaymentById(@RequestParam int paymentId) {
        Optional<PaymentEntity> payment = paymentService.findByPaymentId(paymentId);
        return payment;   
    }

//    @GetMapping("/findByIds")
//    public ResponseEntity<List<PaymentEntity>> findPaymentsByIds(@RequestParam List<Integer> paymentIds) {
//        List<PaymentEntity> payments = paymentService.findPaymentsByIds(paymentIds);
//        if (payments.isEmpty()) {
//            return ResponseEntity.noContent().build();  // Trả về mã 204 nếu không tìm thấy
//        }
//        return ResponseEntity.ok(payments);  // Trả về danh sách Payment với mã 200
//    }

    @GetMapping("")
    public ResponseEntity<Page<PaymentEntity>> getPaymentByUser(
            @RequestParam int userId,
            @RequestParam(defaultValue = "0") int page,  // Trang hiện tại (mặc định là 0)
            @RequestParam(defaultValue = "10") int pageSize) {  // Kích thước trang (mặc định xlà 10)
        Page<PaymentEntity> payments = paymentService.findByUserId(userId, page, pageSize);
        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build();  // Trả về mã 204 nếu không có dữ liệu
        }
        return ResponseEntity.ok(payments);  // Trả về danh sách Payment theo trang với mã 200
    }

    @GetMapping("/findByIds")
    public ResponseEntity<Page<PaymentEntity>> findPaymentsByIds(
            @RequestParam List<Integer> paymentIds,
            @RequestParam(defaultValue = "0") int page,  // Trang hiện tại (mặc định là 0)
            @RequestParam(defaultValue = "10") int pageSize) {  // Kích thước trang (mặc định là 10)
        Page<PaymentEntity> payments = paymentService.findPaymentsByIds(paymentIds, page, pageSize);
        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build();  // Trả về mã 204 nếu không tìm thấy
        }
        return ResponseEntity.ok(payments);  // Trả về danh sách Payment theo trang với mã 200
    }
}
