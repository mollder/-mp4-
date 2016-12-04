package com.ingue.pollStorm;
 
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

import com.ingue.dto.*;

public class CarSpout extends BaseRichSpout {
	
		  private static final long serialVersionUID = 1L;
          private SpoutOutputCollector collector;
          LinkedBlockingQueue<CarDTO> queue = null;
          Random random;
          CarDTO data;
          int num = 0;
          
          public CarSpout() {
        	  queue = new LinkedBlockingQueue<CarDTO>(300000);
        	  random = new Random();
        	  generatingPollData(num);
          }
         
          public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
               this.collector = collector;
          }/*�� �޼���� Spout�� ó�� �ʱ�ȭ �ɶ� �ѹ��� ȣ��Ǵ� �޼����, ����Ÿ �ҽ��� ������ ������ �ʱ�ȭ �ϴ� ���� ������ �Ѵ�.*/
          
          public void nextTuple(){
        	  if(queue.isEmpty()) {
        		  generatingPollData(num);
        	  }else{
                 this.collector.emit(new Values(queue.poll()));
        	  }
          }/*�� �޼���� ����Ÿ ��Ʈ�� �ϳ��� �а� ����, ���� ����Ÿ ��Ʈ���� ���� �� ȣ�� �Ǵ� �޼��� �̴�.*/
          
          public void declareOutputFields(OutputFieldsDeclarer declarer){
        	  declarer.declare(new Fields("Poll"));
          }
          
          private void generatingPollData(int num) {
        	  for(int i = 1+num; i <= 250000+num; i++) {
        		  data = new CarDTO();
        		  data.setPollNum(i);
        		  data.setGps(random.nextInt(8));
        		  data.setPressure(random.nextDouble()*100);
        		  data.setTemperature(random.nextDouble()*100);
        		  data.setPressOk(true);
        		  data.setGpsOk(true);
        		  data.setTemperOk(true);
        		  queue.offer(data);
        	  }
        	  this.num = 250000+num;
          }     
}