package com.async.controllers;

import com.async.models.Product;
import com.async.services.ProductAsyncService;
import com.async.services.ProductServices;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductServices productServices;
    private final ProductAsyncService productAsyncService;


    public ProductController(ProductServices productServices, ProductAsyncService productAsyncService) {
        this.productServices = productServices;
        this.productAsyncService = productAsyncService;
    }

    @GetMapping("/sync")
    public List<Product> getAllProducts() throws InterruptedException, ExecutionException {
        List<Product> list1 = productServices.getProducts1();
        List<Product> list2 = productServices.getProducts2();
        List<Product> list3 = productServices.getProducts3();

        List<Product> list4 = Stream.of(list1, list2, list3)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return list4;
    }

    @GetMapping("/async")
    public List<Product> getAllAsyncProducts() throws InterruptedException, ExecutionException {
        CompletableFuture<List<Product>> list1 = productAsyncService.getProducts1();
        CompletableFuture<List<Product>> list2 = productAsyncService.getProducts2();
        CompletableFuture<List<Product>> list3 = productAsyncService.getProducts3();

        CompletableFuture.allOf(list1, list2, list3).join();

        List<Product> list4 = Stream.of(list1.get(), list2.get(), list3.get())
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return list4;
    }

}
