package com.ra.model.repository;

import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByRoleName(RoleName roleName);
}
