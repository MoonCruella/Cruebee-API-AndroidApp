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

    @Override
    public Page<ProductEntity> findTopTenSold() {
        String field = "soldCount";
        Page<ProductEntity> page = productRepository.findAll(PageRequest.of(0, 10).withSort(Sort.by(field).reverse()));
        return page;
    }

    @Override
    public Page<ProductEntity> findCreatedInAWeek(int offset) {
        // Get the current date and time
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);

        // Fetch all products (adjust this query to return your products as needed)
        List<ProductEntity> allProducts = productRepository.findAll();

        // Use streams to filter products created within the last 7 days or earlier
        List<ProductEntity> filteredProducts = allProducts.stream()
                .filter(product -> product.getGeneratedTime().isAfter(sevenDaysAgo) || product.getGeneratedTime().isEqual(sevenDaysAgo))
                .collect(Collectors.toList());

        // Paginate the filtered results
        int start = offset * 10;
        int end = Math.min(start + 10, filteredProducts.size());
        List<ProductEntity> paginatedProducts = filteredProducts.subList(start, end);

        // Return as a Page
        return new PageImpl<>(paginatedProducts.reversed(), PageRequest.of(offset, 10), filteredProducts.size());
    }
}
