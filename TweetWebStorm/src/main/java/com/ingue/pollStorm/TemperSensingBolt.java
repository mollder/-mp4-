package com.ingue.pollStorm;

import java.util.concurrent.LinkedBlockingQueue;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.ingue.dao.*;

// 디비에 단어를 넣는 클래스
public class TemperSensingBolt extends BaseBasicBolt{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8261142026413437692L;
	LinkedBlockingQueue<PollDTO> queue = null;
	PollDTO data;
	
	public TemperSensingBolt() {
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
    	if(data.getTemperature() <= 50) {
    		queue.offer((PollDTO)data);
    	}else {
    		System.out.println("총 4개 센서 검사중 두번째 온도센서 검사에서 에러 검출");
    		System.out.println("정상 온도 범주  : <= 99 , 현재 온도 : " + data.getTemperature());
    		System.out.println("현재 전신주 온도가 매우 높습니다. 비정상적인 상태일 확률이 매우 높습니다.");
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}