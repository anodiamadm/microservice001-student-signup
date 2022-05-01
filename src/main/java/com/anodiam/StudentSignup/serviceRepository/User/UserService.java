package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);
    User save(User user);
}
