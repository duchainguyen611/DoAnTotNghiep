package com.ra.model.entity.dto.response.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CartResponseDTO {
    private Long id;
    private Long productId;
    private String nameProduct;
    private Double unitPrice;
    private String imageProduct;
    private Integer cartQuantity;
}
