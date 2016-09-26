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
          }/*이 메서드는 Spout이 처음 초기화 될때 한번만 호출되는 메서드로, 데이타 소스로 부터의 연결을 초기화 하는 등의 역할을 한다.*/
          
          public void nextTuple(){
        	  if(queue.isEmpty()) {
        		  System.out.print("이만큼 출력가능"+i);
        		  System.exit(0);
        	  }else{
                 this.collector.emit(new Values(queue.poll()));
                 i +=1;
        	  }
          }/*이 메서드는 데이타 스트림 하나를 읽고 나서, 다음 데이타 스트림을 읽을 때 호출 되는 메서드 이다.*/
          
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
     		         // 어느 지역에 한정되서 검색하도록 수정
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