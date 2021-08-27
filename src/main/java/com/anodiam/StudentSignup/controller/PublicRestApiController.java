package com.anodiam.StudentSignup.controller;

import com.anodiam.StudentSignup.db.repository.RoleRepository;
import com.anodiam.StudentSignup.db.repository.UserRepository;
import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicRestApiController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    public PublicRestApiController(UserRepository userRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/studentsignup")
    public ResponseEntity<User> saveStudent(@RequestBody User student) throws Exception {
        try {
            if(userRepository.findByUsername(student.getUsername())!=null) {
                return new ResponseEntity(HttpStatus.GONE);
            } else if (userRepository.findByEmail(student.getEmail())!=null) {
                return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
            } else {
                String encodedPassword = passwordEncoder.encode(student.getPassword());
                User newStudent = new User(student.getUsername(), student.getFirstName(),
                                            student.getLastName(), student.getEmail(), encodedPassword);
                Role role_user = roleRepository.findByRoleName("USER");
                newStudent.getRoleList().add(role_user);
                role_user.getUserList().add(newStudent);
                this.userRepository.save(newStudent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
