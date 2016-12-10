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
	private String uriString;
	private String db;
	
	private Properties properties;
	private InputStream input;
	
	public Mongo (Fongo fongo) {
		
		// Use the URI to connect to the db.
		client = fongo.getMongo();
		
		// Set db name.
		db = fongo.getDB("mm-online-db").getName();
		
		// Creates instance for morphia ORM.
		morphia = new Morphia();
		
		// Create mapping of models.
		morphia.mapPackage("com.ludussquare.mmonline.server.models");
		
		// Create data store using the db.
		datastore = morphia.createDatastore(client, db);
	}
	
	public Mongo () {
		
		uriString = getUriString();
		
		// Set the URI & DB using the properties file.
		uri = new MongoClientURI(uriString);
		db = "mm-online-db";
		// Use the URI to connect to the db.
		client = new MongoClient(uri);
		
		// Creates instance for morphia ORM.
		morphia = new Morphia();
		
		// Create mapping of models.
		morphia.mapPackage("com.ludussquare.mmonline.server.models");
		
		// Create data store using the db.
		datastore = morphia.createDatastore(client, db);
	}
	
	private String getUriString () {
		// Set new properties for config.
		properties = new Properties();
		
		input = getClass().getClassLoader().getResourceAsStream("Mongo.properties");
		// Try to load inputstream into properties data. This will only be successful on local.
		
		try {
			
			properties.load(input);
			return properties.getProperty("MongoUri");
			
		} catch (IOException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return System.getenv("MongoUri");
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