package com.ra.model.entity.dto.response.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressResponseDTO {
    private Long id;
    private String receiveAddress;
    private String receiveName;
    private String receivePhone;
    private String status;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
}
