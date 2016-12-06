package com.bikeshop.school;
import java.util.List;
import java.util.Map;

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

public class SchoolDbBolt implements IRichBolt {
	SchoolDTO data;
	private DB mongoDB;
	DBCollection Normalcollection;
	DBCollection Wrongcollection;

	@SuppressWarnings("deprecation")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.Normalcollection=this.mongoDB.getCollection("NormalSchool");
			this.Wrongcollection = this.mongoDB.getCollection("WrongSchool");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		data = (SchoolDTO) input.getValueByField("School");
		if(data.isGatePass() == true) {
			this.insertNormalSchoolDTO(data);
		}else {
			this.insertWrongSchoolDTO(data);
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("School"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void insertNormalSchoolDTO(SchoolDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getStdNum", dtoData.getStdNum());
		object.put("StdOk", dtoData.isGatePass());
		object.put("SchoolTime", dtoData.getSchoolTime());
		object.put("wrongNum", dtoData.getWrongNum());
		
		this.Normalcollection.insert(object);
	}
	
	public void insertWrongSchoolDTO(SchoolDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getStdNum", dtoData.getStdNum());
		object.put("StdOk", dtoData.isGatePass());
		object.put("SchoolTime", dtoData.getSchoolTime());
		object.put("wrongNum", dtoData.getWrongNum());
		
		this.Wrongcollection.insert(object);
	}
}