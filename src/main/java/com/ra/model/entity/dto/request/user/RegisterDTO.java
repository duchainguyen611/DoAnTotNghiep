package com.ra.model.entity.dto.request.user;

import com.ra.validator.user.UserEmailUnique;
import com.ra.validator.user.UserPhoneUnique;
import com.ra.validator.user.UsernameUnique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    @NotBlank(message = "Email không được để trống!")
    @UserEmailUnique(message = "Địa chỉ email này đã được sử dụng!")
    @Email(message = "Nhập đúng địa chỉ email!")
    private String email;

    @NotBlank(message = "Họ và tên không được để trống!")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @UserPhoneUnique(message = "Số điện thoại này đã được sử dụng!")
    @Pattern(regexp = "^0[1-9]\\d{8}$", message = "Nhập đúng định dạng số điện thoại VN!!")
    private String phone;

    @NotBlank(message = "Tên đăng nhập không được để trống!")
    @UsernameUnique(message = "Tên đăng nhập này đã được sử dụng!")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống!")
    private String password;
}
