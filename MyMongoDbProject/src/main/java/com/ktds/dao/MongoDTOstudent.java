package com.ktds.dao;

import org.springframework.data.annotation.Id;

public class MongoDTOstudent{
    private int id;
    private int studentNum;
    private String name;
    
    public int getStudentNum() {
        return studentNum;
    }
    public void setStudentNum(int studentNum) {
        this.studentNum = studentNum;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
    	return id;
    }
    public void setId(int id) {
    	this.id = id;
    }
}