package com.ecureuill.ada.avanade.orderapi.entity;

import com.ecureuill.ada.avanade.orderapi.dto.AddressRecord;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class AddressEntity {

    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String zip;
    private String number;
    private String complement;
    
    public AddressEntity(AddressRecord address) {
        this.street = address.street();
        this.neighborhood = address.neighborhood();
        this.city = address.city();
        this.state = address.state();
        this.zip = address.zip();
        this.number = address.number();
        this.complement = address.complement();
    }
}