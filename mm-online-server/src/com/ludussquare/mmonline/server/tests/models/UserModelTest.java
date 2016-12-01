package com.ludussquare.mmonline.server.tests.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class UserModelTest {
	private Mongo mongo;
	private UserModel userModel;

	@Before
	public void setUp() throws Exception {
		mongo = new Mongo();
		userModel = new UserModel(mongo);
	}

	@Test
	public void test() {
		User user = userModel.getByUsernameAndPassword("username", "password");
		assertNotNull(user);
	}
}
