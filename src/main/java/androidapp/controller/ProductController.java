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

    @GetMapping("/top-ten-sold")
    public APIResponse<Page<ProductEntity>> getProductsTopTenSold(){
        Page<ProductEntity> products = productService.findTopTenSold();
        return new APIResponse<>(products.getSize(),products);
    }

}
