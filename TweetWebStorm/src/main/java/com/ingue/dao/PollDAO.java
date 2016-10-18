package com.terry.dao;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class WordDAO {
	
	private MongoTemplate mongoTemplate;
	WordDTO[] wordRepo;
	
	public WordDAO() {
		wordRepo = new WordDTO[100000];
	}
	
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    public boolean findWordString(String word) {
    	Criteria criteria = new Criteria("word");
    	criteria.is(word);
    	
    	Query query = new Query(criteria);
    	WordDTO dto = mongoTemplate.findOne(query, WordDTO.class, "Word");
    	
    	if(dto.getWord() !=null) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    public int findWordNum(String word) {
    	int num = 0;
    	Criteria criteria = new Criteria("word");
    	criteria.is(word);
    	
    	Query query = new Query(criteria);
    	WordDTO dto = mongoTemplate.findOne(query, WordDTO.class, "Word");
    	num = dto.getNum();
    	
    	return num;
    }
    
    public boolean updateWordNum(String word) {
    	int num = 0;
    	num = findWordNum(word);
    	
    	Update update = new Update();
    	update.set("num", num+1);
    	
    	Criteria criteria = new Criteria("word");
    	criteria.is(word);
    	
    	Query query = new Query(criteria);

    	mongoTemplate.updateFirst(query, update, WordDTO.class);
    	return true;
    }
    
    public List findAll() {
    	List<WordDTO> dtoList = mongoTemplate.findAll(WordDTO.class, "word");
    	return dtoList;
    }
    
    public boolean insertWord(String word) {
    	WordDTO dto = new WordDTO();
    	dto.setWord(word);
    	dto.setNum(1);
    	
    	// num = 1 로 집어넣고 , 인덱스는 알고리즘 생각후에 집어넣음 , word는 그대로 word로 집어넣음.
    	mongoTemplate.insert(dto, "word");
    	return true;
    }
    
    public int WordNumTopTen() {
    	return 0;
    }
    
    public List<WordDTO> WordGteTen() {
    	Criteria criteria = new Criteria("num");
    	criteria.gte(10);
    	
    	Query query = new Query(criteria);
    	
    	List<WordDTO> dtoList = mongoTemplate.find(query, WordDTO.class, "word");
    	return dtoList;
    }
    
    public void sortWord() {
    	List<WordDTO> dtoList = findAll();
    }
    
    
}