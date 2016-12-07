package com.ludussquare.mmonline.server.schemas;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("base")
public abstract class Base {
	@Id
	protected ObjectId id;

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getId() {
		return id;
	}
	
}
