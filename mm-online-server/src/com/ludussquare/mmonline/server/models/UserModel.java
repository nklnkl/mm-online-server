package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;
import com.mongodb.WriteResult;

public class UserModel {
	
	private Mongo mongo;
	
	public UserModel (Mongo mongo) {
		this.mongo = mongo;
	}
	
	// Returns a user using its username and password. Can also be used for auth.
	public User get (String username, String password) {
		Query<User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("username", username);
		query.filter("password", password);
		
		return query.asList().get(0);
	}
	
	public User get (ObjectId id) {
		Query<User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("id", id);
		
		return query.asList().get(0);
	}
	
	// Looks for users in a specific room.
	public List<User> get (int room) {
		// Search by room.
		Query<User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("room", room);
		query.retrievedFields(true, "username");
		
		// Return list.
		List<User> list = query.asList();
		return list;
	}
	
	// Creates a new user.
	public User create (String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setColor(0);
		user.setLevel(0);
		user.setRoom(0);
		user.setX(0f);
		user.setY(0f);
		mongo.getDatastore().save(user);
		return user;
	}
	
	public boolean update (User user, User userUpdate) {
		
		// Create update.
		UpdateOperations<User> update = mongo.getDatastore().createUpdateOperations(User.class);
		
		// We check for 'null' updates
		if (userUpdate.getPassword() != null) update.set("password", userUpdate.getPassword());
		if (userUpdate.getColor() != -1) update.set("color", userUpdate.getColor());
		if (userUpdate.getRoom() != -1) update.set("room", userUpdate.getRoom());
		if (userUpdate.getLevel() != -1) update.set("level", userUpdate.getLevel());
		if (userUpdate.getX() != -1) update.set("x", userUpdate.getX());
		if (userUpdate.getY() != -1) update.set("y", userUpdate.getY());

		// Run update.
		UpdateResults results = mongo.getDatastore().update(user, update);
		
		// If anything was updated, return true. Otherwise, return false.
		if (results.getUpdatedCount() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean delete(User user) {
		
		// Run delete method. Store results.
		WriteResult result = mongo.getDatastore().delete(user);
		
		// If there was a documented affected, return true, otherwise false.
		if (result.getN() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
