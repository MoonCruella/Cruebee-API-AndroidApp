package androidapp.repository;

import androidapp.entity.CartItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItemsEntity, Integer> {
    List<CartItemsEntity> findByUserId(int customerId);
    @Query("SELECT c FROM CartItemsEntity c WHERE c.user.id = :userId AND c.product.id = :productId")
    CartItemsEntity findByUserIdAndProductId(@Param("userId") int userId, @Param("productId") int productId);

}
