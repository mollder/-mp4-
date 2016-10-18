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
        	
        	System.out.println("Ű���带 �Է��Ͻÿ� : ");
        	keyword = scan.nextLine();
        	
               TopologyBuilder builder = new TopologyBuilder();
               /*Spout�� Bolt���� ���� ���������� TopologyBuilder��� Ŭ������ ���ؼ� �����Ѵ�.*/
               builder.setSpout("TweetSpout", new TweetSpout(keyword),2);
               builder.setBolt("SplitBolt", new SplitBolt(),4).shuffleGrouping("TweetSpout");
               builder.setBolt("InsertBolt", new InsertBolt(), 4).shuffleGrouping("SplitBolt");
               //�������� ���� �̿��ؼ� �������� �⺻ �������ִ� �κ�, spout �� bolt
               
               Config conf = new Config();
               conf.setDebug(true);
               // �������� �⺻ ȯ�漳�� ���ִ� �κ�
               LocalCluster cluster = new LocalCluster();
               //���� Ŭ������ ���� (����ȯ��� Ŭ�����ͷ� ������ȯ�濡�� �ּ����� �������� �̿��� �� �ְ� ��)
               
               cluster.submitTopology("TopologyLocal", conf,builder.createTopology());
               // Ŭ�����Ϳ� �������� ���� 
               Utils.sleep(10000);
               // kill the LearningStormTopology
               cluster.killTopology("TopologyLocal");
               // shutdown the storm test cluster
               cluster.shutdown();          
        }
}