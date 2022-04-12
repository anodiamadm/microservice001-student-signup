package com.anodiam.StudentSignup.serviceRepository.Permission;

import com.anodiam.StudentSignup.model.Permission;

public interface PermissionService {
    Permission findByPermissionName(String permissionName);
}
