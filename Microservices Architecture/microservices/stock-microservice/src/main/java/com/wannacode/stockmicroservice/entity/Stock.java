package com.wannacode.stockmicroservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stock")
@NoArgsConstructor
@Getter
@Setter
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private Integer quantity;
}
