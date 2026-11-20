package com.example.orderservice;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import java.util.*;

@CrossOrigin(origins = "*")   // Frontend (Live Server) gọi trực tiếp Order Service
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final RestClient restClient;

    public OrderController(RestClient.Builder builder) {
        this.restClient = builder
                .baseUrl("http://localhost:8081")
                .build();
    }

    @GetMapping
    public List<Map<String, Object>> getOrders() {
        return List.of(
                Map.of("id", 101, "productId", 1, "quantity", 2),
                Map.of("id", 102, "productId", 2, "quantity", 1)
        );
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<Map<String, Object>> getProduct(@PathVariable int id) {
        try {
            Map<String, Object> product = restClient.get()
                    .uri("/api/products/{id}", id)
                    .retrieve()
                    .body(new ParameterizedTypeReference<Map<String, Object>>() {});
            return ResponseEntity.ok(product);
        } catch (HttpClientErrorException.NotFound e) {
            // Product Service trả 404 -> Order Service cũng trả 404
            return ResponseEntity.notFound().build();
        } catch (ResourceAccessException e) {
            // Không kết nối được Product Service
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }
}
