# Quản lý bán hàng - Microservices (Lab 02)

- backend/product-service (MySQL, port 8081): Loại sản phẩm, thương hiệu, sản phẩm, hình ảnh sản phẩm
- backend/order-service (SQL Server, port 8082): Đơn hàng, chi tiết đơn hàng (gọi Product Service để lấy giá và kiểm tra tồn kho)
- frontend/admin, frontend/client: sẽ bổ sung ở các buổi sau

## Chạy
1. Tạo database: `product_db` (MySQL), `order_db` (SQL Server)
2. Tạo file `.env` trong từng service (không đưa lên GitHub)
3. `mvn spring-boot:run` trong từng service
