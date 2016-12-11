package com.ludussquare.mmonline.server.schemas;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("base")
public abstract class Base {
	@Id
	protected String id = new ObjectId().toHexString();
	
	public Base () {}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
}
