package com.ra.model.entity.dto.response.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UBrandResponseDTO {
    private Long id;
    private String image;
    private String brandName;
}
