package androidapp.service;

import androidapp.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    public List<ProductEntity> findAll();
    public List<ProductEntity> findProductsWithSorting(String field);
    public Page<ProductEntity> findProductsWithPagination(int offset, int pagesize);
    public Page<ProductEntity> findProductsWithPaginationAndSorting(String field,int offset, int pagesize);
}
