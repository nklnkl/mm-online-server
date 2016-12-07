package com.ludussquare.mmonline.server.schemas;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

@Entity("sessions")
public class Session extends Base {
	@Reference
	private User user;
	
	public Session () {}
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
