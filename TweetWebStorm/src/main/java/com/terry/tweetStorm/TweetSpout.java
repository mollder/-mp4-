package com.terry.tweetStorm;
 
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class TweetSpout extends BaseRichSpout {
	
		  private static final long serialVersionUID = 1L;
          private SpoutOutputCollector collector;
          LinkedBlockingQueue<String> queue = null;
          String keyword;
          int i = 0;
          
          public TweetSpout(String keyword) {
        	  this.keyword = keyword;
        	  queue = new LinkedBlockingQueue<String>(15000);
              AccessTweet(keyword);
          }
         
          public void open(Map conf,TopologyContext context,SpoutOutputCollector collector){
               this.collector = collector;
          }/*�� �޼���� Spout�� ó�� �ʱ�ȭ �ɶ� �ѹ��� ȣ��Ǵ� �޼����, ����Ÿ �ҽ��� ������ ������ �ʱ�ȭ �ϴ� ���� ������ �Ѵ�.*/
          
          public void nextTuple(){
        	  if(queue.isEmpty()) {
        		  System.out.print("�̸�ŭ ��°���"+i);
        		  System.exit(0);
        	  }else{
                 this.collector.emit(new Values(queue.poll()));
                 i +=1;
        	  }
          }/*�� �޼���� ����Ÿ ��Ʈ�� �ϳ��� �а� ����, ���� ����Ÿ ��Ʈ���� ���� �� ȣ�� �Ǵ� �޼��� �̴�.*/
          
          public void declareOutputFields(OutputFieldsDeclarer declarer){
                 declarer.declare(new Fields("tweet"));
          }
          
          public void AccessTweet(String keyWord) {
          	  try {
     		        AccessToken accesstoken = new AccessToken("779212357524295680-1UlstpXT5s2gWDCESXTd1adPhIAUT7K", "6GU4F0Es1g2e2iwi7UG5cE7onhaP0EglricYiRHnQerlf");
     		        Twitter twitter = TwitterFactory.getSingleton();
     		        twitter.setOAuthConsumer("51wPX7Qf120uMqMU1ojh3K421", "XBM7Z1HUbaFcbrfrMrh2R0vyEVNwJ3ClA5NBDdp8Xxdqp71YJc");
     		        twitter.setOAuthAccessToken(accesstoken);
     		        User user = twitter.verifyCredentials();
     		         // ��� ������ �����Ǽ� �˻��ϵ��� ����
     		        Query query = new Query(keyWord);
     		        query.setCount(100);
     		        QueryResult twitResult;
     		        int i = 0;
   		        
   		        while(i< 1000)	{
     		        twitResult = twitter.search(query);
     		        List<Status> list = twitResult.getTweets();
     		        for(Status status : list) {
     		        	queue.offer(status.getText());
     		        	i+=1;
     		        }
     		    }
          }catch (Exception e) {
		        e.printStackTrace();
		    }
          }
}