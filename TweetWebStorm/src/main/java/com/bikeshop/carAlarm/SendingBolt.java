package com.bikeshop.carAlarm;

import java.util.List;
import java.util.Map;
import com.bikeshop.sms.*;

import com.bikeshop.dto.CarDTO;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class SendingBolt implements IRichBolt  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5727255119626910464L;
	private List<CarDTO> list;
	CarDTO data;
	
	public SendingBolt() {
		data = new CarDTO();
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void execute(Tuple input) {
		// TODO Auto-generated method stub
		data = (CarDTO) input.getValueByField("WrongCar");
		String s = "차에 이상이 생겻네";
		SendSMS sms = new SendSMS(s);
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// TODO Auto-generated method stub
		declarer.declare(new Fields("WrongCar"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
