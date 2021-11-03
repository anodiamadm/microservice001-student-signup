package com.anodiam.StudentSignup;

import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.serviceRepository.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;

@SpringBootTest
class JwtAuthApplicationTests {

	@Autowired
	private UserService userService;

	@Test
	void contextLoads() {
	}

//	@Test
//	public void testString() throws Exception {
//		String expectedValue="HELLOWORLD";
//		String actualValue="HELLOWORLD";
//		assertEquals(expectedValue, actualValue);
//	}
//
//	@Test
//	public void testByBlankUserName() throws Exception {
//		User expectedStudent=userService.save(new User("","sourav123","sourav@gmail.com"));
//		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name cannot be blank!");
//	}
//
//	@Test
//	public void testByInvalidLengthUserName() throws Exception {
//		User expectedStudent=userService.save(new User("vinay","vinay%6U23L","sourav@gmail.com"));
//		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name cannot be less than eight characters!");
//	}
//
//	@Test
//	public void testByDuplicateUserName() throws Exception
//	{
//		String userName="Dhirajkumar";
//		String enocdedUserName=new GeneralEncoderDecoder().encrypt(userName);
//		String userPassword="dhiraj67$#LB1";
//		String userEmail="dhiraj@gmail.com";
//		User inputUser=new User(userName,userPassword,userEmail);
//
//		if (userService.findByUsername(enocdedUserName)==null)
//		{
//			User newStudent = userService.save(inputUser);
//		}
//		User expectedStudent=userService.save(inputUser);
//		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name already exists!");
//	}
//
//	@Test
//	public void testByInvalidPassword() throws Exception {
//		User expectedStudent=userService.save(new User("souravkumar","sourav123","sourav@gmail.com"));
//		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Invalid Password!");
//	}
//
//	@Test
//	public void testByInvalidEmail() throws Exception {
//		User expectedStudent=userService.save(new User("souravkumar","sourav123@#X","sourav @gmail.com"));
//		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Invalid Email Id!");
//	}
//
//	@Test
//	public void testByDuplicateEmail() throws Exception
//	{
//		String userName="Dhirajkumar";
//		String enocdedUserName=new GeneralEncoderDecoder().encrypt(userName);
//		String userPassword="dhiraj67$#LB1";
//		String userEmail="dhiraj@gmail.com";
//		User inputUser=new User(userName,userPassword,userEmail);
//
//		if (userService.findByUsername(enocdedUserName)==null)
//		{
//			User newStudent = userService.save(inputUser);
//		}
//
//		userName="parijatkumar";
//		userPassword="parij67$#LB1";
//		userEmail="dhiraj@gmail.com";
//		User anotherUser=new User(userName,userPassword,userEmail);
//
//		User expectedStudent=userService.save(anotherUser);
//		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Email already exists!");
//	}

//*******************************************************************************************
//	JUNIT TEST CASES for microservice001-student-signup Test Based Development Starts Here
//*******************************************************************************************
//	Following JUnits need to pass at Dev environment to be deployed to Test environment

//	Use Case 3: Given I am an unsigned user, when I fill up the fields:
//	(username, password, confirm password, email) and click on the Register button:

//	If all fields are legitimate, I should be able to register myself as a student.
//	Also, I should get the message "Student registration successful".

	@Test
	public void testPositiveSuccessfulRegistration() throws Exception
	{
		String userName="pinakidas";
		String email="pinaki.das@gmail.com";
		String password="adcb@12AB";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration successful!");
	}

//	Use Case 3.2: If username < 8 chars, I should NOT be able to register. I should get the message
//	"Student registration failure! Username should be 8 + chars long!".
	@Test
	public void testNegativeShortUsername() throws Exception
	{
		String userName="pinakid";
		String email="pinaki.das1@gmail.com";
		String password="adcb@12AB";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Username should be 8 + chars long!");
	}

//	Use Case 3.3: If username already exists, I should NOT be able to register. I should get the message
//	"Student registration failure! Duplicate username!".
	@Test
	public void testNegativeDuplicateUsername() throws Exception
	{
		String userName="pinakidas";
		String email="1pinaki.das@gmail.com";
		String password="1adcb@12AB";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Duplicate username!");
	}

//	Use Case 3.4: If password is invalid, I should NOT be able to register.

//	password < 8 chars: "Student registration failure! Invalid password < 8 chars.".
	@Test
	public void testNegativeShortPassword() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="ad@12A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password < 8 chars.");
	}

//	password > 20 chars: "Student registration failure! Invalid password > 20 chars."
	@Test
	public void testNegativeLongPassword() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="123456YT&+ad@12Aqwerty";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password > 20 chars.");
	}

//	password containing "username" string:
//	"Student registration failure! Invalid password containing username."
	@Test
	public void testNegativePasswordContainsUsername() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="$ranighosh1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password containing username.");
	}

//	password containing "email" string:
//	"Student registration failure! Invalid password containing email."
	@Test
	public void testNegativePasswordContainsEmail() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="$ranighosh1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password containing username.");
	}

//	password NOT containing small alphabet (a-z):
//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsSmallChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="RANI@GHOSH1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password. " +
						"Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), " +
						"at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)");
}

//	password NOT containing capital alphabet (A-Z):
//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsCapsChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="rani@ghosh1a";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password. " +
						"Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), " +
						"at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)");
	}

//  password NOT containing numeric (0-9) character:
//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsNumericChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="rani@ghoshA";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password. " +
						"Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), " +
						"at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)");
	}

//  password NOT containing special character among (@,#,$,%,^,&,+,=):
//	"Student registration failure! Invalid password. Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)"
	@Test
	public void testNegativePasswordNotContainsSpecialChar() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail.com";
		String password="ranighosh1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid password. " +
						"Must contain at least one small alphabet (a-z), at least one capital alphabet (A-Z), " +
						"at least one numeric (0-9) and at least one special character among (@,#,$,%,^,&,+,=)");
	}

//	Use Case 3.5: If email is invalid, I should NOT be able to register. with the following message
//	"Student registration failure! Invalid email format."
	@Test
	public void testNegativeInvalidEmail1() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695gmail.com";
		String password="ranighosh@1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid email address.");
	}

	@Test
	public void testNegativeInvalidEmail2() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@";
		String password="ranighosh@1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid email address.");
	}

	@Test
	public void testNegativeInvalidEmail3() throws Exception
	{
		String userName="ranighosh";
		String email="@gmail.com";
		String password="ranighosh@1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid email address.");
	}

	@Test
	public void testNegativeInvalidEmail4() throws Exception
	{
		String userName="ranighosh";
		String email="rani.ghosh695@gmail";
		String password="ranighosh@1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Invalid email address.");
	}

//	Use Case 3.6: If email is Duplicate, I should NOT be able to register. with the following message
//	"Student registration failure! Duplicate email."
	@Test
	public void testNegativeDuplicateEmail() throws Exception
	{
		String userName="ranighosh";
		String email="pinaki.das@gmail.com";
		String password="ranighosh@1A";
		User inputUser=new User(userName, password, email);
		User newStudent = userService.save(inputUser);
		assertEquals(newStudent.getMessageResponse().getMessage(),
				"Student registration failure! Duplicate email address.");
	}
}
