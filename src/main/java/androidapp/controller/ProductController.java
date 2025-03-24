package androidapp.controller;


import androidapp.entity.ProductEntity;
import androidapp.model.APIResponse;
import androidapp.repository.ProductRepository;
import androidapp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductEntity> getProducts(){
        List<ProductEntity> products = productService.findAll();
        return products;

    }

    @GetMapping("/{categoryId}")
    public List<ProductEntity> getProductsByCategory(@PathVariable int categoryId){
        List<ProductEntity> products = productService.findByCategoryId(categoryId);
        return products;
    }

    @GetMapping("/sort/{field}")
    public APIResponse<List<ProductEntity>> getProductsWithSorting(@PathVariable String field){
        List<ProductEntity> products = productService.findProductsWithSorting(field);
        return new APIResponse<>(products.size(),products);

    }

    @GetMapping("/pagination/{offset}/{pagesize}")
    public APIResponse<Page<ProductEntity>> getProductsWithPagination(@PathVariable int offset,@PathVariable int pagesize) {
        Page<ProductEntity> products = productService.findProductsWithPagination(offset, pagesize);
        return new APIResponse<>(products.getSize(),products);
    }

    @GetMapping("/paginationAndSort/{offset}/{pagesize}/{field}")
    public APIResponse<Page<ProductEntity>> getProductsWithPaginationAndSorting(@PathVariable String field,@PathVariable int offset,@PathVariable int pagesize) {
        Page<ProductEntity> products = productService.findProductsWithPaginationAndSorting(field,offset, pagesize);
        return new APIResponse<>(products.getSize(),products);
    }

    @GetMapping("/top-ten-sold")
    public APIResponse<Page<ProductEntity>> getProductsTopTenSold(){
        Page<ProductEntity> products = productService.findTopTenSold();
        return new APIResponse<>(products.getSize(),products);
    }

    @GetMapping("/created-in-a-week/{offset}")
    public APIResponse<Page<ProductEntity>> getProductsCreatedInAWeek(@PathVariable int offset){
        Page<ProductEntity> products = productService.findCreatedInAWeek(offset);
        return new APIResponse<>(products.getSize(),products);
    }

}
