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

import com.ingue.dao.*;

public class PollSpout extends BaseRichSpout {
	
		  private static final long serialVersionUID = 1L;
          private SpoutOutputCollector collector;
          LinkedBlockingQueue<PollDTO> queue = null;
          Random random;
          PollDTO data;
          
          public PollSpout() {
        	  queue = new LinkedBlockingQueue<PollDTO>(10000);
        	  random = new Random();
        	  generatingPollData();
          }
         
          public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
               this.collector = collector;
          }/*�� �޼���� Spout�� ó�� �ʱ�ȭ �ɶ� �ѹ��� ȣ��Ǵ� �޼����, ����Ÿ �ҽ��� ������ ������ �ʱ�ȭ �ϴ� ���� ������ �Ѵ�.*/
          
          public void nextTuple(){
        	  if(queue.isEmpty()) {
        		  System.out.println("�����Ͱ� �����ϴ�.");
        	  }else{
                 this.collector.emit(new Values(queue.poll()));
        	  }
          }/*�� �޼���� ����Ÿ ��Ʈ�� �ϳ��� �а� ����, ���� ����Ÿ ��Ʈ���� ���� �� ȣ�� �Ǵ� �޼��� �̴�.*/
          
          public void declareOutputFields(OutputFieldsDeclarer declarer){
        	  declarer.declare(new Fields("Poll"));
          }
          
          private void generatingPollData() {
        	  for(int i = 1; i <= 100000; i++) {
        		  data = new PollDTO();
        		  data.setPollNum(i);
        		  data.setAngle(random.nextDouble()*180);
        		  data.setLiveWireNum(random.nextInt(8));
        		  data.setPressure(random.nextDouble()*100);
        		  data.setTemperature(random.nextDouble()*100);
        		  queue.offer(data);
        	  }
          }     
}