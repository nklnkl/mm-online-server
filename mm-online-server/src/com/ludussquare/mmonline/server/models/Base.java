package com.ludussquare.mmonline.server.models;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("base")
public abstract class Base {
	@Id
	protected ObjectId id;

	public ObjectId getId() {
		return id;
	}
	
	public String getIdHex() {
		return id.toHexString();
	}
	
}
