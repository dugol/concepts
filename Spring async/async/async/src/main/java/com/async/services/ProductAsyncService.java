package com.async.services;

import com.async.models.Product;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductAsyncService {

    @Async("asyncExecutor")
    public CompletableFuture<List<Product>> getProducts1() throws InterruptedException {
        Thread.sleep(1000);
        return CompletableFuture.completedFuture(
                Arrays.asList(
                        new Product(1 , "product 1"),
                        new Product(2 , "product 2"),
                        new Product(3 , "product 3")
                )
        );
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<Product>> getProducts2() throws InterruptedException {
        Thread.sleep(2000);
        return CompletableFuture.completedFuture(Arrays.asList(
                new Product(4 , "product 4"),
                new Product(5 , "product 5"),
                new Product(6 , "product 6")
        ));
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<Product>> getProducts3() throws InterruptedException {
        Thread.sleep(3000);
        return CompletableFuture.completedFuture(Arrays.asList(
                new Product(7 , "product 7"),
                new Product(8 , "product 8"),
                new Product(9 , "product 9")
        ));
    }
}
