package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.entity.ProductImage;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.ProductImageRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductImageService {
    private final ProductImageRepository repository;
    private final ProductService productService;

    public ProductImageService(ProductImageRepository repository, ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public List<ProductImage> getAll() {
        return repository.findAll();
    }

    public ProductImage getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hình ảnh sản phẩm", id));
    }

    public ProductImage create(ProductImage image) {
        image.setId(null);
        image.setProduct(resolveProduct(image));
        return repository.save(image);
    }

    public ProductImage update(Long id, ProductImage data) {
        ProductImage image = getById(id);
        image.setImageUrl(data.getImageUrl());
        image.setProduct(resolveProduct(data));
        return repository.save(image);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    private Product resolveProduct(ProductImage image) {
        if (image.getProduct() == null || image.getProduct().getId() == null) {
            throw new IllegalArgumentException("Thiếu product.id");
        }
        return productService.getById(image.getProduct().getId());
    }
}
