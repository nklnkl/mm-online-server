package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;
import com.mongodb.WriteResult;

public class SessionModel {
	private Mongo mongo;
	
	public SessionModel (Mongo mongo) {
		this.mongo = mongo;
	}
	
	/*
	 * Level 1 - Methods that talk directly to the database and don't rely on other methods.
	 */
	public Session getById (String id) {
		Query <Session> query = mongo.getDatastore().createQuery(Session.class);
		query.filter("id", id);
		int size = query.asList().size();
		if (size < 1) return null;
		else return query.asList().get(0);
	}
	public List<Session> list (User user) {
		Query<Session> query = mongo.getDatastore().createQuery(Session.class);
		query.filter("user", user);
		int size = query.asList().size();
		if (size < 1) return null;
		else return query.asList();
	}
	public Session create (User user) {
		Session session = new Session();
		session.setUser(user);
		mongo.getDatastore().save(session);
		return session;
	}
	public boolean delete (Session session) {
		WriteResult result = mongo.getDatastore().delete(session);
		if (result.getN() > 0) return true;
		return false;
	}
	/*
	 * Level 1 - Methods that talk directly to the database and don't rely on other methods.
	 */
	
	/*
	 * Level 2 - Methods that require other methods, and don't talk directly to the database.
	 */
	
	/*
	 * Returns null if username and password were wrong.
	 * Returns an ObjectId for the new session if they were right.
	 */
	public String registerSession (String username, String password) {
		// Instance userModel.
		UserModel userModel = new UserModel(mongo);
		
		// Using user model, get the user.
		User user = userModel.getByLogin(username, password);
		
		// If the user exists (meaning the username & password were right)
		if (user != null) {
			
			// Create a new session.
			Session session = create(user);
			
			// And return the session ObjectId.
			return session.getId();
			
		} else {
			
			/// Otherwise return null.
			return null;
		}
	}
	
	/*
	 * Returns 2 if no session was found in the database, should delete the session client side regardless. (Client error)
	 * Returns 1 if the session was found but could not be deleted. (Server error)
	 * Returns 0 if the session was found and deleted. (Success)
	 */
	public int deleteSession (String id) {
		
		// Get the session using the passed ObjectId
		Session session = getById(id);
		
		// If the session could not be found.
		if (session == null) return 2;
		
		// Attempt to delete if found.
		boolean deleted = delete(session);
		
		// If the server couldn't delete it, it's a server error.
		if (!deleted) return 1;
		
		// The server found the session and successfully deleted it.
		return 0;
	}
	
	/*
	 * Level 2 - Methods that require other methods, and don't talk directly to the database.
	 */
}
