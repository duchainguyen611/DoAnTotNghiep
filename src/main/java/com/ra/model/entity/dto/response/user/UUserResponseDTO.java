package com.ra.model.entity.dto.response.user;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UUserResponseDTO {
    private Long id;
    private String dateOfBirth;
    private String image;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String username;
    private String status;
    private String createdDate;
    private String modifyDate;
}
