package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.ENUM.OrderStatus;
import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "orders")
public class Order extends BaseModel {

    @Column(name = "resial_number")
    private String serialNumber;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "note",columnDefinition = "TEXT")
    private String note;

    @ManyToOne
    @JoinColumn(name = "vendorId",referencedColumnName = "id")
    private Vendor vendor;

    @OneToMany(mappedBy = "order")
    @JsonIgnore
    private List<OrderDetail> orderDetails;
}
