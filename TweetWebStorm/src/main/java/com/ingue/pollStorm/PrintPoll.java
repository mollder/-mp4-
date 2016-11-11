package com.ingue.pollStorm;

import com.ingue.dao.*;

// 디비에 단어를 넣는 클래스
public class PrintPoll{
	PollDTO data;
	PollDAO dao;
	
	public PrintPoll() {
		data = new PollDTO();
		dao = new PollDAO();
	}

    
    /*public void printPoll() {
    	for(int i = 0; i < dao.pollSize(); i++) {
    		System.out.println("아이디 : "+dao.findAll().get(i).getPollNum());
    	}
    	System.out.println("총 십만개중  정상 전신주 : "+dao.pollSize()+" 비정상 전신주 개수 : "+(100000-dao.pollSize()));
    }*/

}