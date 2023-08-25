package com.ecureuill.ada.avanade.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.ada.avanade.orderapi.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    
}
