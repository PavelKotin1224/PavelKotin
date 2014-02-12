package com.game.object;


public class SpeedObject {
	public int wriggleSpeed;
	
	public int xMoveSpeed;
	
	public int yMoveSpeed;
	
	public int stopImageSpeed;
	
	public int imageCount;
	
	public int wriggleTemp;
	
	public int imageTemp;
	
	public int stopImageTemp;
	
	public SpeedObject(int wriggleSpeed, int xMoveSpeed, int yMoveSpeed, int stopImageSpeed, int imageCount){
		this.wriggleSpeed = wriggleSpeed;
		this.xMoveSpeed = xMoveSpeed;
		this.yMoveSpeed = yMoveSpeed;
		this.stopImageSpeed = stopImageSpeed;
		this.imageCount = imageCount;
		
		wriggleTemp = 0;
		imageTemp = 1;
		stopImageTemp = 0;
	}
	
	public void setSpeed(SpeedObject src) {
		this.wriggleSpeed = src.wriggleSpeed;
		this.xMoveSpeed = src.xMoveSpeed;
		this.yMoveSpeed = src.yMoveSpeed;
		this.imageCount = src.imageCount;
		this.stopImageSpeed = src.stopImageSpeed;		
	}
	
	public SpeedObject clone() {
		SpeedObject newObj = new SpeedObject(wriggleSpeed, xMoveSpeed, yMoveSpeed, stopImageSpeed, imageCount);
		return newObj;
	}
}
