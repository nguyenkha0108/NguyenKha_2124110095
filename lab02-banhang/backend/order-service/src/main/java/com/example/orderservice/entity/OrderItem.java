package com.example.orderservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @NotNull
    @Column(nullable = false)
    private Long productId;

    // Lưu tên và giá tại thời điểm đặt hàng (giá có thể thay đổi sau này)
    @Column(nullable = false, length = 200)
    private String productName;

    @NotNull
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal unitPrice;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    @JsonIgnore // tránh vòng lặp khi trả JSON
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
