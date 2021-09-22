package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.model.User;

public interface UserService {
    User findByUsername(String username);
    User save(User user);
}
