package com.wannacode.productmicroservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter

@NoArgsConstructor
@Document(value = "product")
public class Product {
    @Id
    private String id;
    private String productName;
    private String productDescription;
    private Double unitPrice;
}
