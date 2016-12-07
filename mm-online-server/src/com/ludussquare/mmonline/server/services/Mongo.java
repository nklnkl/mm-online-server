package com.ludussquare.mmonline.server.services;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

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
	
	private Properties properties;
	private InputStream input;
	
	public Mongo () {
		// Get mongo config from properties file.
		properties = new Properties();
		
		try {
			input = new FileInputStream("Mongo.properties");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			properties.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Creates instance for morphia ORM.
		morphia = new Morphia();
		// Create mapping of models.
		morphia.mapPackage("com.ludussquare.mmonline.server.models");
		// Set the URI & DB.
		uri = new MongoClientURI(properties.getProperty("MongoUri"));
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