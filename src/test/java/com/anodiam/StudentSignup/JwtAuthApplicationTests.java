package com.anodiam.StudentSignup;

import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.model.common.MessageResponse;
import com.anodiam.StudentSignup.model.common.ResponseCode;
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
	public void testByDuplicateUserName() throws Exception {
		User expectedStudent=userService.save(new User("sourav","sourav123","sourav@gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "User name already exist!");
	}

	@Test
	public void testByInvalidPassword() throws Exception {
		User expectedStudent=userService.save(new User("sourav1","sourav123","sourav@gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Invalid Password!");
	}

	@Test
	public void testByInvalidEmail() throws Exception {
		User expectedStudent=userService.save(new User("sourav1","sourav123@#X","sourav @gmail.com"));
		assertEquals(expectedStudent.getMessageResponse().getMessage(), "Invalid Email Id!");
	}

}
