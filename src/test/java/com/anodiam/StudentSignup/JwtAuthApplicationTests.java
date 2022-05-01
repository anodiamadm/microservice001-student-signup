package com.anodiam.StudentSignup;

import com.anodiam.StudentSignup.model.Permission;
import com.anodiam.StudentSignup.model.Role;
import com.anodiam.StudentSignup.model.User;
import com.anodiam.StudentSignup.model.common.ResponseCode;
import com.anodiam.StudentSignup.serviceRepository.Permission.PermissionService;
import com.anodiam.StudentSignup.serviceRepository.Role.RoleService;
import com.anodiam.StudentSignup.serviceRepository.User.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

@SpringBootTest
class JwtAuthApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private PermissionService permissionService;

	@Autowired
	private RoleService roleService;

//*******************************************************************************************
//	JUNIT TEST CASES for microservice001-student-signup Test Based Development Starts Here
//*******************************************************************************************

	//	User should NOT be able to register by entering invalid email.
	//  1. Does not contain exactly one '@' character.
	//  1.1 Contain 2 '@' characters.
	//  2. Does not contain one or more '.' characters after the '@' character.
	//  3. Does not contain any character before '@'.
	//  4. Does not contain any character in between '@' and '.'.
	//  5. Does not contain any alphabet (a-z, A-Z) after the last '.' character.
	//  User should NOT be able to register. with the following message:
	//	ERR: Microsvc001: Student Signup: Invalid Username / Email format used for Sign Up!

	@Test
	public void testNegativeFindByInvalidRoleName() throws Exception
	{
		String roleName="INVALID_ROLE";
		Role role = roleService.findByRoleName(roleName).get();
		assertEquals(ResponseCode.ROLE_NAME_INVALID.getMessage(),
				role.getMessageResponse().getMessage());
	}

	@Test
	public void testPositiveFindByValidRoleName() throws Exception
	{
		String roleName="USER";
		Role role = roleService.findByRoleName(roleName).get();
		assertEquals(ResponseCode.ROLE_NAME_EXISTS.getMessage(),
				role.getMessageResponse().getMessage());
	}


	@Test
	public void testNegativeFindByInvalidPermissionName() throws Exception
	{
		String permissionName="INVALID_ACCESS";
		Permission permission = permissionService.findByPermissionName(permissionName).get();
		assertEquals(ResponseCode.PERMISSION_NAME_INVALID.getMessage(),
				permission.getMessageResponse().getMessage());
	}

	@Test
	public void testPositiveFindByValidPermissionName() throws Exception
	{
		String permissionName="STUDENT_ACCESS";
		Permission permission = permissionService.findByPermissionName(permissionName).get();
		assertEquals(ResponseCode.PERMISSION_NAME_EXISTS.getMessage(),
				permission.getMessageResponse().getMessage());
	}

	@Test
	public void testNegativeInvalidEmail1() throws Exception
	{
		String email="invalidemail.noAt.gmail.com";
		String password="dhsa67$#LB1";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.EMAIL_FORMAT_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}
	@Test
	public void testNegativeInvalidEmail1_1() throws Exception
	{
		String email="invalidemail.twoAts@gma@il.com";
		String password="dhsa67$#LB1";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.EMAIL_FORMAT_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}
	@Test
	public void testNegativeInvalidEmail2() throws Exception
	{
		String email="invalidemail.noDotAfterAt@gmail";
		String password="dhsa67$#LB1";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.EMAIL_FORMAT_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}
	@Test
	public void testNegativeInvalidEmail3() throws Exception
	{
		String email="@gmail.com";
		String password="dhsa67$#LB1";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.EMAIL_FORMAT_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}
	@Test
	public void testNegativeInvalidEmail4() throws Exception
	{
		String email="invalidemail.noCharacterBetweenAndDot@.com";
		String password="dhsa67$#LB1";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.EMAIL_FORMAT_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}
	@Test
	public void testNegativeInvalidEmail5() throws Exception
	{
		String email="invalidemail.noAlphabetAfterLastDot@gmail.99";
		String password="dhsaj67$#LB1";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.EMAIL_FORMAT_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	User should NOT be able to register by entering existing / duplicate email.
	//	email id - pinaki.sen@gmail.com already exists
	//	"ERR: Microsvc001: Student Signup: Username / Email used for Sign Up is already registered!".
	@Test
	public void testNegativeDuplicateEmail() throws Exception
	{
		String email="pinaki.sen@gmail.com";
		String password="ABcd@12";
		User inputUser=new User(email, password);
		User newStudent=userService.save(inputUser);
		assertEquals(ResponseCode.USER_ALREADY_EXISTS.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	If password < 6 characters, user should NOT be able to register.
	//	"ERR: Microsvc001: Student Signup: Password cannot be < 6 characters!"
	@Test
	public void testNegativeShortPassword() throws Exception
	{
		String email="abcd.smallpassword@gmail.com";
		String password="a@12A";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.PASSWORD_SHORT.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	If password > 100 charsacters, user should NOT be able to register. Should see message:
	//	"ERR: Microsvc001: Student Signup: Password must be 6 to 100 characters long and contain capital
	//	alphabet, number and special character!"
	@Test
	public void testNegativeLongPassword() throws Exception
	{
		String email="abcd.bigpassword@gmail.com";
		String password="0123456qwertyABCD@#$0123456qwertyABCD@#$0123456qwertyABCD@#$0123456qwertyABCD@#$0123456qwertyABCD@#$x";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.PASSWORD_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	If password does not contain characters [a-z], user should NOT be able to register: Message:
	//	"ERR: Microsvc001: Student Signup: Password must be 6 to 100 characters long and contain capital
	//	alphabet, number and special character!"
	@Test
	public void testNegativePasswordNotContainsSmallChar() throws Exception {
		String email = "password.without.a-z@gmail.com";
		String password = "ABCD@$123";
		User inputUser = new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.PASSWORD_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	If password does not contain characters [A-Z], user should NOT be able to register: Message:
	//	"ERR: Microsvc001: Student Signup: Password must be 6 to 100 characters long and contain capital
	//	alphabet, number and special character!"
	@Test
	public void testNegativePasswordNotContainsCapsChar() throws Exception
	{
		String email = "password.without.A-Z@gmail.com";
		String password = "abcd@$123";
		User inputUser = new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.PASSWORD_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	If password does not contain numerals [0-9], user should NOT be able to register: Message:
	//	"ERR: Microsvc001: Student Signup: Password must be 6 to 100 characters long and contain capital
	//	alphabet, number and special character!"
	@Test
	public void testNegativePasswordNotContainsNumericChar() throws Exception
	{
		String email = "password.without.0-9@gmail.com";
		String password = "abcdABCD@$";
		User inputUser = new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.PASSWORD_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	If password does not contain special chars, user should NOT be able to register: Message:
	//	"ERR: Microsvc001: Student Signup: Password must be 6 to 100 characters long and contain capital
	//	alphabet, number and special character!"
	@Test
	public void testNegativePasswordNotContainsSpecialChar() throws Exception
	{
		String email = "password.withoutSplChar@gmail.com";
		String password = "abcdABCD12";
		User inputUser = new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.PASSWORD_INVALID.getMessage(), newStudent.getMessageResponse().getMessage());
	}

	//	User should be able to register by entering valid email and password.
	//	Message: "SUCCESS: Microsvc001: Student Signup: Student registration successful!".
	@Test
	public void testPositiveSuccessfulRegistration() throws Exception
	{
		String randomNumber=GenerateRandomNumber();
		String preFixEmail="pinaki".concat(randomNumber.substring(1,6));
		String email=preFixEmail.concat(".sen@gmail.com");
		String password="Pinaki@12";
		User inputUser=new User(email, password);
		User newStudent = userService.save(inputUser);
		assertEquals(ResponseCode.SUCCESS.getMessage(), newStudent.getMessageResponse().getMessage());
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
