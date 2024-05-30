package com.ra.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ra.model.entity.ENUM.ActiveStatus;
import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "vendor")
public class Vendor  extends BaseModel {
    @Column(name = "image")
    private String image;
    @Column(name = "vendor_name")
    private String vendorName;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @OneToMany(mappedBy = "vendor")
    @JsonIgnore
    List<Order> orders;
}
