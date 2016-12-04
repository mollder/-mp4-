package com.bikeshop.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import com.bikeshop.alarm.SendSMS;
import com.bikeshop.alarm.SendingBolt;
import com.bikeshop.alarm.alarmSpout;
import com.bikeshop.car.*;
import com.bikeshop.emerbell.*;
import com.bikeshop.school.*;

//하루 한서비스 한사람 0.144 GB 쌓임
public class Topology {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TopologyBuilder carBuilder = new TopologyBuilder();
		TopologyBuilder EmerBuilder = new TopologyBuilder();
		TopologyBuilder SchoolBuilder = new TopologyBuilder();
		TopologyBuilder carSensingBuilder = new TopologyBuilder();

        carBuilder.setSpout("CarSpout", new CarSpout(), 1);
        carBuilder.setBolt("PressBolt", new CarPressBolt(),1).shuffleGrouping("CarSpout");
        carBuilder.setBolt("CarDbBolt", new CarDbBolt(),1).shuffleGrouping("PressBolt");
        
        carSensingBuilder.setSpout("alarmSpout1", new alarmSpout(), 1);
        carSensingBuilder.setBolt("alarmBolt1", new SendingBolt(),1).shuffleGrouping("alarmSpout1");
        
        
        //EmerBuilder.setSpout("EmerbellSpout", new EmerbellSpout(),1);
        //EmerBuilder.setBolt("EmerAccBolt", new EmerAccBolt(),1).shuffleGrouping("EmerbellSpout");
        //EmerBuilder.setBolt("EmerLocaBolt", new EmerLocaBolt(),1).shuffleGrouping("EmerAccBolt");
        //EmerBuilder.setBolt("EmerDbBolt", new EmerDbBolt(),1).shuffleGrouping("EmerLocaBolt");
        
        //SchoolBuilder.setSpout("SchoolSpout", new SchoolSpout(),1);

        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("CarTopology", conf,carBuilder.createTopology());
        cluster.submitTopology("CarAlarmTopologh", conf, carSensingBuilder.createTopology());
        //cluster.submitTopology("EmerTopology", conf,EmerBuilder.createTopology());
        //cluster.submitTopology("SchoolTopology", conf,SchoolBuilder.createTopology());
  
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000); // 무한루프
        		
        //cluster.killTopology("TopologyLocal");
        //cluster.shutdown();  
		
	}
}
