package com.ludussquare.mmonline.server.tests.models;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fakemongo.Fongo;
import com.ludussquare.mmonline.server.models.SessionModel;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class UserModelTest {

	private static Mongo mongo;
	private static Fongo fongo;
	private static UserModel userModel;
	private static SessionModel sessionModel;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fongo = new Fongo("mm-online-fongo");
		mongo = new Mongo(fongo);
		userModel = new UserModel(mongo);
		sessionModel = new SessionModel(mongo);
	}
	
	@Test
	public void testCreate () {
		// The test username and password.
		String username = "usernameTest";
		String password = "passwordTest";
		
		// Try to register the user and log the result.
		int result = userModel.registerUser(username, password);

		User user = userModel.getByUsername(username);
		
		// Test if we get a 0, meaning nothing went wrong.
		assertEquals(0, result, 0);
		assertNotNull(user.getId());
		
		// Test if we get the mock data we registered, back.
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());
		assertEquals(0, user.getColor());
		assertEquals(0, user.getLevel());
		assertEquals(0, user.getRoom());
		assertEquals(0f, user.getX(), 0f);
		assertEquals(0f, user.getY(), 0f);
	}
	
	@Test
	public void testCreateUsernameBlank () {
		// The test username and password.
		String username = "";
		String password = "passwordTest";
		
		// Try to register the user and log the result.
		userModel.registerUser(username, password);
		int result = userModel.registerUser(username, password);

		// Test if we get a 1, meaning this username is blank.
		assertEquals(1, result, 0);
	}
	
	@Test
	public void testCreatePasswordBlank () {
		// The test username and password.
		String username = "usernameTest";
		String password = "";
		
		// Try to register the user and log the result.
		userModel.registerUser(username, password);
		int result = userModel.registerUser(username, password);

		// Test if we get a 1, meaning this username is blank.
		assertEquals(2, result, 0);
	}
	
	@Test
	public void testCreateNotUnique () {
		// The test username and password.
		String username = "usernameTest1";
		String password = "passwordTest1";
		
		// Try to register the user and log the result.
		userModel.registerUser(username, password);
		int result = userModel.registerUser(username, password);

		// Test if we get a 1, meaning this username already exists and won't be registered.
		assertEquals(3, result, 0);
	}
	
	@Test
	public void testDelete () {
		// The test username and password.
		String username = "usernameDelete";
		String password = "passwordDelete";
		
		// Try to register the user.
		userModel.registerUser(username, password);
		
		// Try to create a session.
		String sessionId = sessionModel.registerSession(username, password);
		
		int result = userModel.deleteUser(sessionId);
		
		System.out.print(result);

		// Test if we get a 0, meaning nothing went wrong.
		assertEquals(0, result, 0);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mongo.getClient().close();
	}

}
