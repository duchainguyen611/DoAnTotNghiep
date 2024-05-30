package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "product")
public class Product extends BaseModel {
    @Column(name = "product_name")
    private String productName;
    @Column(name = "description",columnDefinition = "TEXT")
    private String description;
    @Column(name = "unit_price")
    private Double unitPrice;
    @Column(name = "stock_quantity")
    private Integer stockQuantity;
    @Column(name = "image")
    private String image;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @ManyToOne
    @JoinColumn(name = "categoryId", referencedColumnName = "id")
    private Category category;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<WishList> wishLists;

    @OneToMany(mappedBy = "product")
    @JsonIgnore
    private List<ShoppingCart> shoppingCarts;

    @ManyToOne
    @JoinColumn(name = "brandId",referencedColumnName = "id")
    private Brand brand;
}

