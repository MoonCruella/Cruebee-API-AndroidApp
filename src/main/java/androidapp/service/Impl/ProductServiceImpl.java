package androidapp.service.Impl;

import androidapp.entity.ProductEntity;
import androidapp.repository.ProductRepository;
import androidapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductEntity> findByCategoryId(int categoryId) {
        return productRepository.findProductsByCategoryId(categoryId);
    }


    @Override
    public Page<ProductEntity> findTopTenSold() {
        String field = "soldCount";
        Page<ProductEntity> page = productRepository.findAll(PageRequest.of(0, 10).withSort(Sort.by(field).reverse()));
        return page;
    }

}
