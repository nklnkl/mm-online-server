package com.ludussquare.mmonline.server.models;


import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;
import com.mongodb.WriteResult;

public class UserModel {
	
	private Mongo mongo;
	
	public UserModel (Mongo mongo) {
		this.mongo = mongo;
	}
	
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
	
	public User getById (String id) {
		Query <User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("id", id);
		return query.get();
	}
	
	public User getByUsername (String username) {
		Query <User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("username", username);
		return query.get();
	}
	
	public User getByLogin (String username, String password) {
		Query <User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("username", username);
		query.filter("password", password);
		return query.get();
	}
	
	// Returns null if no users in this query was found.
	public List<User> list (int room) {
		Query <User> query = mongo.getDatastore().createQuery(User.class);
		query.filter("room", room);
		return query.asList();
	}
	
	// Returns true if the update affected anything. Returns false if the update did not successfully affect anything.
	public boolean update (User user, User userUpdate) {
		UpdateOperations<User> update = mongo.getDatastore().createUpdateOperations(User.class);
		if (userUpdate.getPassword() != null) update.set("password", userUpdate.getPassword());
		if (userUpdate.getColor() != -1) update.set("color", userUpdate.getColor());
		if (userUpdate.getRoom() != -1) update.set("room", userUpdate.getRoom());
		if (userUpdate.getLevel() != -1) update.set("level", userUpdate.getLevel());
		if (userUpdate.getX() != -1) update.set("x", userUpdate.getX());
		if (userUpdate.getY() != -1) update.set("y", userUpdate.getY());
		return mongo.getDatastore().update(user, update).getUpdatedExisting();
	}
	
	// Returns true if the update affected anything. Returns false if the update did not successfully affect anything.
	public boolean delete (User user) {
		// Run delete method. Store results.
		WriteResult result = mongo.getDatastore().delete(user);
		return result.isUpdateOfExisting();
	}
	
	/*
	 * 
	 */
	
	/*
	 * Returns 4 if the username is unique, but couldn't be saved.
	 * Returns 3 if the username already exists.
	 * Returns 2 if the password is blank.
	 * Returns 1 if the username is blank.
	 * Returns 0 if nothing went wrong.
	 */
	public int registerUser (String username, String password) {
		
		if (username == "") return 1;
		if (password == "") return 2;
		
		// 1. Check for uniqueness of username. If it already exists, return 1.
		User exists = getByUsername(username);
		if (exists != null) return 3;
		
		// 2. Attempt to save user. If it fails, return 2.
		User user = create(username, password);
		if (user.getId() == null) return 4;
		
		// If nothing went wrong.
		return 0;
	}
	
	/*
	 * @description: This method will attempt to update a user. It will try to validate a session by using a Session Model to
	 * find a valid session using the passed in session id.
	 * 
	 * Returns 3 if the session was found, the user was found, but the update wasn't successfully. Server error.
	 * Returns 2 if the session was found, but the user was not found. Server error.
	 * Returns 1 if the session was not found. Client error.
	 * Returns 0 if session & user was found and the user was successfully updated. 
	 */
	public int updateUser (String sessionId, User userUpdate) {
		
		// Authenticate the user, return 1 early if the session is null/invalid.
		SessionModel sessionModel = new SessionModel(mongo);
		Session session = sessionModel.getById(sessionId);
		if (session == null) return 1;
		
		// Using the session, get the user to be updated. Return 2 early if the session is null/invalid.
		User user = session.getUser();
		if (user == null) return 2;
		
		// Run the update using the update method in this model. Store in boolean;
		boolean updated = update(user, userUpdate);
		// If the update did not effect anything, return 3. Server error.
		if (!updated) return 3;
		
		// If nothing went wrong.
		return 0;
	}
	
	/*
	 * @description: This model method will delete a user using a sessionId. It will attempt to get
	 * the session from the database, get the associated user from the session, and then attempt to delete it using
	 * the user model.
	 * 
	 * Returns 3 if the session was found, the user was found, but could not be deleted.
	 * Returns 2 if the session was found, but the user was not found using the session.
	 * Returns 1 if the session was not found.
	 * Returns 0 if the session was found, the user was found, and the user was deleted.
	 */
	public int deleteUser (String sessionId) {
		
		// Authenticate the user, return 1 early if the session is null/invalid.
		SessionModel sessionModel = new SessionModel(mongo);
		Session session = sessionModel.getById(sessionId);
		if (session == null) return 1;
		
		// Using the session, get the user to be updated. Return 2 early if the session is null/invalid.
		User user = session.getUser();
		if (user == null) return 2;
		
		// Using the user associated with the session, delete it using the userModel.
		boolean deleted = delete(user);
		if (!deleted) return 3;
		
		// If nothing went wrong.
		return 0;
	}
}
