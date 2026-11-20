package com.example.productservice.controller;

import com.example.productservice.entity.Brand;
import com.example.productservice.service.BrandService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService service;

    public BrandController(BrandService service) {
        this.service = service;
    }

    @GetMapping
    public List<Brand> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Brand getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Brand create(@Valid @RequestBody Brand brand) {
        return service.create(brand);
    }

    @PutMapping("/{id}")
    public Brand update(@PathVariable Long id, @Valid @RequestBody Brand brand) {
        return service.update(id, brand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
