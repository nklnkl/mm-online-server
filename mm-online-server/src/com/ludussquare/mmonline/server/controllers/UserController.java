package com.ludussquare.mmonline.server.controllers;

import java.util.List;

import com.google.gson.Gson;
import com.ludussquare.mmonline.server.models.SessionModel;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.Session;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

import spark.Request;
import spark.Response;
import spark.Route;


public class UserController {
	
	private UserModel userModel;
	private SessionModel sessionModel;
	private Gson gson;
	
	public UserController(Mongo mongo) {
		this.userModel = new UserModel(mongo);
		this.sessionModel = new SessionModel(mongo);
		gson = new Gson();
		listRoom();
		registerUser();
		getUserById();
		updateUser();
		deleteUser();
	}
	
	private void updateUser() {
		spark.Spark.patch("/users", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Cache body json from req.body.
				String bodyJson = req.body();
				
				// Cache sessionId from headers.
				String sessionId = req.headers("session");
				
				// The session to be used for this request.
				Session session = sessionModel.getById(sessionId);
				
				// If session is not null, proceed normall, otherwise set res.status to 401.
				if (session != null) {
					
					// Get user to be updated, by using the retrieved session.
					User user = session.getUser();
					
					// Using req.body, create user update schema.
					User userUpdate = gson.fromJson(bodyJson, User.class);
					
					// Run update and check for success.
					boolean success = userModel.update(user, userUpdate);
					
					if (success) {
						res.status(200);
					} else {
						res.status(500);
					}
				}
				else {
					res.status(404);
				}

				res.type("application/json");
				return res;
			}
		});
	}
	
	private void deleteUser() {
		spark.Spark.patch("/users", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Cache session id from headers.
				String sessionId = req.headers("session");
				
				// The session to be used for this request.
				Session session = sessionModel.getById(sessionId);
				
				// If session is not null, continue. Otherwise set res.status to 401.
				if (session != null) {
					
					// Get user using session.
					User user = session.getUser();
					
					// Using user model, delete user. Check success
					boolean success = userModel.delete(user);
					
					if (success) {
						res.status(200);
					} else {
						res.status(500);
					}
					
					// 
				} else {
					res.status(401);
				}
				
				res.type("application/json");
				return res;
			}
		});
	}
	
	private void registerUser () {
		spark.Spark.post("/users", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Get json string from request.
				String jsonString = req.body();
				
				// Parse json string into player schema.
				User user = gson.fromJson(jsonString, User.class);
				
				// Use model to create user from temporary buffer. Store result hex id.
				userModel.create(user);
				
				// Look up user to see if it saved.
				User confirmUser = userModel.getByUsernameAndPassword(user.getUsername(), user.getPasssword());
				
				// If there is a hex id, meaning it worked, return a 200.
				if (confirmUser != null) {
					res.status(200);
				} else {
					res.status(500);
				}

				res.type("application/json");
				return res;
			}
		});
	}
	
	private void getUserById () {
		spark.Spark.get("/users/:id", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Parse user id in url request.
				String userId = req.params("id");
				
				// Use model to retrieve user schema.
				User user = userModel.getById(userId);
				
				// If user was found, set 200, otherwise set 404.
				if (user != null) {
					res.status(200);
				} else {
					res.status(404);
				}
				
				// Parse user into json string.
				String userString = gson.toJson(user);
				
				// Add user json string to res body and set type.
				res.type("application/json");
				res.body(userString);
				
				return res;
			}
		});
	}
	
	private void listRoom() {
		spark.Spark.get("/users/room/:room", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Parse room id in url request.
				int roomId = Integer.parseInt(req.params(":room"));
				
				// Get list from model.
				List<User> users = userModel.getByRoom(roomId);
				
				// If there are users found, set 200, otherwise 404.
				if (users.size() > 0) {
					res.status(200);
				} else {
					res.status(404);
				}
				
				// Parse list into json string
				String body = gson.toJson(users);

				res.type("application/json");
				res.body(body);
				
				return res;
			}
		});
	}
	
}
