package com.bikeshop.emerbell;

import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.EmerDTO;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class EmerFilterBolt extends BaseBasicBolt {
	LinkedBlockingQueue<EmerDTO> queue = null;
	EmerDTO data;
	int i = 1;
	
	public EmerFilterBolt() {
		queue = new LinkedBlockingQueue<EmerDTO>(300000);
		data = new EmerDTO();
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
	   	data = (EmerDTO) tuple.getValueByField("Emer");
	   	if(data.getEmerAcc() <= 10) {
	   		queue.offer((EmerDTO)data);
	   	}else {
	   		data.setEmerOk(false);
	   		data.setWrongNum(i);
	   		queue.offer((EmerDTO)data);
	   		i++;
	   	}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Emer"));
	}

}
