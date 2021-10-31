package com.anodiam.StudentSignup;

import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.serviceRepository.User.GeneralEncoderDecoder;
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

	@Test
	public void testString() throws Exception {
		String expectedValue="HELLOWORLD";
		String actualValue="HELLOWORLD";
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void testByBlankUserName() throws Exception {
		User expectedStudent=userService.save(new User("","sourav123","sourav@gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name cannot be blank!");
	}

	@Test
	public void testByInvalidLengthUserName() throws Exception {
		User expectedStudent=userService.save(new User("vinay","vinay%6U23L","sourav@gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name cannot be less than eight characters!");
	}

	@Test
	public void testByDuplicateUserName() throws Exception
	{
		String userName="Dhirajkumar";
		String enocdedUserName=new GeneralEncoderDecoder().encrypt(userName);
		String userPassword="dhiraj67$#LB1";
		String userEmail="dhiraj@gmail.com";
		User inputUser=new User(userName,userPassword,userEmail);

		if (userService.findByUsername(enocdedUserName)==null)
		{
			User newStudent = userService.save(inputUser);
		}
		User expectedStudent=userService.save(inputUser);
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name already exists!");
	}

	@Test
	public void testByInvalidPassword() throws Exception {
		User expectedStudent=userService.save(new User("souravkumar","sourav123","sourav@gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Invalid Password!");
	}

	@Test
	public void testByInvalidEmail() throws Exception {
		User expectedStudent=userService.save(new User("souravkumar","sourav123@#X","sourav @gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Invalid Email Id!");
	}

	@Test
	public void testByDuplicateEmail() throws Exception
	{
		String userName="Dhirajkumar";
		String enocdedUserName=new GeneralEncoderDecoder().encrypt(userName);
		String userPassword="dhiraj67$#LB1";
		String userEmail="dhiraj@gmail.com";
		User inputUser=new User(userName,userPassword,userEmail);

		if (userService.findByUsername(enocdedUserName)==null)
		{
			User newStudent = userService.save(inputUser);
		}

		userName="parijatkumar";
		userPassword="parij67$#LB1";
		userEmail="dhiraj@gmail.com";
		User anotherUser=new User(userName,userPassword,userEmail);

		User expectedStudent=userService.save(anotherUser);
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Email already exists!");
	}
}
