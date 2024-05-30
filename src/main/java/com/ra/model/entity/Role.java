package com.ra.model.entity;

import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "role")
public class Role  extends BaseModel {
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleName roleName;
}
