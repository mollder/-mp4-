package com.bikeshop.dto;

// 위도경도 / 가속도센서 / 비상벨 ID / 현재시간

public class EmerDTO {
	private int emerLocation;
	private int emerAcc;
	private int emerNum;
	private long emerTime;
	private boolean emerLoactionOk;
	private boolean emerAccOk;
	private boolean emerNumOk;
	
	public void EmerDTO() {
		
	}
	
	public int getEmerLocation() {
		return emerLocation;
	}
	public void setEmerLocation(int emerLocation) {
		this.emerLocation = emerLocation;
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
	public long getEmerTime() {
		return emerTime;
	}
	public void setEmerTime(long emerTime) {
		this.emerTime = emerTime;
	}

	public boolean isEmerLoactionOk() {
		return emerLoactionOk;
	}

	public void setEmerLoactionOk(boolean emerLoactionOk) {
		this.emerLoactionOk = emerLoactionOk;
	}

	public boolean isEmerAccOk() {
		return emerAccOk;
	}

	public void setEmerAccOk(boolean emerAccOk) {
		this.emerAccOk = emerAccOk;
	}

	public boolean isEmerNumOk() {
		return emerNumOk;
	}

	public void setEmerNumOk(boolean emerNumOk) {
		this.emerNumOk = emerNumOk;
	}
}
