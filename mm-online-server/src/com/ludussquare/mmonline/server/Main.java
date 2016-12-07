package com.ludussquare.mmonline.server;

import com.ludussquare.mmonline.server.controllers.UserController;
import com.ludussquare.mmonline.server.services.Mongo;
import spark.Spark.*;

public class Main {

	private static Mongo mongo;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		spark.Spark.port(getPort());
		mongo = new Mongo();
		new UserController(mongo);
	}
	
	static int getPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

}
