package com.example.orderservice.service;

import com.example.orderservice.client.ProductClient;
import com.example.orderservice.dto.OrderRequest;
import com.example.orderservice.dto.OrderUpdateRequest;
import com.example.orderservice.dto.ProductInfo;
import com.example.orderservice.entity.Order;
import com.example.orderservice.entity.OrderItem;
import com.example.orderservice.exception.ResourceNotFoundException;
import com.example.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class OrderService {
    private static final Set<String> STATUSES = Set.of("NEW", "CONFIRMED", "SHIPPING", "DELIVERED", "CANCELLED");

    private final OrderRepository repository;
    private final ProductClient productClient;

    public OrderService(OrderRepository repository, ProductClient productClient) {
        this.repository = repository;
        this.productClient = productClient;
    }

    public List<Order> getAll() {
        return repository.findAll();
    }

    public Order getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn hàng", id));
    }

    // Tạo đơn hàng: gọi Product Service để lấy giá, tên và kiểm tra tồn kho
    @Transactional
    public Order create(OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.customerId());
        order.setShippingAddress(request.shippingAddress());

        BigDecimal total = BigDecimal.ZERO;
        for (OrderRequest.Item item : request.items()) {
            ProductInfo product = productClient.getProduct(item.productId());

            if (product.quantity() == null || product.quantity() < item.quantity()) {
                throw new IllegalArgumentException("Sản phẩm '" + product.name()
                        + "' không đủ tồn kho (còn " + product.quantity() + ")");
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductId(item.productId());
            orderItem.setProductName(product.name());
            orderItem.setUnitPrice(product.price());
            orderItem.setQuantity(item.quantity());
            order.getItems().add(orderItem);

            total = total.add(product.price().multiply(BigDecimal.valueOf(item.quantity())));
        }
        order.setTotalAmount(total);
        return repository.save(order);
    }

    public Order update(Long id, OrderUpdateRequest request) {
        if (!STATUSES.contains(request.status())) {
            throw new IllegalArgumentException("Trạng thái không hợp lệ. Chỉ nhận: " + String.join(", ", STATUSES));
        }
        Order order = getById(id);
        order.setStatus(request.status());
        order.setShippingAddress(request.shippingAddress());
        return repository.save(order);
    }

    // Xóa đơn hàng kéo theo các chi tiết đơn (cascade)
    public void delete(Long id) {
        repository.delete(getById(id));
    }
}
