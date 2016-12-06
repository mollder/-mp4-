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
import backtype.storm.tuple.Values;

//위도경도  / 가속도센서 / 비상벨 ID / 현재시간
// 가속도센서 
// 위도경도
// 계속 정보를 보내는데 그 정보가 이상정보이면 이상테이블로 삽입한후 이상테이블에서 처리
public class EmerbellSpout extends BaseRichSpout {
	
	private static final long serialVersionUID = 1L;
    private SpoutOutputCollector collector;
    LinkedBlockingQueue<EmerDTO> queue = null;
    Random random;
    EmerDTO data;
    int num = 0;
    String str;
    
    public EmerbellSpout() {
    	queue = new LinkedBlockingQueue<EmerDTO>(300000);
  	  	random = new Random();
		generateEmerData(num);
    }

	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		// TODO Auto-generated method stub
		this.collector = collector;
		
	}

	@Override
	public void nextTuple() {
		// TODO Auto-generated method stub
		if(queue.isEmpty()) {
			generateEmerData(num);
	  	}else{
	        this.collector.emit(new Values(queue.poll()));
	  	}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("Emer"));
	}
	
	public void generateEmerData(int num) {
		for(int i = 1+num; i <= 250000+num; i++) {
			long time = System.currentTimeMillis(); 
			SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
			str = dayTime.format(new Date(time));
			data = new EmerDTO();
			data.setEmerNum(1);
			data.setEmerAcc(random.nextInt(1));
			data.setEmerAccOk(true);
			data.setWrongNum(0);
			data.setEmerTime(str);
  		  
			if((i % 20000) == 0) {
				data.setEmerAcc(random.nextInt(100)+10); 
  		  	}
			queue.offer(data);
		}
  	  	this.num = 250000+num;
	}
}