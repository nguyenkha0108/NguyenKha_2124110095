package com.example.productservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@CrossOrigin(origins = "*")   // Chỉ nên dùng khi thực hành
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Dữ liệu lưu trong bộ nhớ (RAM): thay đổi khi chạy, mất khi tắt ứng dụng
    private final List<Map<String, Object>> products = new ArrayList<>(List.of(
            Map.of("id", 1, "name", "Mouse", "price", 150000),
            Map.of("id", 2, "name", "Keyboard", "price", 250000),
            Map.of("id", 3, "name", "Monitor", "price", 2500000),
            Map.of("id", 4, "name", "Headphone", "price", 350000),
            Map.of("id", 5, "name", "Webcam", "price", 450000),
            Map.of("id", 6, "name", "USB Hub", "price", 120000),
            Map.of("id", 7, "name", "Laptop Stand", "price", 300000),
            Map.of("id", 8, "name", "Speaker", "price", 550000),
            Map.of("id", 9, "name", "Microphone", "price", 800000),
            Map.of("id", 10, "name", "SSD 512GB", "price", 1200000)
    ));

    private int nextId = 11; // Id tăng dần, không tái sử dụng id đã xóa

    // Dữ liệu gửi lên khi thêm sản phẩm
    public record ProductRequest(String name, Integer price) {}

    @GetMapping
    public List<Map<String, Object>> getProducts() {
        return products;
    }

    // Tìm kiếm theo tên (không phân biệt hoa thường)
    // Đặt trước "/{id}" để không bị hiểu nhầm "search" là id
    @GetMapping("/search")
    public List<Map<String, Object>> searchProducts(@RequestParam(defaultValue = "") String name) {
        String keyword = name.trim().toLowerCase();
        return products.stream()
                .filter(p -> p.get("name").toString().toLowerCase().contains(keyword))
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable int id) {
        return products.stream()
                .filter(p -> p.get("id").equals(id))
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addProduct(@RequestBody ProductRequest req) {
        if (req.name() == null || req.name().isBlank() || req.price() == null || req.price() < 0) {
            return ResponseEntity.badRequest().build();
        }
        Map<String, Object> product = Map.of(
                "id", nextId++,
                "name", req.name().trim(),
                "price", req.price()
        );
        products.add(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        boolean removed = products.removeIf(p -> p.get("id").equals(id));
        return removed ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
