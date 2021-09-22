package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.model.common.MessageResponse;
import com.anodiam.StudentSignup.model.common.ResponseCode;
import com.anodiam.StudentSignup.serviceRepository.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class UserServiceDal extends UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserServiceDal(){}

    @Override
    public User findByUsername(String username) {
        try {
            return userRepository.findByUsername(username);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public User save(User student) {
        MessageResponse messageResponse = new MessageResponse();
        try {
            if (student.getUsername()!="" && student.getPassword()!=""
                        && userRepository.findByUsername(student.getUsername())==null){
                String encodedPassword = passwordEncoder.encode(student.getPassword());
                User studentToSave = new User(student.getUsername(), encodedPassword);
                Role role_user = roleRepository.findByRoleName("USER");
                studentToSave.getRoleList().add(role_user);
                role_user.getUserList().add(studentToSave);
                userRepository.save(studentToSave);
                messageResponse = new MessageResponse(ResponseCode.SUCCESS.getID(),
                        "User Signup Saved Successfully!");
                studentToSave.setMessageResponse(messageResponse);
                return studentToSave;
            } else {
                messageResponse = new MessageResponse(ResponseCode.FAILURE.getID(),
                        "User Invalid. Not saved!");
                student.setMessageResponse(messageResponse);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            messageResponse = new MessageResponse(ResponseCode.FAILURE.getID(),
                    "Student Save Failed with Message: " + exception.getMessage());
        }
        return student;
    }
}
