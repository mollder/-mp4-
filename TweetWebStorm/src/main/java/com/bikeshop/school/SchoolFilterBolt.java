package com.bikeshop.school;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.*;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SchoolFilterBolt extends BaseBasicBolt {
	LinkedBlockingQueue<SchoolDTO> queue = null;
	SchoolDTO data;
	
	public SchoolFilterBolt() {
		queue = new LinkedBlockingQueue<SchoolDTO>(300000);
		data = new SchoolDTO();
	}
	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// TODO Auto-generated method stub	
		checkAndAddQueue(input,collector);
		if(queue.isEmpty()) {
    		 System.out.println("데이터가 없습니다.");
    	}else{
             collector.emit(new Values(queue.poll()));
    	}
	}
	
	public void checkAndAddQueue(Tuple tuple, BasicOutputCollector collector) {
	   	data = (SchoolDTO) tuple.getValueByField("School");
	   	if(data.isPassOk()) {
	   		queue.offer((SchoolDTO)data);
	   	}else {
	   		data.setStdOk(false);
	   		data.setWrongNum(0);
	   		queue.offer((SchoolDTO)data);
	   	}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("School"));
	}
}