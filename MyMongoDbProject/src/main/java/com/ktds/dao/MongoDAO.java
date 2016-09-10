package com.ktds.dao;
 
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoDAO {
    
    private MongoTemplate mongoTemplate;
 
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    
    public void insertStudentOne(int id, String name, int studentNum ){
    	
    	MongoDTOstudent student = new MongoDTOstudent();
    	student.setName("홍길동");
        student.setId(id);
        student.setStudentNum(studentNum);
        
        // student : collection 명
        mongoTemplate.insert(student, "student");
        System.out.println("삽입 성공");
    }
    
    public void deleteStudentOne(int id, String name, int studentNum) {
    	Criteria criteria = new Criteria("name");
    	criteria.is(name);
    	criteria.and("id").is(id);
    	criteria.and("studentNum").is(studentNum);
    	
    	Query query = new Query(criteria);
    	mongoTemplate.remove(query, "student");
    	System.out.println("삭제 성공");
    }
    
    public MongoDTOstudent findStudentByAll(String name,int id, int studentNum){
        Criteria criteria = new Criteria("name");
        criteria.is(name);
        criteria.and("id").is(id);
        criteria.and("studentNum").is(studentNum);
        
        Query query = new Query(criteria);
        MongoDTOstudent  student2 = mongoTemplate.findOne(query, MongoDTOstudent.class, "student");
        return student2;
    }
    
    public List findStudentByName(String name) {
    	Criteria criteria = new Criteria("name");
        criteria.is(name);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentById(int id) {
    	Criteria criteria = new Criteria("id");
        criteria.is(id);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentByStudentNum(int studentNum) {
    	Criteria criteria = new Criteria("studentNum");
        criteria.is(studentNum);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentByStudentNumAndId(int studentNum, int id) {
    	Criteria criteria = new Criteria("studentNum");
        criteria.is(studentNum);
        criteria.and("id").is(id);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentByStudentNumAndName(int studentNum, String name) {
    	Criteria criteria = new Criteria("studentNum");
        criteria.is(studentNum);
        criteria.and("name").is(name);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentByNameAndId(String name, int id) {
    	Criteria criteria = new Criteria("name");
        criteria.is(name);
        criteria.and("id").is(id);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentByStudentNumAndIdAndName(String name, int id, int studentNum) {
    	Criteria criteria = new Criteria("name");
        criteria.is(name);
        criteria.and("id").is(id);
        criteria.and("studentNum").is(studentNum);
        
        Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
    
    public List findStudentAll() {
    	List<MongoDTOstudent> studentList = mongoTemplate.findAll(MongoDTOstudent.class, "student");
    	return studentList;
    }
    
    public int getDataSize() {
    	List<MongoDTOstudent> studentList = mongoTemplate.findAll(MongoDTOstudent.class, "student");
    	return studentList.size();
    }
    
    public int getPageNum() {
    	List<MongoDTOstudent> studentList = mongoTemplate.findAll(MongoDTOstudent.class, "student");
    	int pageNum = ((studentList.size()-1)/10) + 1;
    	return pageNum;
    }
    
    public int getPageByPageNum() {
    	List<MongoDTOstudent> studentList = mongoTemplate.findAll(MongoDTOstudent.class, "student");
    	int pageNum = ((studentList.size()-1)/10) + 1;
    	int pageByPageNum = ((pageNum-1)/10) + 1;
    	return pageByPageNum;
    }
    
    public List tenStudentDivByPageNum(int pageNum) {
    	Criteria criteria = new Criteria("id");
    	criteria.gte((pageNum-1)*10+1).lte((pageNum)*10);

    	Query query = new Query(criteria);
        List<MongoDTOstudent> studentList = mongoTemplate.find(query, MongoDTOstudent.class, "student");
        return studentList;
    }
}