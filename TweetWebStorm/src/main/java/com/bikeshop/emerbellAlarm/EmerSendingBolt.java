package com.bikeshop.emerbellAlarm;

import java.util.List;
import java.util.Map;
import com.bikeshop.sms.*;

import com.bikeshop.dto.EmerDTO;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class EmerSendingBolt implements IRichBolt  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5727255119626910464L;
	private List<EmerDTO> list;
	EmerDTO data;
	
	public EmerSendingBolt() {
		data = new EmerDTO();
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		data = (EmerDTO) input.getValueByField("WrongEmer");
		String a = "emer";
		SendSMS sms = new SendSMS(a);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("WrongEmer"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
