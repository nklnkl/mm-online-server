package com.ludussquare.mmonline.server.tests.models;

import static org.junit.Assert.*;

import org.bson.types.ObjectId;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class UserModelTest {

	private static Mongo mongo;
	private static UserModel userModel;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		mongo = new Mongo();
		userModel = new UserModel(mongo);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		mongo.getClient().close();
	}

	@Test
	public void testCreate() {
		String username = "usernameTest";
		String password = "passwordTest";
		
		User user = userModel.create(username, password);
		
		assertNotNull(user.getId());
		assertEquals(username, user.getUsername());
		assertEquals(password, user.getPassword());
		assertEquals(0, user.getColor());
		assertEquals(0, user.getRoom());
		assertEquals(0f, user.getX(), 0f);
		assertEquals(0f, user.getY(), 0f);
		
		userModel.delete(user);
	}
	
	@Test
	public void testGetByObjectId() {

		// Origin values.
		String username = "usernameTest";
		String password = "passwordTest";
		
		// The origin.
		User user = userModel.create(username, password);
		
		// Get object id of newly created user.
		ObjectId id = user.getId();
		
		// Get by object id.
		user = userModel.get(id);
		
		// Test
		assertNotNull(user);
		
		// Clean up.
		userModel.delete(user);
	}

	@Test
	public void testUpdate() {
		
		// The origin.
		String username = "usernameTest";
		String password = "passwordTest";
		
		// Create origin
		User user = userModel.create(username, password);
		
		// The new values.
		String passwordUpdate = "testPassword";
		int color = 1;
		int level = 2;
		int room = 3;
		float x = 4f;
		float y = 5f;
		
		// Update.
		User userUpdate = new User();
		userUpdate.setPassword(passwordUpdate);
		userUpdate.setColor(color);
		userUpdate.setLevel(level);
		userUpdate.setRoom(room);
		userUpdate.setX(x);
		userUpdate.setY(y);
		
		// Run update and store success.
		boolean updated = userModel.update(user, userUpdate);

		// Test.
		assertTrue(updated);
		user = userModel.get(user.getId());
		assertEquals(passwordUpdate, user.getPassword());
		assertEquals(color, user.getColor());
		assertEquals(room, user.getRoom());
		assertEquals(x, user.getX(), 0f);
		assertEquals(y, user.getY(), 0f);
		
		// Clean up.
		userModel.delete(user);
	}

}
