package com.ra.model.repository;

import com.ra.model.entity.ENUM.OrderStatus;
import com.ra.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{
    List<Order> findAllByOrderStatus(OrderStatus orderStatus);
}
