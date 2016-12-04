package com.bikeshop.emerbell;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;

import com.bikeshop.dto.*;
import com.ingue.dto.CarDTO;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;

//�����浵  / ���ӵ����� / ��� ID / ����ð�
// ���ӵ����� 
// �����浵
// ��� ������ �����µ� �� ������ �̻������̸� �̻����̺�� �������� �̻����̺��� ó��
public class EmerbellSpout extends BaseRichSpout {
	
	private static final long serialVersionUID = 1L;
    private SpoutOutputCollector collector;
    LinkedBlockingQueue<EmerDTO> queue = null;
    Random random;
    EmerDTO data;
    int num = 0;
    
    public void EmerBellSpout() {
    	queue = new LinkedBlockingQueue<EmerDTO>(300000);
  	  	random = new Random();
  	  	generateEmerData(num);
    }

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Poll"));
	}
	
	public void generateEmerData(int num) {
		long time = System.currentTimeMillis(); 
		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		String str = dayTime.format(new Date(time));
		
		
	}
}