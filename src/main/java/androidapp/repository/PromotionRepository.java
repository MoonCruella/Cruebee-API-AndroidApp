package androidapp.repository;

import androidapp.entity.ProductEntity;
import androidapp.entity.PromotionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity,Integer> {
}
