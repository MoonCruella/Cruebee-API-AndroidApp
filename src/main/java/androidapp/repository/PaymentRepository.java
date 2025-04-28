package androidapp.repository;

import androidapp.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity, Integer> {
    List<PaymentEntity> findByUserId(int userId);
    List<PaymentEntity> findByStatusAndOrderDateBefore(String status, LocalDateTime orderDate);
    List<PaymentEntity> findAllByIdIn(List<Integer> ids);
}
