package com.example.orderservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

public record OrderRequest(
        @NotNull Long customerId,
        @NotBlank String shippingAddress,
        @NotEmpty @Valid List<Item> items
) {
    public record Item(
            @NotNull Long productId,
            @NotNull @Min(1) Integer quantity
    ) {}
}
