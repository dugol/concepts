package com.wannacode.productmicroservice.controller;

import com.wannacode.productmicroservice.entity.Product;
import com.wannacode.productmicroservice.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public void createProduct(@RequestBody Product product){
        productRepository.save(product);
    }

}
