package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;
import com.mongodb.WriteResult;

public class SessionModel {
	private Mongo mongo;
	private Query<Session> query;
	private List<Session> list;
	
	public SessionModel (Mongo mongo) {
		this.mongo = mongo;
	}
	
	public boolean auth (String id) {
		query = mongo.getDatastore().
				createQuery(Session.class).
				filter("id", id);
		
		list = query.asList();
		
		
		if (list.size() == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	public Session getById (String id) {
		query = mongo.getDatastore().
				createQuery(Session.class).
				filter("id", id);
		
		list = query.asList();
		
		return list.get(0);
	}
	
	public Session getByUser (User user) {
		query = mongo.getDatastore().
				createQuery(Session.class).
				filter("id", user);
		
		list = query.asList();
		
		return list.get(0);
	}
	
	public Session createSession (User user) {
		Session session = new Session();
		session.setUser(user);
		mongo.getDatastore().save(session);
		return session;
	}
	
	public boolean deleteSession (Session session) {
		
		WriteResult result = mongo.getDatastore().delete(session);
		
		if (result.getN() > 0) {
			return true;
		} else {
			return false;
		}
	}
}
