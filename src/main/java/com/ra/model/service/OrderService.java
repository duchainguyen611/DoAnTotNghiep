package com.ra.model.service;


import com.ra.model.entity.ENUM.OrderStatus;
import com.ra.model.entity.Order;
import com.ra.model.entity.dto.request.admin.AOrderRequestDTO;
import com.ra.model.entity.dto.response.admin.AOrderResponseDTO;

import java.util.List;

public interface OrderService {
    List<Order> getAll();
    List<AOrderResponseDTO> displayAll();
    List<AOrderResponseDTO> findAllByStatus(OrderStatus status);
    Order save(Order order);
    void delete(Long id);
    Order findById(Long id);
    Boolean confirmOrder(Long id);
    void addOrder(AOrderRequestDTO aOrderRequest);
    void updateOrder(AOrderRequestDTO aOrderRequest , Long orderId);
    Order AEntityMapRequest(AOrderRequestDTO aOrderRequest);
    AOrderRequestDTO AEntityMapRequest(Order order);
    AOrderResponseDTO AEntityMapResponse(Order order);
}
