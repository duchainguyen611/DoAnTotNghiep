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
@Table(name = "invoice_detail")
public class InvoiceDetail  extends BaseModel {

    @Column(name = "invoice_quantity")
    private Integer invoiceQuantity;

    @ManyToOne
    @JoinColumn(name = "invoiceId",referencedColumnName = "id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "id")
    private Product product;
}
