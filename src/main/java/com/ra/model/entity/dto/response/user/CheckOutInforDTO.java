package com.ra.model.entity.dto.response.user;

import com.ra.model.entity.ENUM.PaymentMethods;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckOutInforDTO {
    private String receiveName;
    private String receivePhone;
    private String receiveAddress;
    private String note;
    private PaymentMethods paymentMethods;
}
