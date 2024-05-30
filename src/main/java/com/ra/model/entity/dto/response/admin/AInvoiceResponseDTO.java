package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AInvoiceResponseDTO {
    private Long id;
    private String username;
    private String serialNumber;
    private String totalPrice;
    private String statusInvoice;
    private String createdDate;
}
