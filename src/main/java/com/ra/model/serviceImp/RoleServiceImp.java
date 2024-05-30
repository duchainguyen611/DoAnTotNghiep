package com.ra.model.serviceImp;

import com.ra.model.entity.ENUM.RoleName;
import com.ra.model.entity.Role;
import com.ra.model.repository.RoleRepository;
import com.ra.model.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {
    private final RoleRepository roleRepository;
    @Override
    public Role findByRoleName(RoleName name) {
        return roleRepository.findByRoleName(name);
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
