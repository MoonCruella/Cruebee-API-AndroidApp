package androidapp.repository;

import androidapp.entity.ProductEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.IntStream;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

}
