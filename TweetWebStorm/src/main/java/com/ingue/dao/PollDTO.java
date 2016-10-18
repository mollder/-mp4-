package com.terry.dao;

// 단어 데이터 포맷 클래스
public class WordDTO {

	private int num = 0;
	private String word;
	
	public WordDTO() {
		
	}

	public WordDTO(int num, String word) {
		this.num = num;
		this.word = word;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}