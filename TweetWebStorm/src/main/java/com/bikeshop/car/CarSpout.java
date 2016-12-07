package com.bikeshop.car;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.CarDTO;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

//위도경도(일단 유보) / 차량ID / 시간 / 상황Ok / 충격
//계속 정보를 보내는데 충격량이 크면 이상정보에 ㄱㄱ
public class CarSpout extends BaseRichSpout {
	private SpoutOutputCollector collector;
    LinkedBlockingQueue<CarDTO> queue = null;
    Random random;
    CarDTO data;
    int num = 0;
    String str;
	
	public CarSpout() {
		queue = new LinkedBlockingQueue<CarDTO>(300000);
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
		declarer.declare(new Fields("Car"));
	}
	
	public void generateCarData(int num) {
		for(int i = 1+num; i <= 250000+num; i++) {
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			str = dayTime.format(new Date(time));
			data = new CarDTO();
			data.setCarNum(1);
			data.setCarPress(random.nextInt(10));
			data.setCarOk(true);
			data.setWrongNum(0);
			data.setTime(str);
  		  
			if((i % 5000) == 0) {
				data.setCarPress(random.nextInt(20000)+21); 
  		  	}
			queue.offer(data);
		}
  	  	this.num = 250000+num;
	}
}