package com.ecureuill.ada.avanade.orderapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecureuill.ada.avanade.orderapi.dto.ProductRecord;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    
    private final ProductService service;

    @GetMapping("/fetch")
    @SecurityRequirement(name = "bearer-key")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String>  fetchAllProducts() {
        service.fetchAndSave();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ProductRecord>> getAllProducts() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductRecord> getProduct(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(service.findById(id));
    }
}
