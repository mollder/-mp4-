package com.ingue.dto;

import java.io.Serializable;

// 전신주 데이터 포맷 클래스
public class CarDTO implements Serializable{

	@Override
	public String toString() {
		return "PollDTO [pollNum=" + pollNum + ", angle=" + 
	", temperature=" + temperature + ", pressure=" + pressure
				+ ", liveWireNum=" + "]";
	}

	private static final long serialVersionUID = -1307505116232650700L;
	private int pollNum;
	private double temperature;
	private double pressure;
	private int gps;
	private boolean gpsOk;
	private boolean temperOk;
	private boolean pressOk;
	
	public CarDTO() {
		
	}

	public int getPollNum() {
		return pollNum;
	}

	public void setPollNum(int pollNum) {
		this.pollNum = pollNum;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}

	public double getPressure() {
		return pressure;
	}

	public void setPressure(double pressure) {
		this.pressure = pressure;
	}

	public boolean isPressOk() {
		return pressOk;
	}

	public void setPressOk(boolean pressOk) {
		this.pressOk = pressOk;
	}
	
	public boolean isTemperOk() {
		return temperOk;
	}

	public void setTemperOk(boolean temperOk) {
		this.temperOk = temperOk;
	}

	public int getGps() {
		return gps;
	}

	public void setGps(int gps) {
		this.gps = gps;
	}

	public boolean isGpsOk() {
		return gpsOk;
	}

	public void setGpsOk(boolean gpsOk) {
		this.gpsOk = gpsOk;
	}

}