package com.ra.model.serviceImp;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.dto.request.admin.AOrderDetailRequestDTO;
import com.ra.model.entity.dto.response.StringError;
import com.ra.model.entity.dto.response.admin.AOrderDetailResponseDTO;
import com.ra.model.repository.OrderDetailRepository;
import com.ra.model.service.OrderDetailService;
import com.ra.model.service.OrderService;
import com.ra.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImp implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;
    private final ProductService productService;
    private final OrderService orderService;



    @Override
    public List<OrderDetail> findAllByOrder(Order order) {
        return orderDetailRepository.findAllByOrder(order);
    }

    @Override
    public List<AOrderDetailResponseDTO> displayAllByOrder(Order order) {
        return findAllByOrder(order).stream().map(this::AEntityMapResponse).toList();
    }

    @Override
    public void deleteByOrder(Order order) {
        orderDetailRepository.deleteByOrder(order);
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }

    @Override
    public OrderDetail findById(Long id) {
        return orderDetailRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        orderDetailRepository.deleteById(id);
    }

    @Override
    public StringError addOrderDetail(AOrderDetailRequestDTO aOrderDetailRequest, Long orderId) {
        OrderDetail orderDetail = AEntityMapRequest(aOrderDetailRequest);
        orderDetail.setOrder(orderService.findById(orderId));
        boolean isProductExist = orderDetailRepository.existsByProductAndOrder(orderDetail.getProduct(),orderDetail.getOrder());
        StringError stringError = new StringError();
        if (isProductExist){
            stringError.setProduct("Sản phẩm này đã có trong phiếu nhập!");
            return stringError;
        }
        save(orderDetail);
        return null;
    }

    @Override
    public StringError updateOrder(AOrderDetailRequestDTO aOrderDetailRequest, Long id, Long orderId) {
        OrderDetail orderDetailOld = findById(id);
        OrderDetail orderDetailNew = AEntityMapRequest(aOrderDetailRequest);
        orderDetailNew.setOrder(orderService.findById(orderId));
        orderDetailNew.setId(orderDetailOld.getId());
        orderDetailNew.setCreatedDate(orderDetailOld.getCreatedDate());
        boolean isProductExist = orderDetailRepository.existsByProductAndOrder(orderDetailNew.getProduct(),orderDetailNew.getOrder())
                &&!orderDetailOld.getProduct().equals(orderDetailNew.getProduct());
        StringError stringError = new StringError();
        if (isProductExist){
            stringError.setProduct("Sản phẩm này đã có trong phiếu nhập!");
            return stringError;
        }
        save(orderDetailNew);
        return null;
    }

    @Override
    public Double totalPrice(Long orderId) {
        Order order = orderService.findById(orderId);
        double totalPrice = 0;
        for (OrderDetail orderDetail:order.getOrderDetails()){
            totalPrice += orderDetail.getUnitPriceOrder()*orderDetail.getOrderQuantity();
        }
        order.setTotalPrice(totalPrice);
        orderService.save(order);
        return totalPrice;
    }

    @Override
    public Integer countProduct(Long orderId) {
        Order order = orderService.findById(orderId);
        return order.getOrderDetails().size();
    }

    @Override
    public OrderDetail AEntityMapRequest(AOrderDetailRequestDTO aOrderDetailRequest) {
        return OrderDetail.builder()
                .product(productService.findById(aOrderDetailRequest.getProductId()))
                .orderQuantity(aOrderDetailRequest.getOrderQuantity())
                .unitPriceOrder(aOrderDetailRequest.getUnitPriceOrder())
                .build();
    }

    @Override
    public AOrderDetailRequestDTO AEntityMapRequest(OrderDetail orderDetail) {
        return AOrderDetailRequestDTO.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productId(orderDetail.getProduct().getId())
                .orderQuantity(orderDetail.getOrderQuantity())
                .unitPriceOrder(orderDetail.getUnitPriceOrder())
                .build();
    }

    @Override
    public AOrderDetailResponseDTO AEntityMapResponse(OrderDetail orderDetail) {
        return AOrderDetailResponseDTO.builder()
                .id(orderDetail.getId())
                .productImage(orderDetail.getProduct().getImage())
                .productName(orderDetail.getProduct().getProductName())
                .orderQuantity(orderDetail.getOrderQuantity())
                .productUnitPrice(NumberFormat.getInstance(new Locale("vi", "VN")).format(orderDetail.getUnitPriceOrder())+ "₫")
                .totalAmount(NumberFormat.getInstance(new Locale("vi", "VN")).format(orderDetail.getUnitPriceOrder()*orderDetail.getOrderQuantity())+ "₫")
                .build();
    }
}
