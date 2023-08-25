package com.ecureuill.ada.avanade.orderapi.service;

import org.springframework.stereotype.Service;

import com.ecureuill.ada.avanade.orderapi.infra.api.DummyJsonClient;
import com.ecureuill.ada.avanade.orderapi.infra.api.ProductList;
import com.ecureuill.ada.avanade.orderapi.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {
    
    private final ProductRepository repository;
    private final DummyJsonClient client;

    public void fetchAndSave() {
        ProductList products = client.getProducts();
        repository.saveAll(products.getProducts());
    }
}
