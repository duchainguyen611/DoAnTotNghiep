package com.ra.model.serviceImp;

import com.ra.model.entity.ENUM.OrderStatus;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Product;
import com.ra.model.entity.dto.request.admin.AOrderRequestDTO;
import com.ra.model.entity.dto.response.admin.AOrderResponseDTO;
import com.ra.model.repository.OrderRepository;
import com.ra.model.service.OrderService;
import com.ra.model.service.ProductService;
import com.ra.model.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderService {
    private final OrderRepository orderRepository;
    private final VendorService vendorService;
    private final ProductService productService;

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<AOrderResponseDTO> displayAll() {
        return getAll().stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public List<AOrderResponseDTO> findAllByStatus(OrderStatus status) {
        return orderRepository.findAllByOrderStatus(status).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean confirmOrder(Long id) {
        Order order = findById(id);
        order.setOrderStatus(OrderStatus.CONFIRMED);
        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (!orderDetails.isEmpty()){
            for (OrderDetail orderDetail : orderDetails) {
                Product product = orderDetail.getProduct();
                product.setStockQuantity(product.getStockQuantity() + orderDetail.getOrderQuantity());
                productService.save(product);
            }
            save(order);
            return true;
        }
        return false;
    }

    @Override
    public void addOrder(AOrderRequestDTO aOrderRequest) {
        Order order = AEntityMapRequest(aOrderRequest);
        order.setTotalPrice((double) 0);
        order.setOrderStatus(OrderStatus.UNCONFIRMED);
        order.setSerialNumber(UUID.randomUUID().toString());
        save(order);
    }

    @Override
    public void updateOrder(AOrderRequestDTO aOrderRequest, Long orderId) {
        Order orderOld = findById(orderId);
        Order orderNew = AEntityMapRequest(aOrderRequest);
        orderNew.setId(orderOld.getId());
        orderNew.setSerialNumber(orderOld.getSerialNumber());
        orderNew.setCreatedDate(orderOld.getCreatedDate());
        orderNew.setOrderStatus(orderOld.getOrderStatus());
        orderNew.setTotalPrice(orderOld.getTotalPrice());
        save(orderNew);
    }

    @Override
    public Order AEntityMapRequest(AOrderRequestDTO aOrderRequest) {
        return Order.builder()
                .vendor(vendorService.findById(aOrderRequest.getVendorId()))
                .note(aOrderRequest.getNote())
                .build();
    }

    @Override
    public AOrderRequestDTO AEntityMapRequest(Order order) {
        return AOrderRequestDTO.builder()
                .id(order.getId())
                .vendorId(order.getVendor().getId())
                .note(order.getNote())
                .build();
    }

    @Override
    public AOrderResponseDTO AEntityMapResponse(Order order) {
        return AOrderResponseDTO.builder()
                .id(order.getId())
                .vendorName(order.getVendor().getVendorName())
                .serialNumber(order.getSerialNumber())
                .totalPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(order.getTotalPrice())+ "₫")
                .statusOrder(order.getOrderStatus().toString())
                .createdDate(order.getCreatedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .build();
    }

}
