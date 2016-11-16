package com.ingue.pollStorm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
 
public class TopologyLocal {
	
	public TopologyLocal() {
		
	}
        public static void main(String args[]){
               TopologyBuilder builder = new TopologyBuilder();
               /*Spout과 Bolt간의 연결 토폴로지는 TopologyBuilder라는 클래스를 통해서 정의한다.*/
               builder.setSpout("PollSpout", new PollSpout(),1);
               builder.setBolt("Bolt1", new WireSensingBolt(),1).shuffleGrouping("PollSpout");
               builder.setBolt("Bolt2", new TemperSensingBolt(),1).shuffleGrouping("Bolt1");
               builder.setBolt("Bolt3", new AngleSensingBolt(),1).shuffleGrouping("Bolt2");
               builder.setBolt("Bolt4", new PressSensingBolt(),1).shuffleGrouping("Bolt3");
               builder.setBolt("Bolt5", new DbBolt(),1).shuffleGrouping("Bolt4");
               //토폴로지 빌더 이용해서 토폴로지 기본 설정해주는 부분, spout 과 bolt
               
               Config conf = new Config();
               conf.setDebug(true);
               // 토폴로지 기본 환경설정 해주는 부분
               LocalCluster cluster = new LocalCluster();
               //로컬 클러스터 생성 (개발환경용 클러스터로 개발자환경에서 최소한의 설정으로 이용할 수 있게 함)
               
               cluster.submitTopology("TopologyLocal", conf,builder.createTopology());
               // 클러스터에 토폴로지 제출 
               Utils.sleep(100000);
               // kill the LearningStormTopology
               cluster.killTopology("TopologyLocal");
               // shutdown the storm test cluster
               cluster.shutdown();  
               //print.printPoll();
        }
}