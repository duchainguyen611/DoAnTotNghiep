package com.ra.model.entity;

import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "shopping_cart")
public class ShoppingCart  extends BaseModel {
    @Column(name = "cart_quantity")
    private Integer cartQuantity;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;
}
