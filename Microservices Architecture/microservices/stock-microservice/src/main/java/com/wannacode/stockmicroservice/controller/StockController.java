package com.wannacode.stockmicroservice.controller;

import com.wannacode.stockmicroservice.entity.Stock;
import com.wannacode.stockmicroservice.repository.StockRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private StockRepository stockRepository;

    public StockController(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @GetMapping("/{code}")
    public boolean stockAvaliable(@PathVariable String code){
        Optional<Stock> stock = stockRepository.findByCode(code);

        //stock.orElseThrow(() -> new RuntimeException("Cannot find the product " + code));

        return stock.orElseGet(() -> {
            Stock fake = new Stock();
            fake.setQuantity(0);
            return fake;
        }).getQuantity() > 0;
    }
}
