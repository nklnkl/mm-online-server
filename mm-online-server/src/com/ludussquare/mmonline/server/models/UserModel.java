package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class UserModel {
	private Mongo mongo;
	private Query<User> query;
	private List<User> list;
	
	public UserModel (Mongo mongo) {
		this.mongo = mongo;
	}
	
	// Looks for a user by username and password
	public User getByUsernameAndPassword (String username, String password) {
		query = mongo.getDatastore().createQuery(User.class)
				.filter("username", username)
				.filter("password", password);
		list = query.asList();
		return list.get(0);
	}
	
	// Looks for a user by username
	public User getByUsername (String username) {
		query = mongo.getDatastore().createQuery(User.class)
				.filter("username", username);
		list = query.asList();
		return list.get(0);
	}
	
	// Looks for users in a specific room.
	public User getByRoom (int room) {
		query = mongo.getDatastore().createQuery(User.class)
				.filter("room", room);
		list = query.asList();
		return list.get(0);
	}
	
	// Looks for users of a specific level.
	public User getByLevel (int level) {
		query = mongo.getDatastore().createQuery(User.class)
				.filter("level", level);
		list = query.asList();
		return list.get(0);
	}
}
