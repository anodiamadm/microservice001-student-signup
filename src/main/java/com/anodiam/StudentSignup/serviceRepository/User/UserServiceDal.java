package com.anodiam.StudentSignup.serviceRepository.User;

import com.anodiam.StudentSignup.StudentSignupApplication;
import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.model.common.MessageResponse;
import com.anodiam.StudentSignup.model.common.ResponseCode;
import com.anodiam.StudentSignup.serviceRepository.Message.MessageService;
import com.anodiam.StudentSignup.serviceRepository.Role.RoleRepository;
import com.anodiam.StudentSignup.serviceRepository.errorHandling.ErrorHandlingService;
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
    private ErrorHandlingService errorHandlingService;

    @Autowired
    private MessageService messageService;

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
        int language_Id = StudentSignupApplication.languageId;
        String returnMessage="";
        try
        {
            if(student.getUsername().trim().length() == 0)
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_USERNAME_BLANK");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        returnMessage));
                return student;
            }
            if(student.getUsername().trim().length() < 8)
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_USERNAME_MIN_LENGTH");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        returnMessage));
                return student;
            }

            if(student.getPassword().trim().length() < 8)
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_PASSWORD_MIN_LENGTH");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(), returnMessage));
                return student;
            }
            if(student.getPassword().trim().length() > 20)
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_PASSWORD_MAX_LENGTH");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(), returnMessage));
                return student;
            }
            if(student.getPassword().contains(student.getUsername()))
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_PASSWORD_CONTAIN_USERNAME");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(), returnMessage));
                return student;
            }
            if (!isValidPassword(student.getPassword()))
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_INVALID_PASSWORD");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        returnMessage));
                return student;
            }
            if (!isValidEmail(student.getEmail()))
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_INVALID_EMAIL_ADDRESS");
                student.setMessageResponse(new MessageResponse(ResponseCode.FAILURE.getID(),
                        returnMessage));
                return student;
            }

            String enocdedUserName=new GeneralEncoderDecoder().encrypt(student.getUsername());
            if(userRepository.findByUsername(enocdedUserName) !=null)
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_DUPLICATE_USERNAME");
                student.setMessageResponse(new MessageResponse(ResponseCode.DUPLICATE.getID(),
                        returnMessage));
                return student;
            }

            String enocdedEmail=new GeneralEncoderDecoder().encrypt(student.getEmail());
            if(userRepository.findByEmail(enocdedEmail) !=null)
            {
                returnMessage=messageService.showMessage(language_Id,"STUDENT_DUPLICATE_EMAIL_ADDRESS");
                student.setMessageResponse(new MessageResponse(ResponseCode.DUPLICATE.getID(),
                        returnMessage));
                return student;
            }

            String encodedPassword = passwordEncoder.encode(student.getPassword());
            User studentToSave = new User(enocdedUserName, encodedPassword,enocdedEmail);
            Role role_user = roleRepository.findByRoleName("USER");
            studentToSave.getRoleList().add(role_user);
            role_user.getUserList().add(studentToSave);
            userRepository.save(studentToSave);
            returnMessage=messageService.showMessage(language_Id,"STUDENT_SAVE_SUCCESS");
            studentToSave.setMessageResponse(new MessageResponse(ResponseCode.SUCCESS.getID(),
                    returnMessage));
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

    private static boolean isValidEmail(String email)
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
