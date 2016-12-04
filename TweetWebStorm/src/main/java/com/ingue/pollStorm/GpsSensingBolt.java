package com.ingue.pollStorm;

import java.util.concurrent.LinkedBlockingQueue;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.ingue.dto.*;

// 디비에 단어를 넣는 클래스
public class GpsSensingBolt extends BaseBasicBolt{

	private static final long serialVersionUID = -2179294233058608620L;
	CarDTO data;
	LinkedBlockingQueue<CarDTO> queue = null;
	
	public GpsSensingBolt() {
		queue = new LinkedBlockingQueue<CarDTO>(300000);
		data = new CarDTO();
	}

    public void execute(Tuple tuple, BasicOutputCollector collector) {
           // TODO Auto-generated method stub
           checkAndAddQueue(tuple, collector);
           if(queue.isEmpty()) {
     		  System.out.println("데이터가 없습니다.");
     	  }else{
              collector.emit(new Values(queue.poll()));
     	  }
    }
    
    public void checkAndAddQueue(Tuple tuple, BasicOutputCollector collector) {
    	data = (CarDTO) tuple.getValueByField("Poll");
    	if(data.getGps() > 4) {
    		queue.offer((CarDTO)data);
    	}else {
    		data.setGpsOk(false);
    		queue.offer((CarDTO)data);
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}