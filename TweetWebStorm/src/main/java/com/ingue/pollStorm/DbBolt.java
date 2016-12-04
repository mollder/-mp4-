package com.ingue.pollStorm;

import java.util.List;
import java.util.Map;

import com.ingue.dto.CarDTO;
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

//cron table
//
//이상테이블에 따로저장
//이상테이블 감시하는 task
public class DbBolt implements IRichBolt {
	
	private DB mongoDB;
	DBCollection collection;
	private List<CarDTO> list;
	CarDTO data;

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		try {
			this.mongoDB = new Mongo("localhost",27017).getDB("Data");
			this.collection=this.mongoDB.getCollection("Test2");
			data = new CarDTO();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void execute(Tuple input) {
		data = (CarDTO) input.getValueByField("Poll");
		this.insertPollDTO(data);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
		this.mongoDB.getMongo().close();
	}
	
	public void insertPollDTO(CarDTO dtoData) {
	      BasicDBObject object = new BasicDBObject();
	      object.put("getPollNum",dtoData.getPollNum());
	      object.put("temperature",dtoData.getTemperature());
	      object.put("pressure", dtoData.getPressure());
	      object.put("gps", dtoData.getGps());
	      object.put("pressOk", dtoData.isPressOk());
	      object.put("temperOk", dtoData.isTemperOk());
	      object.put("gpsOk", dtoData.isGpsOk());
	      this.collection.insert(object);
	}
	
	public List<CarDTO> findWireWrongDataPollDTO() {	
		BasicDBObject query = new BasicDBObject();
		query.put("wireOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (CarDTO)cursor.next());
			i++;
		}
		return list;
	}
	
	public List<CarDTO> findTemperWrongDataPollDTO() {
		
		BasicDBObject query = new BasicDBObject();
		query.put("temperOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (CarDTO)cursor.next());
			i++;
		}
		return list;
	}
 
	public List<CarDTO> findAngleWrongDataPollDTO() {
		BasicDBObject query = new BasicDBObject();
		query.put("angleOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (CarDTO)cursor.next());
			i++;
		} 
		return list;
	}

	public List<CarDTO> findPressWrongDataPollDTO() {
		BasicDBObject query = new BasicDBObject();
		query.put("pressOk", false);
		DBCursor cursor = collection.find(query);
		int i = 0;
		while(cursor.hasNext()) {
			list.add(i, (CarDTO)cursor.next());
			i++;
		}
		return list;
	}
	
	public int getWrongGpsSize() {
		BasicDBObject query = new BasicDBObject();
		query.put("GpsOk", false);
		DBCursor cursor = collection.find(query);
		return cursor.size();
	}
	
	public int getWrongGpsPageSize() {
		if((getWrongGpsSize() % 10) == 0) {
			return getWrongGpsSize()/10;
		} else {
			return (getWrongGpsSize()/10)+1;
		}
	}
	
	public int getWrongTemperSize() {
		BasicDBObject query = new BasicDBObject();
		query.put("temperOk", false);
		DBCursor cursor = collection.find(query);
		return cursor.size();
	}
	
	public int getWrongTemperPageSize() {
		if((getWrongTemperSize() % 10) == 0) {
			return getWrongTemperSize()/10;
		} else {
			return (getWrongTemperSize()/10)+1;
		}
	}

	public int getWrongPressSize() {
		BasicDBObject query = new BasicDBObject();
		query.put("pressOk", false);
		DBCursor cursor = collection.find(query);
		return cursor.size();
	}
	
	public int getWrongPressPageSize() {
		if((getWrongPressSize() % 10) == 0) {
			return getWrongPressSize()/10;
		} else {
			return (getWrongPressSize()/10)+1;
		}
	}
	
	public int getWrongDataSize() {
		return getWrongGpsSize()+getWrongTemperSize()+
				getWrongPressSize();
	}
	
	public int getWrongDataPageSize() {
		if(((getWrongGpsSize()+getWrongTemperSize()+
				getWrongPressSize()) % 10) == 0 ) {
			return (getWrongGpsSize()+getWrongTemperSize()+
					getWrongPressSize())/10;
		}
		return ((getWrongGpsSize()+getWrongTemperSize()+
				getWrongPressSize())/10)+1;
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