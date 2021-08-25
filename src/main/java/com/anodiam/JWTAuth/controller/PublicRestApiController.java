package com.anodiam.JWTAuth.controller;

import com.anodiam.JWTAuth.db.UserRepository;
import com.anodiam.JWTAuth.model.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/public")
@CrossOrigin
public class PublicRestApiController {

    private UserRepository userRepository;
    public PublicRestApiController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    Available to all authenticated users
    @GetMapping("test")
    public String test(){
        return "API Test";
    }

//    Available to Managers
    @GetMapping("management/reports")
    public String reports(){
        return "Anodiam Report Data";
    }

//    Available to ROLE_ADMIN
    @GetMapping("admin/users")
    public List<User> users(){
        return this.userRepository.findAll();
    }
}
