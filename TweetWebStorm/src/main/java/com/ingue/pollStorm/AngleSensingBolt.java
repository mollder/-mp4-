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
public class AngleSensingBolt extends BaseBasicBolt{
	
	private static final long serialVersionUID = -293245825742509019L;
	LinkedBlockingQueue<PollDTO> queue = null;
	PollDTO data;
	
	public AngleSensingBolt() {
		queue = new LinkedBlockingQueue<PollDTO>(300000);
		data = new PollDTO();
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
    	data = (PollDTO) tuple.getValueByField("Poll");

    	if( (45<=data.getAngle()) && (data.getAngle() <= 135)) {
    		queue.offer((PollDTO)data);
    	}else {
    		data.setAngleOk(false);
    		queue.offer((PollDTO)data);
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}