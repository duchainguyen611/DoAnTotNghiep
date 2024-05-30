package com.ra.model.entity.dto.request.user;

import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.User;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {
    private Long id;
    private User user;
    private String receiveName;
    private String receivePhone;
    private ActiveStatus status;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
}
