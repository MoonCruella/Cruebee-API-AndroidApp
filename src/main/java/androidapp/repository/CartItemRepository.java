package androidapp.repository;

import androidapp.entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemsEntity, Integer> {
    List<CartItemsEntity> findByUserId(int customerId);
}
