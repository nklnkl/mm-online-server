package com.ludussquare.mmonline.server.controllers;

import com.google.gson.Gson;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;

import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

import java.util.List;

public class UserController {
	private UserModel userModel;
	private Gson gson;
	
	public UserController (Mongo mongo) {
		userModel = new UserModel(mongo);
		gson = new Gson();
		registerUser();
		getUser();
		updateUser();
		deleteUser();
		getRoom();
	}
	
	private void registerUser() {
		post("/users", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				// Parse body json string into user.
				User user = gson.fromJson(req.body(), User.class);
				
				// Register and get result/
				int result = userModel.registerUser(user.getUsername(), user.getPassword());

				// Set http status based on response from register.
				switch(result) {
					case 0:
						res.status(200);
						break;
					case 1:
						res.status(400);
						break;
					case 2:
						res.status(400);
						break;
					case 3:
						res.status(409);
						break;
					case 4:
						res.status(500);
						break;
				}
				
				return gson.toJson(null);
			}
		});
	}
	
	private void getUser () {
		get("/users/:id", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Get id from url.
				String id = req.params("id");
				
				// Get the user using the id.
				User user = userModel.getById(id);
				
				// Return to request body as a json.
				return gson.toJson(user);
			}
		});
	}
	
	private void updateUser() {
		patch("/users", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Take session id from http headers.
				String sessionId = req.headers("session");
				
				// Translate req.body json string to User.
				User userUpdate = gson.fromJson(req.body(), User.class);
				
				int result = userModel.updateUser(sessionId, userUpdate);
				
				// Set http status based on response from register.
				switch(result) {
					case 0:
						res.status(200);
						break;
					case 1:
						res.status(401);
						break;
					case 2:
						res.status(500);
						break;
					case 3:
						res.status(500);
						break;
				}
				return gson.toJson(null);
			}
		});
	}
	
	private void deleteUser () {
		delete("/users", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Take session id from http headers.
				String sessionId = req.headers("session");
				
				// Delete user and get result.
				int result = userModel.deleteUser(sessionId);
				
				// Set http status based on response from register.
				switch(result) {
					case 0:
						res.status(200);
						break;
					case 1:
						res.status(404);
						break;
					case 2:
						res.status(500);
						break;
					case 3:
						res.status(500);
						break;
				}
				return gson.toJson(null);
			}
		});
	}
	
	private void getRoom () {
		get("/users/room/:id", new Route() {
			@Override
			public Object handle(Request req, Response res) throws Exception {
				
				// Get id from url.
				int id = Integer.parseInt( req.params("id"));
				
				// Get the user using the id.
				List<User> users = userModel.list(id);
				
				// Return to request body as a json.
				return gson.toJson(users);
			}
		});
	}
}
