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
public class AngleSensingBolt extends BaseBasicBolt{
	
	/**
	 * 
	 */
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
    		System.out.println("총 4개 센서 검사중 세번째 각도센서 검사에서 에러 검출");
    		System.out.println("정상 각도 범주  : 45 <= 정상각도  <= 135 , 현재 각도 : " + data.getAngle());
    		System.out.println("전신주가 비정상적으로 기울어져있을 수 있습니다.");
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}