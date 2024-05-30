package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.ENUM.InvoiceStatus;
import com.ra.model.entity.ENUM.PaymentMethods;
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
@Table(name = "invoice")
public class Invoice  extends BaseModel {

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "invoice_status")
    @Enumerated(EnumType.STRING)
    private InvoiceStatus invoiceStatus;

    @Column(name = "note",columnDefinition = "TEXT")
    private String note;

    @Column(name = "receive_name")
    private String receiveName;

    @Column(name = "receive_address")
    private String receiveAddress;

    @Column(name = "receive_phone")
    private String receivePhone;

    @Column(name = "payment_methods")
    @Enumerated(EnumType.STRING)
    private PaymentMethods paymentMethods;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "invoice")
    @JsonIgnore
    private List<InvoiceDetail> invoiceDetails;
}
