package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AOrderResponseDTO {
    private Long id;
    private String vendorName;
    private String serialNumber;
    private String totalPrice;
    private String statusOrder;
    private String createdDate;
}
