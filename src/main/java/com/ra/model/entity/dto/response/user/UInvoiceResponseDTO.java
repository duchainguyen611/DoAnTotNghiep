package com.ra.model.entity.dto.response.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UInvoiceResponseDTO {
    private Long id;
    private String createdDate;
    private String receiveName;
    private String receiveAddress;
    private String receivePhone;
    private String paymentMethods;
    private String note;
    private String totalPriceNotShip;
    private String totalPriceHaveShip;
    private String statusInvoice;
}
