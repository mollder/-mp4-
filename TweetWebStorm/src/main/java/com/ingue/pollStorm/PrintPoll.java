package com.ingue.pollStorm;

import com.ingue.dao.*;

// ��� �ܾ �ִ� Ŭ����
public class PrintPoll{
	PollDTO data;
	PollDAO dao;
	
	public PrintPoll() {
		data = new PollDTO();
		dao = new PollDAO();
	}

    
    /*public void printPoll() {
    	for(int i = 0; i < dao.pollSize(); i++) {
    		System.out.println("���̵� : "+dao.findAll().get(i).getPollNum());
    	}
    	System.out.println("�� �ʸ�����  ���� ������ : "+dao.pollSize()+" ������ ������ ���� : "+(100000-dao.pollSize()));
    }*/

}