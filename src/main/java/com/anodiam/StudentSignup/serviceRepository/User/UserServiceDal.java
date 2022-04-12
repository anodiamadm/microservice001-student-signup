package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.model.Permission;
import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.model.common.MessageResponse;
import com.anodiam.StudentSignup.model.common.ResponseCode;
import com.anodiam.StudentSignup.serviceRepository.Permission.PermissionRepository;
import com.anodiam.StudentSignup.serviceRepository.Role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
class UserServiceDal extends UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

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
    public User save(User student)
    {
        try
        {
            if (!isValidEmail(student.getUsername())) {
                student.setMessageResponse(new MessageResponse(ResponseCode.EMAIL_FORMAT_INVALID.getID(),
                        ResponseCode.EMAIL_FORMAT_INVALID.getMessage()));
                return student;
            }
            if (userRepository.findByUsername(student.getUsername())!=null) {
                student.setMessageResponse(new MessageResponse(ResponseCode.USER_ALREADY_EXISTS.getID(),
                        ResponseCode.USER_ALREADY_EXISTS.getMessage()));
                return student;
            }
            if(student.getPassword().length() < 6) {
                student.setMessageResponse(new MessageResponse(ResponseCode.PASSWORD_SHORT.getID(),
                        ResponseCode.PASSWORD_SHORT.getMessage()));
                return student;
            }
            if (!isValidPassword(student.getPassword())) {
                student.setMessageResponse(new MessageResponse(ResponseCode.PASSWORD_INVALID.getID(),
                        ResponseCode.PASSWORD_INVALID.getMessage()));
                return student;
            }
            String encodedPassword = passwordEncoder.encode(student.getPassword());
            User studentToSave = new User(student.getUsername(), encodedPassword);
            Role role_user = roleRepository.findByRoleName("USER");
            Permission permission_user = permissionRepository.findByPermissionName("STUDENT_ACCESS");
            studentToSave.getRoleList().add(role_user);
            studentToSave.getPermissionList().add(permission_user);
            role_user.getUserList().add(studentToSave);
            permission_user.getUserList().add(studentToSave);
            userRepository.save(studentToSave);
            studentToSave.setMessageResponse(new MessageResponse(ResponseCode.SUCCESS.getID(),
                    ResponseCode.SUCCESS.getMessage()));
            return studentToSave;

        } catch (Exception exception) {
            exception.printStackTrace();
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        ResponseCode.FAILURE.getMessage() + exception.getMessage()));
            return student;
        }
    }

    private  static boolean isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{5,100}$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);

        // If the password is empty
        // return false
        if (password == null) {
            return false;
        }

        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);

        // Return if the password
        // matched the ReGex
        return m.matches();
    }

    private static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{1,9}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
