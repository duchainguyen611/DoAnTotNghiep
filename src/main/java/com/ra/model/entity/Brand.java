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
@Table(name = "brand")
public class Brand extends BaseModel {

    @Column(name = "image")
    private String image;

    @Column(name = "brand_name")
    private String brandName;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ActiveStatus status;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Product> products;
}
