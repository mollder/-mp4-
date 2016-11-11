package com.ingue.pollStorm;

import com.ingue.dao.ConnectionMongo;
import com.mongodb.DBCollection;
import com.ingue.dao.*;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import backtype.storm.utils.Utils;
 
public class TopologyLocal {
	
	ConnectionMongo mongoConnection;
	@Autowired
	DBCollection collection;
	
	public TopologyLocal() {
		mongoConnection = new ConnectionMongo();
     	this.collection=mongoConnection.collection;
	}
	
        public static void main(String args[]){
        	
        	   PrintPoll print = new PrintPoll();
               TopologyBuilder builder = new TopologyBuilder();
               /*Spout�� Bolt���� ���� ���������� TopologyBuilder��� Ŭ������ ���ؼ� �����Ѵ�.*/
               builder.setSpout("PollSpout", new PollSpout(),1);
               builder.setBolt("Bolt1", new WireSensingBolt(),1).shuffleGrouping("PollSpout");
               builder.setBolt("Bolt2", new TemperSensingBolt(),1).shuffleGrouping("Bolt1");
               builder.setBolt("Bolt3", new AngleSensingBolt(),1).shuffleGrouping("Bolt2");
               builder.setBolt("Bolt4", new PressSensingBolt(),1).shuffleGrouping("Bolt3");
               //�������� ���� �̿��ؼ� �������� �⺻ �������ִ� �κ�, spout �� bolt
               
               Config conf = new Config();
               conf.setDebug(true);
               // �������� �⺻ ȯ�漳�� ���ִ� �κ�
               LocalCluster cluster = new LocalCluster();
               //���� Ŭ������ ���� (����ȯ��� Ŭ�����ͷ� ������ȯ�濡�� �ּ����� �������� �̿��� �� �ְ� ��)
               
               cluster.submitTopology("TopologyLocal", conf,builder.createTopology());
               // Ŭ�����Ϳ� �������� ���� 
               Utils.sleep(100000);
               // kill the LearningStormTopology
               cluster.killTopology("TopologyLocal");
               // shutdown the storm test cluster
               cluster.shutdown();  
               //print.printPoll();
        }
}