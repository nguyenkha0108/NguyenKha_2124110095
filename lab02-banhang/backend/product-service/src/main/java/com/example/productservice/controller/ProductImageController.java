package com.example.productservice.controller;

import com.example.productservice.entity.ProductImage;
import com.example.productservice.service.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/product-images")
public class ProductImageController {
    private final ProductImageService service;

    public ProductImageController(ProductImageService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductImage> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ProductImage getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductImage create(@Valid @RequestBody ProductImage image) {
        return service.create(image);
    }

    @PutMapping("/{id}")
    public ProductImage update(@PathVariable Long id, @Valid @RequestBody ProductImage image) {
        return service.update(id, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
