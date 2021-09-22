package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.model.User;

abstract class UserServiceImpl implements UserService {

    @Override
    public User findByUsername(String username) {
        return new UserServiceDal().findByUsername(username);
    }

    @Override
    public User save(User user) {
        return new UserServiceDal().save(user);
    }
}
