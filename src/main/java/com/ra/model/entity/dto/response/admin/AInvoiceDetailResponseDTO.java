package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AInvoiceDetailResponseDTO {
    private Long id;
    private String productImage;
    private String productName;
    private Integer invoiceQuantity;
    private String productUnitPrice;
    private String totalAmount;
}
