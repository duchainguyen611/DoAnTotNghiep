package com.ra.model.service;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.dto.request.admin.AOrderDetailRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AOrderDetailResponseDTO;

import java.util.List;

public interface OrderDetailService {
    List<OrderDetail> findAllByOrder(Order order);
    List<AOrderDetailResponseDTO> displayAllByOrder(Order order);
    void deleteByOrder(Order order);
    OrderDetail save(OrderDetail orderDetail);
    OrderDetail findById(Long id);
    void deleteById(Long id);
    StringError addOrderDetail(AOrderDetailRequestDTO aOrderDetailRequest, Long orderId);
    StringError updateOrder(AOrderDetailRequestDTO aOrderDetailRequest , Long id, Long orderId);
    Double totalPrice(Long orderId);
    Integer countProduct(Long orderId);
    OrderDetail AEntityMapRequest(AOrderDetailRequestDTO aOrderDetailRequest);
    AOrderDetailRequestDTO AEntityMapRequest(OrderDetail orderDetail);
    AOrderDetailResponseDTO AEntityMapResponse(OrderDetail orderDetail);
}
