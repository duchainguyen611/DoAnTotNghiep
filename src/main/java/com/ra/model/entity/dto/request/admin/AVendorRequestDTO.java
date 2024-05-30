package com.ra.model.entity.dto.request.admin;

import com.ra.validator.vendor.EmailVendorUnique;
import com.ra.validator.vendor.NameVendorUnique;
import com.ra.validator.vendor.PhoneVendorUnique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AVendorRequestDTO {
    private String image;
    @NameVendorUnique(message = "Tên nhà cung cấp này đã được sử dụng!")
    @NotBlank(message = "Tên nhà cung cấp không được để trống!")
    private String vendorName;
    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;
    @EmailVendorUnique(message = "Email này đã được sử dụng!")
    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Nhập đúng địa chỉ email!")
    private String email;
    @PhoneVendorUnique(message = "Số điện thoại này đã được sử dụng!")
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Nhập đúng định dạng số điện thoại VN!!")
    private String phone;
}
