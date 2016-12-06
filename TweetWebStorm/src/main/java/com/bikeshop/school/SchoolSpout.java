package com.bikeshop.school;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.SchoolDTO;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

//학생아이디 /통과시간/통과OK
//
public class SchoolSpout extends BaseRichSpout {

	private SpoutOutputCollector collector;
    LinkedBlockingQueue<SchoolDTO> queue = null;
    Random random;
    SchoolDTO data;
    int num = 0;
    String str;
	
	public SchoolSpout() {
		
		queue = new LinkedBlockingQueue<SchoolDTO>(300000);
  	  	random = new Random();
		generateCarData(num);
	}

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		if(queue.isEmpty()) {
  		  generateCarData(num);
  	  	}else{
           this.collector.emit(new Values(queue.poll()));
  	  }
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("School"));
	}
	
	public void generateCarData(int num) {
		for(int i = 1+num; i <= 250000+num; i++) {
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			str = dayTime.format(new Date(time));
			data = new SchoolDTO();
			data.setStdNum(1);
			data.setGatePass(true);
			data.setWrongNum(0);
			data.setSchoolTime(str);
  		  
			if((i % 20000) == 0) {
				data.setGatePass(false); 
  		  	}
			queue.offer(data);
		}
  	  	this.num = 250000+num;
	}
	
}