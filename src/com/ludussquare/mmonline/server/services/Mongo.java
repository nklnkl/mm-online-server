package com.ludussquare.mmonline.server.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.github.fakemongo.Fongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Mongo {
	
	private Morphia morphia;
	private Datastore datastore;
	private MongoClient client;
	private MongoClientURI uri;
	private String db;
	
	public Mongo (Fongo fongo) {
		
		// Use fongo to get client.
		client = fongo.getMongo();
		
		// Set db name.
		db = fongo.getDB("mm-online-db").getName();
		
		setMorphia();
	}
	
	public Mongo () {
		
		// Get uri to create MongoClient.
		uri = new MongoClientURI(System.getenv("MongoUri"));
		client = new MongoClient(uri); 	
		
		// Set db.
		db = "mm-online-db";
		
		setMorphia();
	}
	
	private void setMorphia () {
		// Creates instance for morphia ORM.
		morphia = new Morphia();
		
		// Create mapping of models.
		morphia.mapPackage("com.ludussquare.mmonline.server.models");
		
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