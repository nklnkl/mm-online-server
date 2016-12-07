package com.ludussquare.mmonline.server.controllers;

import java.util.List;

import com.google.gson.Gson;
import com.ludussquare.mmonline.server.models.UserModel;
import com.ludussquare.mmonline.server.schemas.User;
import com.ludussquare.mmonline.server.services.Mongo;
import com.mongodb.util.JSON;

import spark.Request;
import spark.Response;
import spark.Route;


public class UserController {
	
	private UserModel userModel;
	private Gson gson;
	
	public UserController(Mongo mongo) {
		this.userModel = new UserModel(mongo);
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
			public Object handle(Request arg0, Response arg1) throws Exception {
				
				// Get json string of session token from req headers.
				
				// Pass session token json string to Session model to auth.
				// This also returns a user object id, store it.
				
				// If auth, proceed normally
				
					// Find user by using the user objectId.
				
					// Using req.body, create UserUpdate schema
				
					// Pass user to be updated and userUpdate to userModel.update
					// Use returned boolean to check success
				
					// If successful, set to 200 ok
				
					// Otherwise set to 500 server error
				
				// Else set res status to 401 unauthorized
				
				return null;
			}
		});
	}
	
	private void deleteUser() {
		spark.Spark.patch("/users", new Route() {
			@Override
			public Object handle(Request arg0, Response arg1) throws Exception {
				
				// Get json string of session token from req headers.
				
				// Pass session token json string to Session model to auth.
				// This also returns a user object id, store it.
				
				// If auth, proceed normally
				
					// Pass user id to userModel.delete
				
					// If it returns true, set status to 200 ok
				
					// Otherwise set status to 500 server error
				
				// Else set res status to 401 unauthorized
				
				return null;
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
				User newUser = gson.fromJson(jsonString, User.class);
				
				// Use model to create user from temporary buffer. Store result hex id.
				String result = userModel.create(newUser.getUsername(), newUser.getPasssword());
				
				// If there is a hex id, meaning it worked, return a 200.
				if (result != null) {
					res.status(200);
				} else {
					res.status(500);
				}
				
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
				
				// Parse list into json string
				String body = gson.toJson(users);

				res.type("application/json");
				res.body(body);
				
				return res;
			}
		});
	}
	
}
