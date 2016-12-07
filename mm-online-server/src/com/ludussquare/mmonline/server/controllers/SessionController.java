package com.ludussquare.mmonline.server.controllers;

import com.google.gson.Gson;
import com.ludussquare.mmonline.server.models.SessionModel;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

import spark.Request;
import spark.Response;
import spark.Route;

public class SessionController {
	
	private SessionModel sessionModel;
	private UserModel userModel;
	private Gson gson;
	
	public SessionController (Mongo mongo) {
		sessionModel = new SessionModel(mongo);
		userModel = new UserModel(mongo);
		gson = new Gson();
		
		create();
		delete();
	}
	
	private void create() {
		spark.Spark.post("/session", new Route() {
			
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Cache body json.
				String bodyJson = req.body();
				
				// Parse body json into user.
				User user = gson.fromJson(bodyJson, User.class);
				
				String username = user.getUsername();
				String password = user.getPasssword();
				
				// Check for user existence/auth.
				user = userModel.getByUsernameAndPassword(username, password);
				
				// If not null, the user exists and the login information is correct.
				if (user != null) {
					
					// Create new session.
					Session session = sessionModel.createSession(user);
					
					// If it has an ObjectId meaning it worked.
					if (session.getId() != null) {
						
						// Set 200.
						res.status(200);
						
						// Create string response body.
						String response = "{'session':'" + session.getIdHex() + "'}";
						
						// Set body.
						res.body(response);
						
					} else {
						res.status(500);
					}
				} else {
					res.status(401);
				}
				
				return res;
			}
		});
	}
	
	private void delete() {
		spark.Spark.delete("/session", new Route() {
			
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Cache session from header.
				String sessionId = req.headers("session");
				
				// Get session using sessionModel.
				Session session = sessionModel.getById(sessionId);
				
				// If session exists, proceed to delete, otherwise set 404.
				if (session != null) {
					
					// Delete session using sessionModel.
					boolean success = sessionModel.deleteSession(session);
					
					// If it successfully deleted, set 200, otherwise 500.
					if (success) {
						res.status(200);
					} else {
						res.status(500);
					}
				} else {
					res.status(404);
				}
				
				return res;
			}
		});
	}

}
