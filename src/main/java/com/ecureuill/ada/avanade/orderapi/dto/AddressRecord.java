package com.ecureuill.ada.avanade.orderapi.dto;

import com.ecureuill.ada.avanade.orderapi.entity.AddressEntity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressRecord(
    @NotBlank
    String street,
    @NotBlank
    String neighborhood,
    @NotBlank
    String city,
    @NotBlank
    String state,
    @NotBlank
    @Pattern(regexp = "\\d{8}")
    String zip,
    @NotBlank
    String number,
    String complement
) {

    public AddressRecord(AddressEntity address) {
        this(
            address.getStreet(),
            address.getNeighborhood(),
            address.getCity(),
            address.getState(),
            address.getZip(),
            address.getNumber(),
            address.getComplement()
        );
    }

}
