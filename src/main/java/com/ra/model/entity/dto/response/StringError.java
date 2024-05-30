package com.ra.model.entity.dto.response;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StringError {
    private String name;
    private String email;
    private String phone;
    private String product;
    private String oldPassword;
    private String newPassword;
    private String conPassword;
    private String username;
}
