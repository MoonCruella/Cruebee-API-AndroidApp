package androidapp.repository;

import androidapp.entity.PaymentProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentProductRepository extends JpaRepository<PaymentProductEntity, Integer> {
}
