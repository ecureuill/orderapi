package com.ecureuill.ada.avanade.orderapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record OrderItemRecord(
    @NotBlank
    @Size(min = 1)
    Integer quantity,
    @NotBlank
    Long productId
) {
}
