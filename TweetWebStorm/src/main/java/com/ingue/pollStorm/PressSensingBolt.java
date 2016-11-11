package com.ingue.pollStorm;

import java.util.Map;

import com.ingue.dao.PollDTO;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

public class PressSensingBolt implements IRichBolt {
	private OutputCollector collector;
	private DB mongoDB;
	DBCollection collection;
	private PollDTO data;
	/**
	 * @param mongoHost The host on which Mongo is running.
	 * @param mongoPort The port on which Mongo is running.
	 * @param mongoDbName The Mongo database containing all collections being
	 * written to.
	 */
	protected PressSensingBolt() {

	}
	
	public void prepare(
			@SuppressWarnings("rawtypes") Map stormConf, TopologyContext context, OutputCollector collector) {
		
		this.collector = collector;
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.collection=this.mongoDB.getCollection("Datas");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void execute(Tuple input) {
		checkAndAddQueue(input,collector);

	}
    public void checkAndAddQueue(Tuple tuple, OutputCollector collector) {
    	data = (PollDTO) tuple.getValueByField("Poll");
    	
    	if(data.getPressure() <= 50) {
    		insertPollDTO(data);
    		System.out.println("ã��");
    	}else {
    		System.out.println("�� 4�� ���� �˻��� �׹�° �з� �˻翡�� ���� ����");
    		System.out.println("�������� �з� ����  : <=50 , ���� �з� : " + data.getPressure());
    		System.out.println("���� �����ְ� ������������ ���� �з��� �ް����� Ȯ���� �ſ� �����ϴ�.");
    	}
    }
    
	public void cleanup() {
		this.mongoDB.getMongo().close();
	}
	
	public void insertPollDTO(PollDTO dtoData) {
	      BasicDBObject object = new BasicDBObject();
	       object.put("getPollNum",dtoData.getPollNum());
	       object.put("pollNum",2);
	       object.put("angle", dtoData.getAngle());
	       object.put("temperature",dtoData.getTemperature());
	       object.put("pressure", dtoData.getPressure());
	       object.put("liveWireNum", dtoData.getLiveWireNum());
	       this.collection.insert(object);
	       System.out.println("���Լ���");
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