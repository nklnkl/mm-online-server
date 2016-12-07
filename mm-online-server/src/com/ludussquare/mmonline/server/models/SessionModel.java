package com.ludussquare.mmonline.server.models;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

public class SessionModel {
	private Mongo mongo;
	private Query<User> query;
	private List<User> list;
	
	public SessionModel (Mongo mongo) {
		this.mongo = mongo;
	}
}
