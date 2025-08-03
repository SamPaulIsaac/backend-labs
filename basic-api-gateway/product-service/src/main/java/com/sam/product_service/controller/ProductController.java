package com.sam.product_service.controller;

import com.sam.product_service.entity.Product;
import com.sam.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;


    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public Product createProduct(@RequestBody Product product) {
        log.info("POST /api/product");
        return productService.createProduct(product);
    }

    @GetMapping("/all")
    public List<Product> getAllProducts() {
        log.info("GET /api/product");
        return productService.getAllProducts();
    }
}

