package androidapp.service;

import androidapp.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public List<ProductEntity> findAll();
    public List<ProductEntity> findByCategoryId(int categoryId);
    public Page<ProductEntity> findTopTenSold();

}
