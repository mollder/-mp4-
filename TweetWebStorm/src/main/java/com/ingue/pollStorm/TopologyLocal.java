package com.ingue.pollStorm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;
 
/*
 * 1. ������ ��츦 ���� spout�� bolt�� �۾�
 * 1-1 ���ϱ�
 * 1-2 ������ gps
 * 1-3 ���
 * 2. �̻����͸� ���� �����ϴ� ���̺��� ����� �ű⿡ �̻����͵鸸����
 * 2-1 �̻����͸� �����ϴ� task�� ���� ���� �̻����� ó���� �ϴ� Ŭ���� ����
 * 3. cron table �� ����
 * 3-1 ������ �����Ͱ� ������ �̻� �������� ������ �ϴ� task ����
 */
public class TopologyLocal {
	
	public TopologyLocal() {
		
	}
        public static void main(String args[]){
               TopologyBuilder builder = new TopologyBuilder();
               /*Spout�� Bolt���� ���� ���������� TopologyBuilder��� Ŭ������ ���ؼ� �����Ѵ�.*/
               builder.setSpout("CarSpout", new CarSpout(),1);
               builder.setBolt("Bolt1", new GpsSensingBolt(),1).shuffleGrouping("CarSpout");
               builder.setBolt("Bolt2", new TemperSensingBolt(),1).shuffleGrouping("Bolt1");
               builder.setBolt("Bolt3", new PressSensingBolt(),1).shuffleGrouping("Bolt2");
               builder.setBolt("Bolt4", new DbBolt(),1).shuffleGrouping("Bolt3");
               //�������� ���� �̿��ؼ� �������� �⺻ �������ִ� �κ�, spout �� bolt
               
               Config conf = new Config();
               conf.setDebug(true);
               // �������� �⺻ ȯ�漳�� ���ִ� �κ�
               LocalCluster cluster = new LocalCluster();
               //���� Ŭ������ ���� (����ȯ��� Ŭ�����ͷ� ������ȯ�濡�� �ּ����� �������� �̿��� �� �ְ� ��)
               
               cluster.submitTopology("TopologyLocal", conf,builder.createTopology());
               // Ŭ�����Ϳ� �������� ���� 
               Utils.sleep(1000000000);
               		// kill the LearningStormTopology
               //cluster.killTopology("TopologyLocal");
               		// shutdown the storm test cluster
               //cluster.shutdown();  
        }
}