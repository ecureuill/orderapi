package com.ecureuill.ada.avanade.orderapi.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.ada.avanade.orderapi.entity.CostumerEntity;

public interface CostumerRepository extends JpaRepository<CostumerEntity, Long>{

    Optional<CostumerEntity> findByUserId(UUID id);

}
