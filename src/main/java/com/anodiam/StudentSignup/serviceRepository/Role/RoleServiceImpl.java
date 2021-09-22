package com.anodiam.StudentSignup.serviceRepository.Role;

import com.anodiam.StudentSignup.model.Role;

abstract class RoleServiceImpl implements RoleService {

    @Override
    public Role findByRoleName(String roleName) {
        return new RoleServiceDal().findByRoleName(roleName);
    }
}
