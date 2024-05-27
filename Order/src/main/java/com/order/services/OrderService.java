package com.order.services;

import com.order.dtos.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    void removeOrder(String orderId);
    List<OrderDto> getOrdersByUser(String userId);
    List<OrderDto> getAllOrders(int pageNum, int pageSize, String sortBy);
}
