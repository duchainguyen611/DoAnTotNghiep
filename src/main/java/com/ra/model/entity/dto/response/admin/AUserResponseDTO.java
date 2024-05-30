package com.ra.model.entity.dto.response.admin;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AUserResponseDTO {
    private Long id;
    private String dateOfBirth;
    private String image;
    private String fullName;
    private String email;
    private String phone;
    private String gender;
    private String address;
    private String username;
    private String status;
    private String createdDate;
    private String modifyDate;
}
