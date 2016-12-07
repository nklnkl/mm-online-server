package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;
import com.mongodb.WriteConcern;
import com.mongodb.WriteResult;

public class UserModel {
	private Mongo mongo;
	private Query<User> query;
	private List<User> list;
	
	public UserModel (Mongo mongo) {
		this.mongo = mongo;
	}
	
	public User getById (ObjectId id) {
		// Search by id.
		query = mongo.getDatastore().createQuery(User.class)
				.filter("id", id);
		// Return first in list.
		list = query.asList();
		return list.get(0);
	}
	
	// Looks for a user by username and password
	public User getByUsernameAndPassword (String username, String password) {
		// Search by username and password.
		query = mongo.getDatastore().createQuery(User.class)
				.filter("username", username)
				.filter("password", password);
		// Return first in list.
		list = query.asList();
		return list.get(0);
	}
	
	// Looks for a user by username
	public User getByUsername (String username) {
		// Search by username.
		query = mongo.getDatastore().createQuery(User.class)
				.filter("username", username);
		// Return first in list.
		list = query.asList();
		return list.get(0);
	}
	
	// Looks for users in a specific room.
	public List<User> getByRoom (int room) {
		// Search by room.
		query = mongo.getDatastore().createQuery(User.class)
				.filter("room", room);
		// Return list.
		list = query.asList();
		return list;
	}
	
	// Looks for users of a specific level.
	public List<User> getByLevel (int level) {
		// Search by level.
		query = mongo.getDatastore().createQuery(User.class)
				.filter("level", level);
		// Return list.
		list = query.asList();
		return list;
	}
	
	// Creates a new user.
	public String create (String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPasssword(password);
		user.setColor(0);
		user.setLevel(0);
		user.setRoom(0);
		user.setX(0f);
		user.setY(0f);
		mongo.getDatastore().save(user);
		// Get the recently saved user and get its objectId as a hex string.
		return getByUsername(username).getIdHex();
	}
	
	public boolean update (User userUpdate) {
		
		// The user to perform an update on.
		User user;
		// The update to perform.
		UpdateOperations<User> update;
		// The results of the update.
		UpdateResults results;
		
		// Create update.
		update = mongo.getDatastore().createUpdateOperations(User.class);
		
		// We check for 'null' members by checking strings for "", and int by checking
		// for -1 values.
		if (userUpdate.getColor() != -1) {
			update.set("color", userUpdate.getColor());
		}
		if (userUpdate.getRoom() != -1) {
			update.set("room", userUpdate.getRoom());
		}
		if (userUpdate.getLevel() != -1) {
			update.set("level", userUpdate.getLevel());
		}
		if (userUpdate.getColor() != -1) {
			update.set("color", userUpdate.getColor());
		}
		if (userUpdate.getX() != -1) {
			update.set("x", userUpdate.getX());
		}
		if (userUpdate.getY() != -1) {
			update.set("y", userUpdate.getY());
		}
		
		// Get user to update.
		user = getById(userUpdate.getId());

		// Run update.
		results = mongo.getDatastore().update(user, update);
		
		// If anyting was updated, return true. Otherwise, return false.
		if (results.getUpdatedCount() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean delete(ObjectId id) {
		// The user to delete.
		User user;
		// Get user by ObjectId.
		user = getById(id);
		// The result of the delete.
		WriteResult result;
		
		// Run delete method. Store results. Make use later.
		result = mongo.getDatastore().delete(user);
		
		// If there was a documented affected, return true, otherwise false.
		if (result.getN() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
