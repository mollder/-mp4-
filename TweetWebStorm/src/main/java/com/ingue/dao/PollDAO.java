package com.ingue.dao;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;



import org.yaml.snakeyaml.serializer.Serializer;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


public class PollDAO implements Serializable{
   
   private static final long serialVersionUID = -8297242073699292665L;
   List<PollDTO> a = null;
   DBCollection coll;
   ConnectionMongo b;
   
   public PollDAO() {
	   b = new ConnectionMongo();
	   coll = b.getCollection();
   }
   
   public void insertPollDTO(Object dtoData) {
	      BasicDBObject object = new BasicDBObject();
	       object.put("getPollNum",((PollDTO) dtoData).getPollNum());
	       object.put("pollNum",2);
	       object.put("angle", ((PollDTO) dtoData).getAngle());
	       object.put("temperature",((PollDTO) dtoData).getTemperature());
	       object.put("pressure", ((PollDTO) dtoData).getPressure());
	       object.put("liveWireNum", ((PollDTO) dtoData).getLiveWireNum());
	       this.coll.insert(object);
	       System.out.println("»ðÀÔ¼º°ø");
	    }
}
   