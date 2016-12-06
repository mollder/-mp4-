package com.bikeshop.schoolAlarm;

import java.util.List;
import java.util.Map;
import com.bikeshop.sms.*;

import com.bikeshop.dto.SchoolDTO;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class SchoolSendingBolt implements IRichBolt  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5727255119626910464L;
	private List<SchoolDTO> list;
	SchoolDTO data;
	
	public SchoolSendingBolt() {
		data = new SchoolDTO();
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		data = (SchoolDTO) input.getValueByField("WrongSchool");
		String d = "학교에 이상";
		SendSMS sms = new SendSMS(d);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("WrongSchool"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
