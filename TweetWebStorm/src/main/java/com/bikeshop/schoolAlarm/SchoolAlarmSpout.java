package com.bikeshop.schoolAlarm;

import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.SchoolDTO;
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

public class SchoolAlarmSpout implements IRichSpout {
																				
	private static final long serialVersionUID = 5147686186825975929L;
	private DB mongoDB;
	DBCollection wrongCollection;
	SchoolDTO data;
	LinkedBlockingQueue<SchoolDTO> queue = null;
	private SpoutOutputCollector collector;
	List<SchoolDTO> StdList;
	
	public SchoolAlarmSpout() {
		queue = new LinkedBlockingQueue<SchoolDTO>(1000);
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.wrongCollection=this.mongoDB.getCollection("WrongSchool");
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
		declarer.declare(new Fields("WrongSchool"));
	}
	
	public void SensingData() {
		BasicDBObject object = new BasicDBObject();
			if( this.wrongCollection.count()!= 0) {
				SchoolDTO wrongData = new SchoolDTO();
				wrongData.setStdNum((int)this.wrongCollection.findOne().get("getStdNum"));
				wrongData.setGatePass((boolean)this.wrongCollection.findOne().get("StdOk"));
				wrongData.setSchoolTime((String)this.wrongCollection.findOne().get("SchoolTime"));
				queue.offer(wrongData);

				object.put("getStdNum", 1);
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
