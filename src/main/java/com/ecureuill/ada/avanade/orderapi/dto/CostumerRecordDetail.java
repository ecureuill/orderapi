package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.CostumerEntity;

public record CostumerRecordDetail(
    Long id, 
    String name,
    String cpf,
    String email,
    AddressRecord address
)  {

    public CostumerRecordDetail(CostumerEntity user) {
        this(user.getId(), user.getName(), user.getCpf(), user.getEmail() , new AddressRecord(user.getAddress()));
    }
    
}
