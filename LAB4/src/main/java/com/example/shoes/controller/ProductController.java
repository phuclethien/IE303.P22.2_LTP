package com.example.shoes.controller;

import com.example.shoes.model.Product;
import com.example.shoes.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shoes")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getAllShoes() {
        return productRepository.findAll();
    }
}

