package com.bikeshop.dto;

import java.io.Serializable;

// 차량num / 시간  / 충격량 / 상황Ok

public class CarDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6744547027086623257L;
	private int CarNum;
	private int CarPress;
	private boolean CarOk;
	private String time;
	private int wrongNum;
	
	public void CarDTO() {
		
	}
	
	public int getCarNum() {
		return CarNum;
	}
	public void setCarNum(int carNum) {
		CarNum = carNum;
	}
	public int getCarPress() {
		return CarPress;
	}
	public void setCarPress(int carPress) {
		CarPress = carPress;
	}
	public boolean isCarOk() {
		return CarOk;
	}
	public void setCarOk(boolean carOk) {
		CarOk = carOk;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}

	public int getWrongNum() {
		return wrongNum;
	}

	public void setWrongNum(int wrongNum) {
		this.wrongNum = wrongNum;
	}
	

}
