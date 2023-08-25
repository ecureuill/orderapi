package com.ecureuill.ada.avanade.orderapi.dto;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record OrderRecord(
    @Valid
    @NotNull
    List<OrderItemRecord> items
) {
    
}
