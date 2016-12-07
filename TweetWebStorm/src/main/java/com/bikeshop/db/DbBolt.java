package com.bikeshop.db;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

import com.bikeshop.dto.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class DbBolt implements IRichBolt {
	CarDTO carData;
	EmerDTO emerData;
	SchoolDTO schoolData;
	private DB mongoDB;
	DBCollection normalCarCollection;
	DBCollection wrongCarCollection;
	DBCollection normalEmerCollection;
	DBCollection wrongEmerCollection;
	DBCollection normalSchoolCollection;
	DBCollection wrongSchoolCollection;

	@SuppressWarnings("deprecation")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.normalCarCollection=this.mongoDB.getCollection("NormalCar");
			this.wrongCarCollection = this.mongoDB.getCollection("WrongCar");
			this.normalEmerCollection = this.mongoDB.getCollection("NormalEmer");
			this.wrongEmerCollection = this.mongoDB.getCollection("WrongEmer");
			this.normalSchoolCollection = this.mongoDB.getCollection("NormalSchool");
			this.wrongSchoolCollection = this.mongoDB.getCollection("WrongSchool");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		if(input.contains("Car")) {
			carData = (CarDTO) input.getValueByField("Car");
			
			if(carData.isCarOk() == true) {
				this.insertNormalCarDTO(carData);
			}else {
				this.insertWrongCarDTO(carData);
			}
		}else if(input.contains("Emer")) {
			emerData = (EmerDTO) input.getValueByField("Emer");
			
			if(emerData.isEmerOk() == true) {
				this.insertNormalEmerDTO(emerData);
			}else {
				this.insertWrongEmerDTO(emerData);
			}
		}else if(input.contains("School")) {
			schoolData = (SchoolDTO) input.getValueByField("School");
			
			if(schoolData.isStdOk() == true) {
				this.insertNormalSchoolDTO(schoolData);
			}else {
				this.insertWrongSchoolDTO(schoolData);
			}
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Db"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void insertNormalCarDTO(CarDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getCarNum", dtoData.getCarNum());
		object.put("press", dtoData.getCarPress());
		object.put("pressOk", dtoData.isCarOk());
		object.put("time", dtoData.getTime());
		object.put("wrongNum", dtoData.getWrongNum());
		
		this.normalCarCollection.insert(object);
	}
	
	public void insertWrongCarDTO(CarDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getCarNum", dtoData.getCarNum());
		object.put("press", dtoData.getCarPress());
		object.put("pressOk", dtoData.isCarOk());
		object.put("time", dtoData.getTime());
		
		this.wrongCarCollection.insert(object);
	}
	
	public void insertNormalEmerDTO(EmerDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getEmerNum", dtoData.getEmerNum());
		object.put("acc", dtoData.getEmerAcc());
		object.put("accOk", dtoData.isEmerOk());
		object.put("Emertime", dtoData.getEmerTime());
		object.put("EmerWrongNum", dtoData.getWrongNum());
		
		this.normalEmerCollection.insert(object);
	}
	
	public void insertWrongEmerDTO(EmerDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getEmerNum", dtoData.getEmerNum());
		object.put("acc", dtoData.getEmerAcc());
		object.put("accOk", dtoData.isEmerOk());
		object.put("Emertime", dtoData.getEmerTime());
		object.put("EmerWrongNum", dtoData.getWrongNum());
		
		this.wrongEmerCollection.insert(object);
	}
	
	public void insertNormalSchoolDTO(SchoolDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getStdNum", dtoData.getStdNum());
		object.put("StdOk", dtoData.isStdOk());
		object.put("SchoolTime", dtoData.getSchoolTime());
		object.put("wrongNum", dtoData.getWrongNum());
		
		this.normalSchoolCollection.insert(object);
	}
	
	public void insertWrongSchoolDTO(SchoolDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getStdNum", dtoData.getStdNum());
		object.put("StdOk", dtoData.isStdOk());
		object.put("SchoolTime", dtoData.getSchoolTime());
		object.put("wrongNum", dtoData.getWrongNum());
		
		this.wrongSchoolCollection.insert(object);
	}
}