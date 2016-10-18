package com.terry.tweetStorm;
 
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingQueue;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitBolt extends BaseBasicBolt{
	
		private LinkedBlockingQueue<String> queue = null;
	
		public SplitBolt() {
			queue = new LinkedBlockingQueue<String>(300000);
		}
 
        public void execute(Tuple tuple, BasicOutputCollector collector) {
               // TODO Auto-generated method stub
               String value = tuple.getStringByField("tweet");
               System.out.println("Tuple value is "+value);
               splitWord(value);
               
               if(!queue.isEmpty()) {
            	   collector.emit(new Values(queue.poll()));
               }
               else {
            	   System.out.println("큐에 단어가 없습니다.");
               }
        }

        public void declareOutputFields(OutputFieldsDeclarer declarer) {
        	declarer.declare(new Fields("splitWord"));
        }
        
        private void splitWord(String value) {
        	StringTokenizer st = new StringTokenizer(value);
        	String wordStore = st.nextToken();
        	
        	if(wordStore.equals("RT")) {
        		
        	}else {
        		queue.offer(wordStore);
        		while(st.hasMoreTokens()) {
        			wordStore = st.nextToken();
        			queue.offer(wordStore);
        		}
        	}
        }// 단어 끊은 다음 RT 는 거르고 나머지를 큐에 넣어놓는 메소드
}