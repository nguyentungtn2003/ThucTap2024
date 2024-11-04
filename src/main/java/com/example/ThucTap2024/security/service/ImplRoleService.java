package com.example.ThucTap2024.security.service;

import com.example.ThucTap2024.entity.RoleEntity;
import com.example.ThucTap2024.services.IGeneralService;

public interface ImplRoleService extends IGeneralService<RoleEntity> {
    RoleEntity findByName(String name);
}
