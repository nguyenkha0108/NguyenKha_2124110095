package com.example.productservice.service;

import com.example.productservice.entity.Category;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.CategoryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    public List<Category> getAll() {
        return repository.findAll();
    }

    public Category getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loại sản phẩm", id));
    }

    public Category create(Category category) {
        category.setId(null);
        return repository.save(category);
    }

    public Category update(Long id, Category data) {
        Category category = getById(id);
        category.setName(data.getName());
        category.setDescription(data.getDescription());
        return repository.save(category);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
