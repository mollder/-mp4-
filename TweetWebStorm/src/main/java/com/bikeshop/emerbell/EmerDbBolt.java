package com.bikeshop.emerbell;

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

public class EmerDbBolt implements IRichBolt {
	EmerDTO data;
	private DB mongoDB;
	DBCollection Normalcollection;
	DBCollection Wrongcollection;

	@SuppressWarnings("deprecation")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.Normalcollection=this.mongoDB.getCollection("NormalEmer");
			this.Wrongcollection = this.mongoDB.getCollection("WrongEmer");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		data = (EmerDTO) input.getValueByField("Emer");
		if(data.isEmerAccOk() == true) {
			this.insertNormalEmerDTO(data);
		}else {
			this.insertWrongEmerDTO(data);
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Emer"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void insertNormalEmerDTO(EmerDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getEmerNum", dtoData.getEmerNum());
		object.put("acc", dtoData.getEmerAcc());
		object.put("accOk", dtoData.isEmerAccOk());
		object.put("Emertime", dtoData.getEmerTime());
		object.put("EmerWrongNum", dtoData.getWrongNum());
		
		this.Normalcollection.insert(object);
	}
	
	public void insertWrongEmerDTO(EmerDTO dtoData) {
		BasicDBObject object = new BasicDBObject();
		object.put("getEmerNum", dtoData.getEmerNum());
		object.put("acc", dtoData.getEmerAcc());
		object.put("accOk", dtoData.isEmerAccOk());
		object.put("Emertime", dtoData.getEmerTime());
		object.put("EmerWrongNum", dtoData.getWrongNum());
		
		this.Wrongcollection.insert(object);
	}
}