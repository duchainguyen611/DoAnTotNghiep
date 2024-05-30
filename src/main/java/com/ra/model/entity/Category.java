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
@Table(name = "category")
public class Category extends BaseModel {

    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    List<Product> products;
}
