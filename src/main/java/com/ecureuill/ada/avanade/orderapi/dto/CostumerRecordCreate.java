package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.AddressEntity;
import com.ecureuill.ada.avanade.orderapi.entity.CustomerEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CostumerRecordCreate(
    @NotBlank
    String name,
    @NotBlank
    String cpf,
    @NotBlank
    @Email
    String email,
    @NotNull
    @Valid
    AddressRecord address
) {

    public CustomerEntity toEntity() {
        return new CustomerEntity(null, name, cpf, email, new AddressEntity(address), null);
    }
    
}
