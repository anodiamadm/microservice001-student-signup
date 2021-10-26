package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.model.common.MessageResponse;
import com.anodiam.StudentSignup.model.common.ResponseCode;
import com.anodiam.StudentSignup.serviceRepository.Role.RoleRepository;
import com.anodiam.StudentSignup.serviceRepository.errorHandling.ErrorHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
class UserServiceDal extends UserServiceImpl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ErrorHandlingService errorHandlingService;

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
            if(student.getUsername().trim().length() == 0)
            {
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        "User name cannot be blank!"));
                return student;
            }
            if(student.getUsername().trim().length() < 8)
            {
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        "User name cannot be less than eight characters!"));
                return student;
            }
            String enocdedUserName=new GeneralEncoderDecoder().encrypt(student.getUsername());
            if(userRepository.findByUsername(enocdedUserName) !=null)
            {
                student.setMessageResponse(new MessageResponse(ResponseCode.DUPLICATE.getID(),
                        "User name already exists!"));
                return student;
            }

            if (!isValidPassword(student.getPassword()))
            {
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        "Invalid Password!"));
                return student;
            }
            if (!isValidEmail(student.getEmail()))
            {
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        "Invalid Email Id!"));
                return student;
            }

            String enocdedEmail=new GeneralEncoderDecoder().encrypt(student.getEmail());
            if(userRepository.findByEmail(enocdedEmail) !=null)
            {
                student.setMessageResponse(new MessageResponse(ResponseCode.DUPLICATE.getID(),
                        "Email already exists!"));
                return student;
            }

            String encodedPassword = passwordEncoder.encode(student.getPassword());
            User studentToSave = new User(enocdedUserName, encodedPassword,enocdedEmail);
            Role role_user = roleRepository.findByRoleName("USER");
            studentToSave.getRoleList().add(role_user);
            role_user.getUserList().add(studentToSave);
            userRepository.save(studentToSave);
            studentToSave.setMessageResponse(new MessageResponse(ResponseCode.SUCCESS.getID(),
                    "User Signup Saved Successfully!"));
            return studentToSave;

        } catch (Exception exception)
        {
            exception.printStackTrace();
            student.setMessageResponse(errorHandlingService.GetErrorMessage(exception.getMessage()));
            return student;
        }
    }

    private  static boolean isValidPassword(String password)
    {

        // Regex to check valid password.
        String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";

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

    public static boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
