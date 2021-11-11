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
//	****** Test case numbers are as per USECASE# from Product Backlog for traceability ******
//	Following JUnits need to pass at Dev environment to be deployed to Test environment

	//	Use Case 3: Enter valid user,email and password, I should be able to register.
	//	I should get the message "Student registration successful!".
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

	//	Use Case 3.2.1: If username < 8 chars, I should NOT be able to register. I should get the message
	//	"Student registration failure! Username should be 8 or more characters long.".
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

	//	Use Case 3.2.2: If username already exists, I should NOT be able to register. I should get the
	//	message "Student registration failure! This username is already taken by another user.
	//	Please try some other username.".
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

	//	Use Case 3.2.3: If username is blank, I should NOT be able to register. I should get the message
	//	"Student registration failure! Username cannot be blank.".
	@Test
	public void testNegativeBlankUserName() throws Exception {
		User expectedStudent=userService.save(new User("","sourav123","sourav@gmail.com"));
		String returnMessage=messageService.showMessage(languageId,"STUDENT_USERNAME_BLANK");
		assertEquals(expectedStudent.getMessageResponse().getMessage(),returnMessage);
	}

	//	Use Case 3.3.1: If password < 8 characters, I should NOT be able to register. Should see message:
	//	"Student registration failure! Invalid password. Password should be 8 to 20 characters long.".
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

	//	Use Case 3.3.2: If password > 20 charsacters, I should NOT be able to register. Should see message:
	//	"Student registration failure! Invalid password. Password should be 8 to 20 characters long."
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

	//	Use Case 3.3.3: password containing "username" string,I should not be able to register. Message:
	//	"Student registration failure! Invalid password. Password should not contain your username."
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

	//	Use Case 3.3.4.1: Invalid password NOT containing small alphabet (a-z),
	//	I should not be able to register. Message:
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z),
	//	at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character
	//	among (@,#,$,%,^,&,+,=)"
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

	//	Use Case 3.3.4.2: Invalid password NOT containing CAPITAL alphabet (A-Z),
	//	I should not be able to register. Message:
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z),
	//	at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character
	//	among (@,#,$,%,^,&,+,=)"
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

	//	Use Case 3.3.4.3: Invalid password NOT containing numeric (0-9),
	//	I should not be able to register. Message:
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z),
	//	at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character
	//	among (@,#,$,%,^,&,+,=)"
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

	//	Use Case 3.3.4.4: Invalid password NOT containing CAPITAL special character among (@,#,$,%,^,&,+,=)
	//	I should not be able to register. Message:
	//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z),
	//	at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character
	//	among (@,#,$,%,^,&,+,=)"
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

	//	Use Case 3.4.1: If email is invalid, =>
	//1. Does not contain exactly one '@' character.
	//2. Does not contain one or more '.' characters after the '@' character.
	//3. Does not contain any alphabet (a-z, A-Z) before '@'.
	//4. Does not contain any alphabet (a-z, A-Z) in between '@' and '.'.
	//5. Does not contain any alphabet (a-z, A-Z) after the last '.' character.
	// I should NOT be able to register. with the following message:
	//	"Student registration failure! Invalid email address."
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

	//	Use Case 3.4.2: If email already exists, I should NOT be able to register. I should get the message:
	//	"Student registration failure! An user with the same email address is already registered.".
	@Test
	public void testNegativeDuplicateEmail() throws Exception
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

		userName="pinakidas";
		userPassword="pinaki4*67$#LB1";
		userEmail="dhiraj@gmail.com";
		inputUser=new User(userName,userPassword,userEmail);

		User newStudent=userService.save(inputUser);
		String returnMessage=messageService.showMessage(languageId,"STUDENT_DUPLICATE_EMAIL_ADDRESS");
		assertEquals(newStudent.getMessageResponse().getMessage(),
				returnMessage);
	}

//	*******************************************************************************
//					      Utility functions for unit test cases
//	*******************************************************************************

//	1. Random number generation
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
