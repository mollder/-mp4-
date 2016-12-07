package com.bikeshop.dto;

import java.io.Serializable;

// 위도경도 / 가속도센서 / 비상벨 ID / 현재시간

public class EmerDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7069690277758917245L;
	private int emerAcc;
	private int emerNum;
	private String emerTime;
	private boolean emerOk;
	private int wrongNum;
	
	public void EmerDTO() {
		
	}
	
	public int getEmerAcc() {
		return emerAcc;
	}
	public void setEmerAcc(int emerAcc) {
		this.emerAcc = emerAcc;
	}
	public int getEmerNum() {
		return emerNum;
	}
	public void setEmerNum(int emerNum) {
		this.emerNum = emerNum;
	}
	public String getEmerTime() {
		return emerTime;
	}
	public void setEmerTime(String emerTime) {
		this.emerTime = emerTime;
	}

	public boolean isEmerOk() {
		return emerOk;
	}

	public void setEmerOk(boolean emerOk) {
		this.emerOk = emerOk;
	}

	public int getWrongNum() {
		return wrongNum;
	}

	public void setWrongNum(int wrongNum) {
		this.wrongNum = wrongNum;
	}

}
