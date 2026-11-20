package com.example.orderservice.dto;

import jakarta.validation.constraints.NotBlank;

public record OrderUpdateRequest(
        @NotBlank String status,
        @NotBlank String shippingAddress
) {}
