package com.anodiam.StudentSignup;

import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.serviceRepository.Message.MessageService;
import com.anodiam.StudentSignup.serviceRepository.User.GeneralEncoderDecoder;
import com.anodiam.StudentSignup.serviceRepository.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

@SpringBootTest
class JwtAuthApplicationTests {

	int languageId= StudentSignupApplication.languageId;
	@Autowired
	private UserService userService;

	@Autowired
	private MessageService messageService;

	@Test
	void contextLoads() {
	}

	//*******************************************************************************************
//	JUNIT TEST CASES for microservice001-student-signup Test Based Development Starts Here
//*******************************************************************************************
//	Following JUnits need to pass at Dev environment to be deployed to Test environment

	//	Use Case 1.1: If username is blank, I should NOT be able to register. I should get the message
	//	"Student registration failure! Username should be 8 + chars long!".
	@Test
	public void testNegativeBlankUserName() throws Exception {
		User expectedStudent=userService.save(new User("","sourav123","sourav@gmail.com"));
		String returnMessage=messageService.showMessage(languageId,"STUDENT_USERNAME_BLANK");
		assertEquals(expectedStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 1.2: If username < 8 chars, I should NOT be able to register. I should get the message
    //	"Student registration failure! Username should be 8 + chars long!".
	@Test
	public void testNegativeShortUsername() throws Exception
	{
		String userName="pinakid";
		String email="pinaki.das1@gmail.com";
		String password="adcb@12AB";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_USERNAME_MIN_LENGTH");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 2.1: If password is invalid, I should NOT be able to register.
	//	password < 8 chars: "Student registration failure! Invalid password < 8 chars.".
	@Test
	public void testNegativeShortPassword() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="ad@12A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_PASSWORD_MIN_LENGTH");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 2.2: If password is invalid, I should NOT be able to register.
	//	password > 20 chars: "Student registration failure! Invalid password > 20 chars."
	@Test
	public void testNegativeLongPassword() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="123456YT&+ad@12Aqwerty";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_PASSWORD_MAX_LENGTH");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 2.3: password containing "username" string,I should not be able to register.
	//	"Student registration failure! Invalid password containing username."
	@Test
	public void testNegativePasswordContainsUsername() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="ranighosh1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_PASSWORD_CONTAIN_USERNAME");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	password NOT containing small alphabet (a-z),I should not be able to register.
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsSmallChar() throws Exception {
		String userName = "ranighosh";
		String email = "rani.ghosh695@gmail.com";
		String password = "RANI@GHOSH1A";
		User inputUser = new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_PASSWORD");
		assertEquals(newStudent.getMessageResponse().getMessage(), returnMessage);
	}

	//	password NOT containing capital alphabet (A-Z), I should not be able to register.
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsCapsChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="rani@ghosh1a";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_PASSWORD");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//  password NOT containing numeric (0-9) character, I should not be able to register.
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsNumericChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="rani@ghoshA";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_PASSWORD");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//  password NOT containing special character among (@,#,$,%,^,&,+,=), I should not be able to register.
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsSpecialChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="gfdhtyws1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_PASSWORD");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 3.5: If email is invalid, I should NOT be able to register. with the following message
	//	"Student registration failure! Invalid email format."
	@Test
	public void testNegativeInvalidEmail1() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695gmail.com";
		String password="dhiraj67$#LB1";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_EMAIL_ADDRESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	@Test
	public void testNegativeInvalidEmail2() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@";
		String password="dhiraj67$#LB1";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_EMAIL_ADDRESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	@Test
	public void testNegativeInvalidEmail3() throws Exception
	{
		String userName="ranighosh";
		String email="@gmail.com";
		String password="dhiraj67$#LB1";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_EMAIL_ADDRESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	@Test
	public void testNegativeInvalidEmail4() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail";
		String password="dhiraj67$#LB1";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage = messageService.showMessage(languageId, "STUDENT_INVALID_EMAIL_ADDRESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 1.3: If username already exists, I should NOT be able to register. I should get the message
	//	"Student registration failure! Duplicate username!".
	@Test
	public void testNegativeDuplicateUsername() throws Exception
	{
		String userName="Dhirajkumar";
		String enocdedUserName=new GeneralEncoderDecoder().encrypt(userName);
		String userPassword="dhiraj67$#LB1";
		String userEmail="dhiraj@gmail.com";
		User inputUser=new User(userName,userPassword,userEmail);

		if (userService.findByUsername(enocdedUserName)==null)
		{
			User firstStudent = userService.save(inputUser);
		}

		userName="Dhirajkumar";
		userPassword="vinay4*67$#LB1";
		userEmail="vinay@gmail.com";
		inputUser=new User(userName,userPassword,userEmail);

		User newStudent=userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_DUPLICATE_USERNAME");
		assertEquals(newStudent.getMessageResponse().getMessage(),
				returnMessage);
	}

	//	Use Case 1.3: If email already exists, I should NOT be able to register. I should get the message
	//	"Student registration failure! Duplicate email address.".
	@Test
	public void testNegativeDuplicateEmail() throws Exception
	{
		String randomNumber=GenerateRandomNumber();
		String userName="adas".concat(randomNumber);
		String preFixEmail="abc".concat(randomNumber.substring(1,6));
		String email=preFixEmail.concat(".das@gmail.com");
		String password="klngxc@12AB";

		User inputUser=new User(userName,password,email);
		String enocdedUserName=new GeneralEncoderDecoder().encrypt(userName);
		if (userService.findByUsername(enocdedUserName)==null)
		{
			User firstStudent = userService.save(inputUser);
		}

		userName="phjk".concat(randomNumber);
		password="hgtyu@12AB";
		inputUser=new User(userName,password,email);

		User newStudent=userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_DUPLICATE_EMAIL_ADDRESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),
				returnMessage);
	}

	//	Use Case 1: Enter valid user,email and password, I should be able to register. I should get the message
	//	"Student registration successful!".
	@Test
	public void testPositiveSuccessfulRegistration() throws Exception
	{
		String randomNumber=GenerateRandomNumber();
		String userName="adas".concat(randomNumber);
		String preFixEmail="abc".concat(randomNumber.substring(1,6));
		String email=preFixEmail.concat(".das@gmail.com");
		String password="klngxc@12AB";

		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_SAVE_SUCCESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),returnMessage);
	}

	private String GenerateRandomNumber()
	{
		try
		{
			return String.valueOf(ThreadLocalRandom.current().nextInt());
		}catch(Exception ex)
		{
			System.out.println(ex.getMessage());
			return "";
		}
	}

}
