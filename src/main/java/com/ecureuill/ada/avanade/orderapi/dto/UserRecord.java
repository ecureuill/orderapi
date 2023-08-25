package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;

public record UserRecord(
    @NotBlank
    String username,
    @NotBlank
    String password
) {
    public UserRecord(UserEntity user) {
        this(user.getUsername(), user.getPassword());
    }
}
