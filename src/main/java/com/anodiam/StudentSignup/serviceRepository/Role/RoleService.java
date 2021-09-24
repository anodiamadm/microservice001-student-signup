package com.anodiam.StudentSignup.serviceRepository.Role;

import com.anodiam.StudentSignup.model.Role;

public interface RoleService {
    Role findByRoleName(String roleName);
}
