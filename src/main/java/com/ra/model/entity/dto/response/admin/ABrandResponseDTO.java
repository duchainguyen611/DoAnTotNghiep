package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ABrandResponseDTO {
    private Long id;
    private String image;
    private String brandName;
    private String address;
    private String email;
    private String phone;
    private String status;
}
