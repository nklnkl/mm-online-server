package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Test;
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
	
	public Session get (ObjectId id) {
		
		Query<Session> query = mongo.getDatastore().createQuery(Session.class);
		query.filter("id", id);
		
		List<Session> sessions = query.asList();
		
		if (sessions.size() < 1) {
			return null;
		} else {
			return sessions.get(0);
		}
	}
	
	public Session create () {
		
		// Create new session.
		Session session = new Session();
		
		// Set user for session.
		session.setUser(user);
		
		// Save session to dab.
		mongo.getDatastore().save(session);
		
		// Return session info to client.
		return session;
	}
	
	public Session create (User user) {
		
		// Create new session.
		Session session = new Session();
		
		// Set user for session.
		session.setUser(user);
		
		// Save session to dab.
		mongo.getDatastore().save(session);
		
		// Return session info to client.
		return session;
	}
	
	@Test
	public boolean delete (Session session) {
		WriteResult result = mongo.getDatastore().delete(session);
		
		// If there was a documented affected, return true, otherwise false.
		if (result.getN() > 0) {
			return true;
		} else {
			return false;
		}
		
	}
}
