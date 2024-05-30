package com.ra.model.entity.dto.request.admin;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AOrderDetailRequestDTO {
    private Long id;
    private Long orderId;
    private Long productId;
    @NotNull(message = "Số lượng sản phẩm không được để trống!")
    private Integer orderQuantity;
    @NotNull(message = "Đơn giá sản phẩm nhập hàng không được để trống!")
    private Double unitPriceOrder;
}
