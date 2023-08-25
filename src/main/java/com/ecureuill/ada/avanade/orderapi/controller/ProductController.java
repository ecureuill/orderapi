package com.ecureuill.ada.avanade.orderapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecureuill.ada.avanade.orderapi.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService service;

    @GetMapping("/fetch")
    public ResponseEntity<String>  getAllProducts() {
        service.fetchAndSave();
        return ResponseEntity.ok().build();
    }
}
