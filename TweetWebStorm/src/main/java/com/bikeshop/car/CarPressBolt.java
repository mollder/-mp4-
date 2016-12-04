package com.bikeshop.car;

import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.CarDTO;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CarPressBolt extends BaseBasicBolt {
	LinkedBlockingQueue<CarDTO> queue = null;
	CarDTO data;
	int i = 1;
	
	public CarPressBolt() {
		queue = new LinkedBlockingQueue<CarDTO>(300000);
		data = new CarDTO();
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
	   	data = (CarDTO) tuple.getValueByField("Car");
	   	if(data.getCarPress() <= 20) {
	   		queue.offer((CarDTO)data);
	   	}else {
	   		data.setCarOk(false);
	   		data.setWrongNum(i);
	   		queue.offer((CarDTO)data);
	   		i++;
	   	}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Car"));
	}
}