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
    public ResponseEntity<Object> createOrder(@RequestBody OrderRecord record, UriComponentsBuilder uriBuilder, @RequestHeader (name="Authorization") String token) {
        Long orderId = (long) -1;
        try {
            orderId = service.create(record, token);
        } catch (NotFoundException e) {
            return ResponseEntity.badRequest().body("product not found");
        } catch (InsufficientStockException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
        var uri = uriBuilder.path("/orders/{id}").buildAndExpand(orderId).toUri();
        
        return ResponseEntity.created(uri).build();
    }
}