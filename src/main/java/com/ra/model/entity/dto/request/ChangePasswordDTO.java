package com.ra.model.entity.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordDTO {
    @NotBlank(message = "Mật khẩu cũ không được để trống!")
    private String oldPassword;
    @NotBlank(message = "Mật khẩu mới không được để trống!")
    private String newPassword;
    @NotBlank(message = "Mật khẩu xác nhận không được để trống!")
    private String conPassword;
}
