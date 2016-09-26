package com.terry.tweetStorm;

import java.util.concurrent.LinkedBlockingQueue;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import com.terry.dao.*;

// 디비에 단어를 넣는 클래스
public class InsertBolt extends BaseBasicBolt{
	
	LinkedBlockingQueue<String> queue = null;
	WordDAO dao = null;
	
	public InsertBolt() {
		queue = new LinkedBlockingQueue<String>(300000);
		dao = new WordDAO();
	}

    public void execute(Tuple tuple, BasicOutputCollector collector) {
           // TODO Auto-generated method stub
           String value = tuple.getStringByField("splitWord");
           System.out.println("Tuple value is "+value);
           handleWord(value);
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
    	declarer.declare(new Fields("FinalWord"));
    }
    
    private void handleWord(String word) {
    	if(searchWord(word)) {
    		updateWord(word);
    	}else {
    		insertWord(word);
    	}
    }
    
    private void insertWord(String word) {
    	dao.insertWord(word);
    }
    
    private boolean searchWord(String word) {
    	if(dao.findWordString(word)) {
    		return true;
    	}else {
    		return false;
    	}
    }
    
    private void updateWord(String word) {
    	dao.updateWordNum(word);
    }
}