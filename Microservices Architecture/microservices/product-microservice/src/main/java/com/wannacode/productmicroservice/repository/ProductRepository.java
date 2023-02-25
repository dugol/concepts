package com.wannacode.productmicroservice.repository;


import com.wannacode.productmicroservice.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
