package com.bikeshop.main;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

import com.bikeshop.car.*;
import com.bikeshop.db.DbBolt;
import com.bikeshop.emerbell.*;
import com.bikeshop.school.*;

//하루 한서비스 한사람 0.144 GB 쌓임
public class Topology {
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TopologyBuilder Builder = new TopologyBuilder();
		
		Builder.setSpout("CarSpout", new CarSpout(), 1);
		Builder.setBolt("CarBolt", new CarFilterBolt(),1).shuffleGrouping("CarSpout");
		Builder.setBolt("CarAlarmBolt", new CarAlarmBolt(), 1).shuffleGrouping("CarBolt");
		
		Builder.setSpout("EmerbellSpout", new EmerbellSpout(),1);
		Builder.setBolt("EmerbellBolt", new EmerFilterBolt(),1).shuffleGrouping("EmerbellSpout");
		Builder.setBolt("EmerAlarmBolt", new EmerAlarmBolt(),1).shuffleGrouping("EmerbellBolt");
		
		Builder.setSpout("SchoolSpout", new SchoolSpout(),1);
		Builder.setBolt("SchoolBolt", new SchoolFilterBolt(), 1).shuffleGrouping("SchoolSpout");
		Builder.setBolt("SchoolAlarmBolt", new SchoolAlarmBolt(),1).shuffleGrouping("SchoolBolt");
		
		Builder.setBolt("DbBolt", new DbBolt(), 1 )
			.shuffleGrouping("EmerAlarmBolt")
			.shuffleGrouping("SchoolAlarmBolt")
			.shuffleGrouping("CarAlarmBolt");
		
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
