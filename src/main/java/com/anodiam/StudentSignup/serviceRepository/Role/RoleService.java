package com.anodiam.StudentSignup.serviceRepository.Role;

import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;

public interface RoleService {
    Role findByRoleName(String roleName);
}
