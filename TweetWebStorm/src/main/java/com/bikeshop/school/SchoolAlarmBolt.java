package com.bikeshop.school;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.CarDTO;
import com.bikeshop.dto.SchoolDTO;
import com.bikeshop.sms.SendSMS;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SchoolAlarmBolt extends BaseBasicBolt{

	LinkedBlockingQueue<SchoolDTO> queue = null;
	SchoolDTO data;
	SendSMS sms;
	String text;
	
	public SchoolAlarmBolt() {
		queue = new LinkedBlockingQueue<SchoolDTO>(300000);
		data = new SchoolDTO();
		text = "학생 등교에 이상이 생겼습니다!";
	}

	@Override
	public void execute(Tuple input, BasicOutputCollector collector) {
		// TODO Auto-generated method stub
		checkAndSend(input,collector);
		if(queue.isEmpty()) {
    		 System.out.println("데이터가 없습니다.");
    	}else{
             collector.emit(new Values(queue.poll()));
    	}
	}
	
	public void checkAndSend(Tuple tuple, BasicOutputCollector collector) {
	   	data = (SchoolDTO) tuple.getValueByField("School");
	   	if(data.isStdOk()) {
	   		queue.offer((SchoolDTO)data);
	   	}else {
	   		sms = new SendSMS(text);
	   		queue.offer((SchoolDTO)data);
	   	}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("School"));
	}
	
}