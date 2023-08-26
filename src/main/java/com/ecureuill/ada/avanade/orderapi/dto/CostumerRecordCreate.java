package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.AddressEntity;
import com.ecureuill.ada.avanade.orderapi.entity.CostumerEntity;

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
    AddressRecord address,
    @NotNull
    String username
) {

    public CostumerEntity toEntity() {
        return new CostumerEntity(null, name, cpf, email, new AddressEntity(address), null);
    }
    
}
