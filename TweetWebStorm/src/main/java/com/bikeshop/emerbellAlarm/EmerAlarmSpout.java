package com.bikeshop.emerbellAlarm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.EmerDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class EmerAlarmSpout implements IRichSpout {
																				
	private static final long serialVersionUID = 5147686186825975929L;
	private DB mongoDB;
	DBCollection wrongCollection;
	EmerDTO data;
	LinkedBlockingQueue<EmerDTO> queue = null;
	private SpoutOutputCollector collector;
	List<EmerDTO> carList;
	
	public EmerAlarmSpout() {
		queue = new LinkedBlockingQueue<EmerDTO>(1000);
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.wrongCollection=this.mongoDB.getCollection("WrongEmer");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		if(queue.isEmpty()) {
	  	  SensingData();
	  	}else{
	         this.collector.emit(new Values(queue.poll()));
	  	}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("WrongEmer"));
	}
	
	public void SensingData() {
		BasicDBObject object = new BasicDBObject();
			if( this.wrongCollection.count()!= 0) {
				EmerDTO wrongData = new EmerDTO();
				wrongData.setEmerNum((int)this.wrongCollection.findOne().get("getEmerNum"));
				wrongData.setEmerAccOk((boolean)this.wrongCollection.findOne().get("accOk"));
				wrongData.setEmerAcc((int)this.wrongCollection.findOne().get("acc"));
				wrongData.setEmerTime((String)this.wrongCollection.findOne().get("EmerTime"));
				wrongData.setWrongNum((int)this.wrongCollection.findOne().get("EmerWrongNum"));
				queue.offer(wrongData);

				object.put("getEmerNum", 1);
				this.wrongCollection.remove(object);
			}else {
					
			}	
	}


	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ack(Object msgId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fail(Object msgId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
