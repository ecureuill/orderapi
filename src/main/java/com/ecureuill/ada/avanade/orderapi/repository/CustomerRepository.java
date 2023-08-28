package com.ecureuill.ada.avanade.orderapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.ada.avanade.orderapi.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long>{

    Optional<CustomerEntity> findByUserId(UUID id);

}
