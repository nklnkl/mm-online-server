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
	private String uriString;
	private String db;
	
	private Properties properties;
	private InputStream input;
	
	public Mongo () {
		// Set new properties for config.
		properties = new Properties();
		
		// Try to get properties from file. This will only be successful on local.
		try {
			input = new FileInputStream("Mongo.properties");
			
			// Try to load inputstream into properties data. This will only be successful on local.
			try {
				properties.load(input);
				uriString = properties.getProperty("MongoUri");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		// If the file doesn't load, we're on the production server and should pull the uri from the environment variables.
		} catch (FileNotFoundException e) {
			uriString = System.getenv("MongoUri");
			e.printStackTrace();
		}
		
		// Creates instance for morphia ORM.
		morphia = new Morphia();
		// Create mapping of models.
		morphia.mapPackage("com.ludussquare.mmonline.server.models");
		// Set the URI & DB using the properties file.
		uri = new MongoClientURI(uriString);
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