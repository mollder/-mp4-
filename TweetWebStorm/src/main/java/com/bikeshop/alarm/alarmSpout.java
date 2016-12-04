package com.bikeshop.alarm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.bikeshop.dto.CarDTO;
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

public class alarmSpout implements IRichSpout {
																				
	private static final long serialVersionUID = 5147686186825975929L;
	private DB mongoDB;
	DBCollection wrongCollection;
	CarDTO data;
	LinkedBlockingQueue<CarDTO> queue = null;
	private SpoutOutputCollector collector;
	List<CarDTO> carList;
	
	public alarmSpout() {
		queue = new LinkedBlockingQueue<CarDTO>(1000);
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.wrongCollection=this.mongoDB.getCollection("WrongCar");
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
		declarer.declare(new Fields("WrongCar"));
	}
	
	public void SensingData() {
		BasicDBObject object = new BasicDBObject();
			if( this.wrongCollection.count()!= 0) {
				CarDTO wrongData = new CarDTO();
				wrongData.setCarNum((int)this.wrongCollection.findOne().get("getCarNum"));
				wrongData.setCarOk((boolean)this.wrongCollection.findOne().get("pressOk"));
				wrongData.setCarPress((int)this.wrongCollection.findOne().get("press"));
				wrongData.setTime((String)this.wrongCollection.findOne().get("time"));
				wrongData.setWrongNum((int)this.wrongCollection.findOne().get("wrongNum"));
				queue.offer(wrongData);

				object.put("getCarNum", 1);
				this.wrongCollection.remove(object);
			}else {
					System.out.println("이상이 없습니다.");
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
