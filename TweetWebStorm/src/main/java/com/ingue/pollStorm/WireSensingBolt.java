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
     		  System.out.println("�����Ͱ� �����ϴ�.");
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
    		System.out.println("�� 4�� ���� �˻��� ù��° ���̾� �˻翡�� ���� ����");
    		System.out.println("�����ؾ� �ϴ� ���̾� ����  : "+3+"�̻� ���� �����ϴ� ���̾� ���� : " + data.getLiveWireNum());
    		System.out.println("���� ������ ���̾� �� �Ϻ� ���°� ���������� Ȯ���� �ſ� �����ϴ�.");
    	}
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("Poll"));
    }
}