package com.ra.model.entity.dto.request.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AVendorUpdateRequestDTO {
    private Long id;

    @NotBlank(message = "Tên thương hiệu không được để trống!")
    private String vendorName;

    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Nhập đúng địa chỉ email!")
    private String email;

    private String image;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Nhập đúng định dạng số điện thoại VN!!")
    private String phone;
}
