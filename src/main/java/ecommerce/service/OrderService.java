package ecommerce.service;

import ecommerce.dto.OrderCreateRequest;
import ecommerce.dto.OrderResponse;
import ecommerce.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    OrderResponse createOrder(Long userId, OrderCreateRequest request);
    OrderResponse getOrderById(Long orderId);
    List<OrderResponse> getOrdersByUserId(Long userId);
    List<OrderResponse> getAllOrders();
    OrderResponse updateOrderStatus(Long orderId, OrderStatus status);
    void cancelOrder(Long orderId);
    List<OrderResponse> getOrdersByStatus(OrderStatus status);
    boolean canCancelOrder(Long orderId);
}