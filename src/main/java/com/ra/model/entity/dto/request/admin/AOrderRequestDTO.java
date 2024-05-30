package com.ra.model.entity.dto.request.admin;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AOrderRequestDTO {
    private Long id;
    @NotNull(message = "Nhà cung cấp không được để trống")
    private Long vendorId;
    private String note;
}
