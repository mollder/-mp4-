package com.bikeshop.dto;

import java.io.Serializable;

public class SchoolDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7190138962594316319L;
	private int stdNum;
	private boolean gatePass;
	private String SchoolTime;
	private int wrongNum;
	
	public int getStdNum() {
		return stdNum;
	}
	public void setStdNum(int stdNum) {
		this.stdNum = stdNum;
	}
	public boolean isGatePass() {
		return gatePass;
	}
	public void setGatePass(boolean gatePass) {
		this.gatePass = gatePass;
	}
	public String getSchoolTime() {
		return this.SchoolTime;
	}
	public void setSchoolTime(String SchoolTime) {
		this.SchoolTime = SchoolTime;
	}
	public int getWrongNum() {
		return wrongNum;
	}
	public void setWrongNum(int wrongNum) {
		this.wrongNum = wrongNum;
	}

}
