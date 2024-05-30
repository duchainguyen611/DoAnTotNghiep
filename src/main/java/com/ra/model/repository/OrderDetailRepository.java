package com.ra.model.repository;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long>{
    List<OrderDetail> findAllByOrder(Order order);
    void deleteByOrder(Order order);
    boolean existsByProductAndOrder(Product product, Order order);
}
