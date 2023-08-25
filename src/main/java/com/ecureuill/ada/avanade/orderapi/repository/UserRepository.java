package com.ecureuill.ada.avanade.orderapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecureuill.ada.avanade.orderapi.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long>{
    public Optional<UserEntity> findByUsername(String username);
}
