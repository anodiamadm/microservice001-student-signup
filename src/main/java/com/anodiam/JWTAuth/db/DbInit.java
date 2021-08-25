package com.anodiam.JWTAuth.db;

import com.anodiam.JWTAuth.model.Permission;
import com.anodiam.JWTAuth.model.Role;
import com.anodiam.JWTAuth.model.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DbInit implements CommandLineRunner {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private PasswordEncoder passwordEncoder;

    public DbInit(UserRepository userRepository, RoleRepository roleRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        this.userRepository.deleteAll();

        User dan = new User("dan", passwordEncoder.encode("dan123"));
        User anirban = new User("anirban", passwordEncoder.encode("anirban123"));
        User admin = new User("admin", passwordEncoder.encode("admin123"));
        User manager = new User("manager", passwordEncoder.encode("manager123"));

        Role role_user = new Role("USER");
        Role role_admin = new Role("ADMIN");
        Role role_manager = new Role("MANAGER");

        Permission permission_access_test1 = new Permission("ACCESS_TEST1");
        Permission permission_access_test2 = new Permission("ACCESS_TEST2");

        dan.getRoleList().add(role_user);
        anirban.getRoleList().add(role_user);
        admin.getRoleList().add(role_admin);
        manager.getRoleList().add(role_manager);

        role_user.getUserList().add(dan);
        role_user.getUserList().add(anirban);
        role_admin.getUserList().add(admin);
        role_user.getUserList().add(manager);

        manager.getPermissionList().add(permission_access_test1);
        admin.getPermissionList().add(permission_access_test1);
        admin.getPermissionList().add(permission_access_test2);

        permission_access_test1.getUserList().add(manager);
        permission_access_test1.getUserList().add(admin);
        permission_access_test2.getUserList().add(admin);

        List<Role> roles = Arrays.asList(role_user, role_admin, role_manager);
        List<Permission> permissions = Arrays.asList(permission_access_test1, permission_access_test2);
        List<User> users = Arrays.asList(dan, anirban, admin, manager);

        this.roleRepository.saveAll(roles);
        this.permissionRepository.saveAll(permissions);
        this.userRepository.saveAll(users);
    }
}
