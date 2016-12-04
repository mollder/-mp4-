package com.bikeshop.emerbell;

import java.util.List;
import java.util.Map;

import com.bikeshop.dto.*;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class EmerDbBolt implements IRichBolt {
	private DB mongoDB;
	DBCollection collection;
	private List<EmerDTO> list;
	EmerDTO data;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.collection=this.mongoDB.getCollection("EmerBell");
			data = new EmerDTO();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
