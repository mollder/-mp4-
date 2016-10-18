package com.terry.tweetStorm;
 
import java.util.Scanner;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
 
public class TopologyLocal {
	
        public static void main(String args[]){
        	
        	String keyword;
        	Scanner scan = new Scanner(System.in);
        	
        	System.out.println("키워드를 입력하시오 : ");
        	keyword = scan.nextLine();
        	
               TopologyBuilder builder = new TopologyBuilder();
               /*Spout과 Bolt간의 연결 토폴로지는 TopologyBuilder라는 클래스를 통해서 정의한다.*/
               builder.setSpout("TweetSpout", new TweetSpout(keyword),2);
               builder.setBolt("SplitBolt", new SplitBolt(),4).shuffleGrouping("TweetSpout");
               builder.setBolt("InsertBolt", new InsertBolt(), 4).shuffleGrouping("SplitBolt");
               //토폴로지 빌더 이용해서 토폴로지 기본 설정해주는 부분, spout 과 bolt
               
               Config conf = new Config();
               conf.setDebug(true);
               // 토폴로지 기본 환경설정 해주는 부분
               LocalCluster cluster = new LocalCluster();
               //로컬 클러스터 생성 (개발환경용 클러스터로 개발자환경에서 최소한의 설정으로 이용할 수 있게 함)
               
               cluster.submitTopology("TopologyLocal", conf,builder.createTopology());
               // 클러스터에 토폴로지 제출 
               Utils.sleep(10000);
               // kill the LearningStormTopology
               cluster.killTopology("TopologyLocal");
               // shutdown the storm test cluster
               cluster.shutdown();          
        }
}