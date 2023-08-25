package com.ecureuill.ada.avanade.orderapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecureuill.ada.avanade.orderapi.dto.ProductRecord;
import com.ecureuill.ada.avanade.orderapi.infra.NotFoundException;
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

    public List<ProductRecord> findAll() {
        return repository.findAll().stream().map(ProductRecord::new).collect(Collectors.toList());
    }

    public ProductRecord findById(Long id) throws NotFoundException {
        var prod = repository.findById(id);
        if (prod.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }

        return new ProductRecord(prod.get());
    }
}
