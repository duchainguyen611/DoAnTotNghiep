package com.ra.model.entity;

import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "address")
public class Address extends BaseModel {

    @Column(name = "receive_name")
    private String receiveName;

    @Column(name = "receive_phone")
    private String receivePhone;

    @Column(name = "receive_address")
    private String receiveAddress;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private User user;
}
