package com.ra.model.entity.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangeProfileDTO {

    @NotBlank(message = "Email không được để trống!")
    @Email(message = "Nhập đúng địa chỉ email!")
    private String email;
    private String image;
    @NotBlank(message = "Tên đầy đủ không được bỏ trống")
    private String fullName;
    @NotBlank(message = "Số điện thoại không được để trống!")
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Nhập đúng định dạng số điện thoại VN!!")
    private String phone;
    @NotBlank(message = "Địa chỉ không được để trống!")
    private String address;
    @NotBlank(message = "Ngày sinh không được để trống!")
    private String dateOfBirth;
    private Boolean gender;
}
