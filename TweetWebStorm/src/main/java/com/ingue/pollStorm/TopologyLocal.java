package com.ingue.pollStorm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
 
/*
 * 1. 세가지 경우를 전부 spout과 bolt로 작업
 * 1-1 등하교
 * 1-2 차량용 gps
 * 1-3 비상벨
 * 2. 이상데이터를 따로 저장하는 테이블을 만들고 거기에 이상데이터들만저장
 * 2-1 이상데이터를 감시하는 task를 따로 만들어서 이상생기면 처리를 하는 클래스 만듬
 * 3. cron table 을 만듬
 * 3-1 몽고디비에 데이터가 일정량 이상 차있을때 삭제를 하는 task 만듬
 */
public class TopologyLocal {
	
	public TopologyLocal() {
		
	}
        public static void main(String args[]){
               TopologyBuilder builder = new TopologyBuilder();
               /*Spout과 Bolt간의 연결 토폴로지는 TopologyBuilder라는 클래스를 통해서 정의한다.*/
               builder.setSpout("CarSpout", new CarSpout(),1);
               builder.setBolt("Bolt1", new GpsSensingBolt(),1).shuffleGrouping("CarSpout");
               builder.setBolt("Bolt2", new TemperSensingBolt(),1).shuffleGrouping("Bolt1");
               builder.setBolt("Bolt3", new PressSensingBolt(),1).shuffleGrouping("Bolt2");
               builder.setBolt("Bolt4", new DbBolt(),1).shuffleGrouping("Bolt3");
               //토폴로지 빌더 이용해서 토폴로지 기본 설정해주는 부분, spout 과 bolt
               
               Config conf = new Config();
               conf.setDebug(true);
               // 토폴로지 기본 환경설정 해주는 부분
               LocalCluster cluster = new LocalCluster();
               //로컬 클러스터 생성 (개발환경용 클러스터로 개발자환경에서 최소한의 설정으로 이용할 수 있게 함)
               
               cluster.submitTopology("TopologyLocal", conf,builder.createTopology());
               // 클러스터에 토폴로지 제출 
               Utils.sleep(1000000000);
               		// kill the LearningStormTopology
               //cluster.killTopology("TopologyLocal");
               		// shutdown the storm test cluster
               //cluster.shutdown();  
        }
}