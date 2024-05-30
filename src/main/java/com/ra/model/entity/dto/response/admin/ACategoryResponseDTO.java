package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ACategoryResponseDTO {
    private Long id;
    private String categoryName;
    private String description;
    private String status;
}
