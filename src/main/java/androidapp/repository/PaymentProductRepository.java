package androidapp.repository;

import androidapp.entity.PaymentProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentProductRepository extends JpaRepository<PaymentProductEntity, Integer> {
}
