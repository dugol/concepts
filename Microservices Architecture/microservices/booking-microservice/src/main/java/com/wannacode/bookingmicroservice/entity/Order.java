package com.wannacode.bookingmicroservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "order_item")
@Getter
@Setter
@NoArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String oderNo;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}
