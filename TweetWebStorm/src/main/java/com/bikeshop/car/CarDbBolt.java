package com.bikeshop.car;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.bikeshop.dto.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class CarDbBolt implements IRichBolt {
	CarDTO data;
	private DB mongoDB;
	DBCollection Normalcollection;
	DBCollection Wrongcollection;

	@SuppressWarnings("deprecation")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.Normalcollection=this.mongoDB.getCollection("NormalCar");
			this.Wrongcollection = this.mongoDB.getCollection("WrongCar");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		data = (CarDTO) input.getValueByField("Car");
		if(data.isCarOk() == true) {
			this.insertNormalCarDTO(data);
		}else {
			this.insertWrongCarDTO(data);
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Car"));
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
		
		this.Normalcollection.insert(object);
	}
	
	public void insertWrongCarDTO(CarDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getCarNum", dtoData.getCarNum());
		object.put("press", dtoData.getCarPress());
		object.put("pressOk", dtoData.isCarOk());
		object.put("time", dtoData.getTime());
		
		this.Wrongcollection.insert(object);
	}
}