package com.ingue.pollStorm;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.ingue.dto.PollDTO;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class PressSensingBolt extends BaseBasicBolt {
	LinkedBlockingQueue<PollDTO> queue = null;
	PollDTO data;
	
	protected PressSensingBolt() {
		queue = new LinkedBlockingQueue<PollDTO>(300000);
		data = new PollDTO();
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
    	data = (PollDTO) tuple.getValueByField("Poll");
    	if(data.getPressure() <= 50) {
    		queue.offer((PollDTO)data);
    	}else {
    		data.setPressOk(false);
    		queue.offer((PollDTO)data);
    	}
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