package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AProductDetailResponseDTO {
    private Long id;
    private String brandName;
    private String categoryName;
    private String productName;
    private String description;
    private String unitPrice;
    private Integer stockQuantity;
    private String image;
    private String status;
    private String createdDate;
    private String modifyDate;
}
