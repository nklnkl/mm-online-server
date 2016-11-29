package com.ludussquare.mmonline.server.services;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Mongo {
	
	private Morphia morphia;
	private Datastore datastore;
	private MongoClient client;
	private MongoClientURI uri;
	private String db;
	
	public Mongo () {
		// Creates instance for morphia ORM.
		morphia = new Morphia();
		// Create mapping of models.
		morphia.mapPackage("com.ludussquare.mmonline.server.models");
		// Set the URI & DB.
		uri = new MongoClientURI("mongodb://mm-online:mmonline@ds157677.mlab.com:57677/mm-online-db");
		db = "mm-online-db";
		// Use the URI to connect to the db.
		client = new MongoClient(uri);
		// Create data store using the db.
		datastore = morphia.createDatastore(client, db);
	}

	public Morphia getMorphia() {
		return morphia;
	}

	public Datastore getDatastore() {
		return datastore;
	}

	public MongoClient getClient() {
		return client;
	}

	public MongoClientURI getUri() {
		return uri;
	}

	public String getDb() {
		return db;
	}

	public void setMorphia(Morphia morphia) {
		this.morphia = morphia;
	}

	public void setDatastore(Datastore datastore) {
		this.datastore = datastore;
	}

	public void setClient(MongoClient client) {
		this.client = client;
	}

	public void setUri(MongoClientURI uri) {
		this.uri = uri;
	}

	public void setDb(String db) {
		this.db = db;
	}
	
	
	
	
}