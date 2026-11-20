package com.example.productservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 200)
    private String name;

    @Column(length = 1000)
    private String description;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @NotNull
    @Min(0)
    @Column(nullable = false)
    private Integer quantity;

    // Trạng thái kinh doanh: true = đang bán
    @Column(nullable = false)
    private Boolean active = true;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }
    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }
}
