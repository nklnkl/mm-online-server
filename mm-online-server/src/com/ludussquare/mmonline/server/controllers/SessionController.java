package com.ludussquare.mmonline.server.controllers;

import com.google.gson.Gson;
import com.ludussquare.mmonline.server.models.SessionModel;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class SessionController {
	private SessionModel sessionModel;
	private UserModel userModel;
	private Gson gson;
	
	private class SessionBody {
		public String session;
	}
	
	public SessionController (Mongo mongo) {
		userModel = new UserModel(mongo);
		sessionModel = new SessionModel(mongo);
		gson = new Gson();
		registerSession();
		deleteSession();
	}
	
	private void registerSession () {
		post("/sessions", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Prep body response.
				SessionBody sessionBody = new SessionBody();
				
				// Parse username login credentials from req.body
				User user = gson.fromJson(req.body(), User.class);
				
				// Register session using login credentials and save session id.
				String sessionId = sessionModel.registerSession(user.getUsername(), user.getPassword());
				
				// If we do get a sessionId, set it in session body.
				if (sessionId != null) {
					sessionBody.session = sessionId;
				}
				
				return gson.toJson(sessionBody);
			}
		});
	}
	
	private void deleteSession () {
		delete("/sessions", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Prep body response.
				SessionBody sessionBody = new SessionBody();
				
				// Get session id from request.
				String sessionId = req.headers("session");
				
				// Delete session and save result.
				int result = sessionModel.deleteSession(sessionId);
				
				switch (result) {
					case 0:
						res.status(200);
						break;
					case 1:
						res.status(500);
						break;
					case 2:
						res.status(404);
				}
				
				return gson.toJson(sessionBody);
			}
		});
	}
	
	
}
