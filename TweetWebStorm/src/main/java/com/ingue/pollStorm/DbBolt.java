package com.ingue.pollStorm;

import java.util.List;
import java.util.Map;

import com.ingue.dto.PollDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class DbBolt implements IRichBolt {
	
	private DB mongoDB;
	DBCollection collection;
	private PollDTO data;
	private List<PollDTO> list;
	private int wrongWireSize;
	private int wrongTemperSize;
	private int wrongAngleSize;
	private int wrongPressSize;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.collection=this.mongoDB.getCollection("Datas");
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
		this.mongoDB.getMongo().close();
	}
	
	public void insertPollDTO(PollDTO dtoData) {
	      BasicDBObject object = new BasicDBObject();
	      object.put("getPollNum",dtoData.getPollNum());
	      object.put("angle", dtoData.getAngle());
	      object.put("temperature",dtoData.getTemperature());
	      object.put("pressure", dtoData.getPressure());
	      object.put("liveWireNum", dtoData.getLiveWireNum());
	      object.put("angleOk", dtoData.isAngleOk());
	      object.put("pressOk", dtoData.isPressOk());
	      object.put("temperOk", dtoData.isTemperOk());
	      object.put("wireOk", dtoData.isWireOk());
	      this.collection.insert(object);
	}
	
	public List<PollDTO> findWireWrongDataPollDTO() {	
		BasicDBObject query = new BasicDBObject();
		query.put("wireOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (PollDTO)cursor.next());
			i++;
		}
		wrongWireSize = i;
		return list;
	}
	
	public List<PollDTO> findTemperWrongDataPollDTO() {
		
		BasicDBObject query = new BasicDBObject();
		query.put("temperOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (PollDTO)cursor.next());
			i++;
		}
		wrongTemperSize = i;
		return list;
	}

	public List<PollDTO> findAngleWrongDataPollDTO() {
		BasicDBObject query = new BasicDBObject();
		query.put("angleOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (PollDTO)cursor.next());
			i++;
		}
		wrongAngleSize = i; 
		return list;
	}

	public List<PollDTO> findPressWrongDataPollDTO() {
		BasicDBObject query = new BasicDBObject();
		query.put("pressOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (PollDTO)cursor.next());
			i++;
		}
		wrongPressSize = i;
		return list;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Poll"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
}