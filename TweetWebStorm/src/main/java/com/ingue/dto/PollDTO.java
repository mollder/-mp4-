package com.ingue.dto;

import java.io.Serializable;

// 전신주 데이터 포맷 클래스
public class PollDTO implements Serializable{

	@Override
	public String toString() {
		return "PollDTO [pollNum=" + pollNum + ", angle=" + angle
				+ ", temperature=" + temperature + ", pressure=" + pressure
				+ ", liveWireNum=" + liveWireNum + "]";
	}

	private static final long serialVersionUID = -1307505116232650700L;
	private int pollNum;
	private double angle;
	private double temperature;
	private double pressure;
	private int liveWireNum;
	private boolean wireOk;
	private boolean temperOk;
	private boolean angleOk;
	private boolean pressOk;
	
	public PollDTO() {
		
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
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

	public int getLiveWireNum() {
		return liveWireNum;
	}

	public void setLiveWireNum(int liveWireNum) {
		this.liveWireNum = liveWireNum;
	}

	public boolean isPressOk() {
		return pressOk;
	}

	public void setPressOk(boolean pressOk) {
		this.pressOk = pressOk;
	}

	public boolean isAngleOk() {
		return angleOk;
	}

	public void setAngleOk(boolean angleOk) {
		this.angleOk = angleOk;
	}

	public boolean isTemperOk() {
		return temperOk;
	}

	public void setTemperOk(boolean temperOk) {
		this.temperOk = temperOk;
	}

	public boolean isWireOk() {
		return wireOk;
	}

	public void setWireOk(boolean wireOk) {
		this.wireOk = wireOk;
	}

}