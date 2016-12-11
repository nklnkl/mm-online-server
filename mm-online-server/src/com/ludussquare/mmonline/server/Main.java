package com.ludussquare.mmonline.server;

import com.github.fakemongo.Fongo;
import com.ludussquare.mmonline.server.controllers.SessionController;
import com.ludussquare.mmonline.server.controllers.UserController;
import com.ludussquare.mmonline.server.services.Mongo;

import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

import static spark.Spark.*;

public class Main {

	private static Mongo mongo;
	private static Fongo fongo;
	private static UserController userController;
	private static SessionController sessionController;
	
	public static void main(String[] args) {
		
		// Set port.
		port(getPort());
		
		// Set up database.
		setDb();
		
		// Set user controller.
		userController = new UserController(mongo);
		sessionController = new SessionController(mongo);
		
		// All responses going out are set as json responses.
		after(new Filter() {
			@Override
			public void handle(Request req, Response res) throws Exception {
				res.type("application/json");
			}
		});
	}
	
	private static void setDb() {
		// Get env mode.
		String mode = System.getenv("mode");
		
		// Set up database connection.
		if (mode == "production") {
			mongo = new Mongo();
		} else {
			fongo = new Fongo("mm-online-fongo");
			mongo = new Mongo(fongo);
		}
	}
	
	private static int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
