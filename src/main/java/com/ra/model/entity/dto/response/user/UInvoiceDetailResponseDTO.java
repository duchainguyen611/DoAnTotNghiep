package com.ra.model.entity.dto.response.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UInvoiceDetailResponseDTO {
    private Long id;
    private Long idProduct;
    private String nameProduct;
    private String imageProduct;
    private String unitPriceProduct;
    private Integer invoiceQuantity;
    private String totalPrice;
}
