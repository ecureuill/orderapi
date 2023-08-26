package com.ecureuill.ada.avanade.orderapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecureuill.ada.avanade.orderapi.dto.OrderRecord;
import com.ecureuill.ada.avanade.orderapi.exceptions.InsufficientStockException;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.service.OrderService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class OrderController {
    
    private final OrderService service;

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody OrderRecord record, UriComponentsBuilder uriBuilder) throws Exception {
        Long orderId = service.create(record);
        var uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderId).toUri();
        return ResponseEntity.created(uri).build();
    }
}