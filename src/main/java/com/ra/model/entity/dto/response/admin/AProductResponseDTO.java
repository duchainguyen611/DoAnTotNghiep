package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AProductResponseDTO {
    private Long id;
    private String image;
    private String productName;
    private String categoryName;
    private String unitPrice;
    private Integer stockQuantity;
    private String productStatus;
}
