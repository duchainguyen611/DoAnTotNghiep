package com.ra.model.entity.dto.request.admin;

import com.ra.validator.product.NameProductUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AProductRequestDTO {
    @NameProductUnique(message = "Tên sản phẩm đã được sử dụng!")
    @NotBlank(message = "Tên sản phẩm không được để trống!")
    private String productName;
    @NotBlank(message = "Mô tả không được để trống!")
    private String description;
    @NotNull(message = "Đơn giá không được để trống!")
    private Double unitPrice;
    @NotNull(message = "Số lượng trong kho không được để trống")
    private Integer stockQuantity;
    private String image;
    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;
    @NotNull(message = "Thương hiệu không được để trống")
    private Long brandId;
}
