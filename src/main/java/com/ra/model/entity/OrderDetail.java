package com.ra.model.entity;

import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "order_detail")
public class OrderDetail extends BaseModel {

    @Column(name = "order_quantity")
    private Integer orderQuantity;

    @Column(name = "unit_price_order")
    private Double unitPriceOrder;

    @ManyToOne
    @JoinColumn(name = "orderId",referencedColumnName = "id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;
}
