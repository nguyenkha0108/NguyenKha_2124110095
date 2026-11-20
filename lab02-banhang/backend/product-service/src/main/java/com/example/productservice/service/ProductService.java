package com.example.productservice.service;

import com.example.productservice.entity.Product;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final CategoryService categoryService;
    private final BrandService brandService;

    public ProductService(ProductRepository repository, CategoryService categoryService, BrandService brandService) {
        this.repository = repository;
        this.categoryService = categoryService;
        this.brandService = brandService;
    }

    public List<Product> getAll(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return repository.findAll();
        }
        return repository.findByNameContainingIgnoreCase(keyword.trim());
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm", id));
    }

    public Product create(Product product) {
        product.setId(null);
        if (product.getActive() == null) product.setActive(true);
        resolveRelations(product);
        return repository.save(product);
    }

    public Product update(Long id, Product data) {
        Product product = getById(id);
        product.setName(data.getName());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setQuantity(data.getQuantity());
        if (data.getActive() != null) product.setActive(data.getActive());
        product.setCategory(data.getCategory());
        product.setBrand(data.getBrand());
        resolveRelations(product);
        return repository.save(product);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    // Kiểm tra category và brand có tồn tại trước khi lưu
    private void resolveRelations(Product product) {
        if (product.getCategory() == null || product.getCategory().getId() == null) {
            throw new IllegalArgumentException("Thiếu category.id");
        }
        if (product.getBrand() == null || product.getBrand().getId() == null) {
            throw new IllegalArgumentException("Thiếu brand.id");
        }
        product.setCategory(categoryService.getById(product.getCategory().getId()));
        product.setBrand(brandService.getById(product.getBrand().getId()));
    }
}
