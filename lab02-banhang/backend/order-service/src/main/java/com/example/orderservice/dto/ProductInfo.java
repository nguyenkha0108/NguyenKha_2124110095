package com.example.orderservice.dto;

import java.math.BigDecimal;

// Dữ liệu sản phẩm nhận từ Product Service
public record ProductInfo(Long id, String name, BigDecimal price, Integer quantity) {}
