package com.example.productservice.service;

import com.example.productservice.entity.Brand;
import com.example.productservice.exception.ResourceNotFoundException;
import com.example.productservice.repository.BrandRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BrandService {
    private final BrandRepository repository;

    public BrandService(BrandRepository repository) {
        this.repository = repository;
    }

    public List<Brand> getAll() {
        return repository.findAll();
    }

    public Brand getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Thương hiệu", id));
    }

    public Brand create(Brand brand) {
        brand.setId(null);
        return repository.save(brand);
    }

    public Brand update(Long id, Brand data) {
        Brand brand = getById(id);
        brand.setName(data.getName());
        brand.setDescription(data.getDescription());
        return repository.save(brand);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
