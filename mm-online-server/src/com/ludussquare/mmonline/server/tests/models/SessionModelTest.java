package com.ludussquare.mmonline.server.tests.models;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ludussquare.mmonline.server.models.SessionModel;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class SessionModelTest {

	private static Mongo mongo;
	private static SessionModel sessionModel;
	private static UserModel userModel;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongo = new Mongo();
		sessionModel = new SessionModel(mongo);
		userModel = new UserModel(mongo);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mongo.getClient().close();
	}
	
	@Test
	public void createAndDelete() {
		
		// Create user.
		String username = "usernameTest";
		String password = "passwordTest";
		User user = userModel.create(username, password);
		
		// Using user, create session.
		Session session = sessionModel.create(user);
		
		// Test that the user was created.
		assertNotNull(user.getId());
		
		// Delete user, and store the result.
		boolean deleted = sessionModel.delete(session);
		
		// Test the result of the using being deleted.
		assertTrue(deleted);
	}

}
