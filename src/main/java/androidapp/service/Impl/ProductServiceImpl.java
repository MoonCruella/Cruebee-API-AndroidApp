package androidapp.service.Impl;

import androidapp.entity.ProductEntity;
import androidapp.repository.ProductRepository;
import androidapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Override
    public List<ProductEntity> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<ProductEntity> findProductsWithSorting(String field){
        return productRepository.findAll(Sort.by(field));
    }

    @Override
    public Page<ProductEntity> findProductsWithPagination(int offset, int pagesize){
        Page<ProductEntity> page = productRepository.findAll(PageRequest.of(offset, pagesize));
        return page;
    }

    @Override
    public Page<ProductEntity> findProductsWithPaginationAndSorting(String field, int offset, int pagesize) {
        Page<ProductEntity> page = productRepository.findAll(PageRequest.of(offset, pagesize).withSort(Sort.by(field)));
        return page;
    }
}
