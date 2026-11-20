package com.example.productservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resource, Long id) {
        super("Không tìm thấy " + resource + " với id = " + id);
    }
}
