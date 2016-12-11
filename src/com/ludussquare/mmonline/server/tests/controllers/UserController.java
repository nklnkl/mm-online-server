package com.ludussquare.mmonline.server.tests.controllers;

import com.ludussquare.mmonline.server.services.Mongo;

public class UserController {
	private static Mongo mongo;
	public UserController(Mongo mongo) {
		this.mongo = mongo;
	}
}
