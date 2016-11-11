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
          }/*이 메서드는 Spout이 처음 초기화 될때 한번만 호출되는 메서드로, 데이타 소스로 부터의 연결을 초기화 하는 등의 역할을 한다.*/
          
          public void nextTuple(){
        	  if(queue.isEmpty()) {
        		  System.out.println("데이터가 없습니다.");
        	  }else{
                 this.collector.emit(new Values(queue.poll()));
        	  }
          }/*이 메서드는 데이타 스트림 하나를 읽고 나서, 다음 데이타 스트림을 읽을 때 호출 되는 메서드 이다.*/
          
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