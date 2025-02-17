package androidapp.repository;

import androidapp.entity.ProductEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.stream.IntStream;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

}
