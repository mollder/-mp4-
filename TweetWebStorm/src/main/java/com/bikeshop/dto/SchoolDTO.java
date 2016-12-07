package com.bikeshop.dto;

import java.io.Serializable;

public class SchoolDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7190138962594316319L;
	private int stdNum;
	private boolean stdOk;
	private String SchoolTime;
	private boolean passOk;
	private int wrongNum;
	
	public int getStdNum() {
		return stdNum;
	}
	public void setStdNum(int stdNum) {
		this.stdNum = stdNum;
	}
	public boolean isStdOk() {
		return this.stdOk;
	}
	public void setStdOk(boolean stdOk) {
		this.stdOk = stdOk;
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
	public boolean isPassOk() {
		return passOk;
	}
	public void setPassOk(boolean passOk) {
		this.passOk = passOk;
	}
}