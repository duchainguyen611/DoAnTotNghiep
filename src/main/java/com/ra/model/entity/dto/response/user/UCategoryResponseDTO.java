package com.ra.model.entity.dto.response.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UCategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
}
