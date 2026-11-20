package com.example.orderservice.client;

import com.example.orderservice.dto.ProductInfo;
import com.example.orderservice.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Component
public class ProductClient {
    private final RestClient restClient;

    public ProductClient(RestClient.Builder builder, @Value("${product-service.url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    public ProductInfo getProduct(Long productId) {
        try {
            return restClient.get()
                    .uri("/api/products/{id}", productId)
                    .retrieve()
                    .body(ProductInfo.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("Sản phẩm", productId);
        }
    }
}
