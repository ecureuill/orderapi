package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.CustomerEntity;

public record CustomerRecordDetail(
    Long id, 
    String name,
    String cpf,
    String email,
    AddressRecord address
)  {

    public CustomerRecordDetail(CustomerEntity user) {
        this(user.getId(), user.getName(), user.getCpf(), user.getEmail() , new AddressRecord(user.getAddress()));
    }
    
}
