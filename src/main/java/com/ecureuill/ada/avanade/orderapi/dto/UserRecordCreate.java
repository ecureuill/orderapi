package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.UserEntity;

import jakarta.validation.constraints.NotBlank;

public record UserRecordCreate(
    @NotBlank
    String username,
    @NotBlank
    @NotBlank
    String password
) {

    public UserEntity toEntity() {
        return new UserEntity(null, username, password);
    }
    
}
