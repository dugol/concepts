package com.wannacode.bookingmicroservice.controller;

import com.wannacode.bookingmicroservice.client.StockClient;
import com.wannacode.bookingmicroservice.dto.OrderDTO;
import com.wannacode.bookingmicroservice.entity.Order;
import com.wannacode.bookingmicroservice.repository.OrderRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    private OrderRepository orderRepository;
    private StockClient stockClient;

    public BookingController(OrderRepository orderRepository, StockClient stockClient) {
        this.orderRepository = orderRepository;
        this.stockClient = stockClient;
    }

    @PostMapping("/oders")
    public String saveOrder(@RequestBody OrderDTO orderDTO){

        boolean inStock =orderDTO.getOrderItems().stream()
                .allMatch(orderItem -> stockClient.stockAvaliable(orderItem.getCode()));

        if (inStock){
            Order order = new Order();
            order.setOderNo(UUID.randomUUID().toString());
            order.setOrderItems(orderDTO.getOrderItems());

            orderRepository.save(order);

            return "Order Saved";
        }

        return "Order cannot be Saved";
    }

    private String fallbackToStockService(){
        return "Something went wrong, please try after some time";
    }
}
