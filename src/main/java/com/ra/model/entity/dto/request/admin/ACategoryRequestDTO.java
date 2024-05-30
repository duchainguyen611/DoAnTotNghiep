package com.ra.model.entity.dto.request.admin;

import com.ra.validator.category.NameCategoryUnique;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ACategoryRequestDTO {
    @NameCategoryUnique(message = "Tên danh mục đã được sử dụng!")
    @NotBlank(message = "Tên danh mục không được để trống!")
    private String categoryName;
    @NotBlank(message = "Mổ tả không được để trống!")
    private String description;
}
