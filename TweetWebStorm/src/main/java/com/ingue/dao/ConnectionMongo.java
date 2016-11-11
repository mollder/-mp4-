package com.ingue.dao;

import java.io.Serializable;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class ConnectionMongo implements Serializable{
   /**
	 * 
	 */
	private static final long serialVersionUID = -935883957128246300L;
	Mongo mongo = null;
	DB db = null;
	public DBCollection collection = null;
	public ConnectionMongo(){
      mongo = new Mongo("localhost",27017);
	  db = mongo.getDB("data");
	  collection = db.getCollection("Datas");
	}
	public DBCollection getCollection() {
		return collection;
	}
	public void setCollection(DBCollection collection) {
		this.collection = collection;
	}
}