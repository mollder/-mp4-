package com.bikeshop.main;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import com.bikeshop.*;
import com.bikeshop.car.*;
import com.bikeshop.carAlarm.SendingBolt;
import com.bikeshop.carAlarm.alarmSpout;
import com.bikeshop.emerbell.*;
import com.bikeshop.emerbellAlarm.EmerAlarmSpout;
import com.bikeshop.emerbellAlarm.EmerSendingBolt;
import com.bikeshop.school.*;
import com.bikeshop.schoolAlarm.SchoolAlarmSpout;
import com.bikeshop.schoolAlarm.SchoolSendingBolt;

//하루 한서비스 한사람 0.144 GB 쌓임
public class Topology {
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TopologyBuilder Builder = new TopologyBuilder();
		
		Builder.setSpout("CarSpout", new CarSpout(), 1);
		Builder.setBolt("PressBolt", new CarPressBolt(),1).shuffleGrouping("CarSpout");
		Builder.setBolt("CarDbBolt", new CarDbBolt(),1).shuffleGrouping("PressBolt");
		
		Builder.setSpout("CarAlarmSpout", new alarmSpout(), 1);
		Builder.setBolt("CarAlarmBolt", new SendingBolt(),1).shuffleGrouping("CarAlarmSpout");
		
		Builder.setSpout("EmerbellSpout", new EmerbellSpout(),1);
		Builder.setBolt("EmerAccBolt", new EmerAccBolt(),1).shuffleGrouping("EmerbellSpout");
		Builder.setBolt("EmerDbBolt", new EmerDbBolt(),1).shuffleGrouping("EmerAccBolt");
		
		Builder.setSpout("EmerAlarmSpout", new EmerAlarmSpout(), 1);
		Builder.setBolt("EmerSendingBolt", new EmerSendingBolt(), 1).shuffleGrouping("EmerAlarmSpout");
		
		Builder.setSpout("SchoolSpout", new SchoolSpout(),1);
		Builder.setBolt("SchoolBolt", new SchoolDbBolt(), 1).shuffleGrouping("SchoolSpout");

		Builder.setSpout("SchoolAlarmSpout", new SchoolAlarmSpout(), 1);
		Builder.setBolt("SchoolSendingBolt", new SchoolSendingBolt(), 1).shuffleGrouping("SchoolAlarmSpout");
		
        Config conf = new Config();
        conf.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        
        cluster.submitTopology("Topology", conf,Builder.createTopology());
        
       
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
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        Utils.sleep(1000000000);
        				
        //cluster.killTopology("TopologyLocal");
        //cluster.shutdown();  	
	}
}
