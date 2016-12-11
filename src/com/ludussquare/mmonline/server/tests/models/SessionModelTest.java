package com.ludussquare.mmonline.server.tests.models;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.fakemongo.Fongo;
import com.ludussquare.mmonline.server.models.SessionModel;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class SessionModelTest {

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

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mongo.getClient().close();
	}
	
	@Test
	public void registerTest () {
		// Set up user.
		String username = "usernameTest";
		String password = "passwordTest";
		
		// Register user.
		userModel.registerUser(username, password);
		
		// Use user login to register a session.
		String sessionId = sessionModel.registerSession(username, password);
		
		// Test to see if we get a session id back.
		assertNotNull("After registering, the sessionId should not be null.", sessionId);
		
		// Then use the session id to retrieve the session.
		Session session = sessionModel.getById(sessionId);
		
		// Test to see if we get a Session back.
		assertNotNull("After using the sessionId to retrieve the session, it should not be null.", session);
	}
	
	@Test
	public void deleteTest () {
		// Set up user.
		String username = "usernameTest";
		String password = "passwordTest";
		
		// Register user.
		userModel.registerUser(username, password);
		
		// Use user login to register a session.
		String sessionId = sessionModel.registerSession(username, password);
		
		// Test to see if we get a session id back.
		assertNotNull("After registering, the sessionId should not be null.", sessionId);
		
		// Then use the session id to retrieve the session.
		Session session = sessionModel.getById(sessionId);
		
		// Test to see if we get a Session back.
		assertNotNull("After using the sessionId to retrieve the session, it should not be null.", session);
		
		int result = sessionModel.deleteSession(sessionId);
		
		assertEquals("The result from deleting the session should be 0.", 0, result);
		
		session = sessionModel.getById(sessionId);
		
		assertNull("Attempting to get the session again using the session id, should return null", session);
	}

}
