package com.ra.model.entity.dto.request.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AProductUpdateRequestDTO {
    private Long id;
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    private String productName;
    @NotBlank(message = "Mô tả không được để trống!")
    private String description;
    @NotNull(message = "Đơn giá không được để trống!")
    private Double unitPrice;
    @NotNull(message = "Số lượng trong kho không được để trống")
    private Integer stockQuantity;
    private String image;
    private Long categoryId;
    private Long brandId;
}
