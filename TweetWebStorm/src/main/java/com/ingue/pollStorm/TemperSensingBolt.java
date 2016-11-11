package com.ingue.pollStorm;

import java.util.concurrent.LinkedBlockingQueue;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import com.ingue.dao.*;

// ��� �ܾ �ִ� Ŭ����
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
      		  System.out.println("�����Ͱ� �����ϴ�.");
      	  }else{
               collector.emit(new Values(queue.poll()));
      	  }
    }
    
    public void checkAndAddQueue(Tuple tuple, BasicOutputCollector collector) {
    	data = (PollDTO) tuple.getValueByField("Poll");
    	if(data.getTemperature() <= 50) {
    		queue.offer((PollDTO)data);
    	}else {
    		System.out.println("�� 4�� ���� �˻��� �ι�° �µ����� �˻翡�� ���� ����");
    		System.out.println("���� �µ� ����  : <= 99 , ���� �µ� : " + data.getTemperature());
    		System.out.println("���� ������ �µ��� �ſ� �����ϴ�. ���������� ������ Ȯ���� �ſ� �����ϴ�.");
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}