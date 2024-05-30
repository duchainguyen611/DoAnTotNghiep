package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AOrderDetailResponseDTO {
    private Long id;
    private String productImage;
    private String productName;
    private Integer orderQuantity;
    private String productUnitPrice;
    private String totalAmount;
}
