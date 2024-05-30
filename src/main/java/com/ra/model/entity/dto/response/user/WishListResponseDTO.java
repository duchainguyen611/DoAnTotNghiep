package com.ra.model.entity.dto.response.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WishListResponseDTO {
    private Long id;
    private Long productId;
    private String nameProduct;
    private Integer stockQuantity;
    private String imageProduct;
    private String unitPrice;
}
