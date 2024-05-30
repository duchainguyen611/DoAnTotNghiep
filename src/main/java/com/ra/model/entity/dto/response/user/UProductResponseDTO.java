package com.ra.model.entity.dto.response.user;

import com.ra.model.entity.Brand;
import com.ra.model.entity.Category;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UProductResponseDTO {
    private Long id;
    private Brand brand;
    private Category category;
    private String productName;
    private String description;
    private String unitPrice;
    private String image;
    private Integer stockQuantity;
}
