package com.ra.model.entity.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ACategoryUpdateRequestDTO {
    private Long id;
    @NotBlank(message = "Tên danh mục không được để trống!")
    private String categoryName;
    @NotBlank(message = "Mổ tả không được để trống!")
    private String description;
}
