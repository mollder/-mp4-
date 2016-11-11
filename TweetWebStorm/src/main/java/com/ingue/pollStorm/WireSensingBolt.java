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
public class WireSensingBolt extends BaseBasicBolt{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2179294233058608620L;
	PollDTO data;
	//PollDAO dao;
	LinkedBlockingQueue<PollDTO> queue = null;
	
	public WireSensingBolt() {
		queue = new LinkedBlockingQueue<PollDTO>(300000);
		data = new PollDTO();
		//dao = new PollDAO();
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
    	if(data.getLiveWireNum() < 3) {
    		queue.offer((PollDTO)data);
    		//dao.insertPollDTO(data);
    	}else {
    		System.out.println("총 4개 센서 검사중 첫번째 와이어 검사에서 에러 검출");
    		System.out.println("동작해야 하는 와이어 개수  : "+3+"이상 현재 동작하는 와이어 개수 : " + data.getLiveWireNum());
    		System.out.println("현재 전신주 와이어 중 일부 상태가 비정상적일 확률이 매우 높습니다.");
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}