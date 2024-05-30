package com.ra.model.entity.dto.request.admin;

import com.ra.validator.brand.EmailBrandUnique;
import com.ra.validator.brand.NameBrandUnique;
import com.ra.validator.brand.PhoneBrandUnique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ABrandRequestDTO {
    @NotBlank(message = "Tên thương hiệu không được để trống!")
    @NameBrandUnique(message = "Tên thương hiệu đã được sử dụng")
    private String brandName;

    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;

    @NotBlank(message = "Email không được để trống!")
    @EmailBrandUnique(message = "Email này đã được sử dụng")
    @Email(message = "Nhập đúng địa chỉ email!")
    private String email;

    private String image;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @PhoneBrandUnique(message = "Số điện thoại này đã được sử dụng")
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Nhập đúng định dạng số điện thoại VN!!")
    private String phone;
}
