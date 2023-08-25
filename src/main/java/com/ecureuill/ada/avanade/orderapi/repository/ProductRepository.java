package com.ecureuill.ada.avanade.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.ada.avanade.orderapi.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>{

}
