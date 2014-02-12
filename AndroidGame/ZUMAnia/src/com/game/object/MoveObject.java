package com.game.object;

public class MoveObject {
	public int xMoveEndWidth;
	
	// Plus 1
	// minus -1
	// stop 0
	
	public int xMoveDirection;
	
	public int yMoveEndHeight;
	
	public int yMoveDirection;

	public MoveObject(int xMoveEndWidth, int xMoveDirection, int yMoveEndHeight, int yMoveDirection){
		this.xMoveEndWidth = xMoveEndWidth;
		this.xMoveDirection = xMoveDirection;
		this.yMoveEndHeight = yMoveEndHeight;
		this.yMoveDirection = yMoveDirection;
	}
	
	public void setMove(MoveObject src) {
		this.xMoveEndWidth = src.xMoveEndWidth;
		this.xMoveDirection = src.xMoveDirection;
		this.yMoveEndHeight = src.yMoveEndHeight;
		this.yMoveDirection = src.yMoveDirection;
	}
	
	public MoveObject clone() {
		MoveObject newObj = new MoveObject(xMoveEndWidth, xMoveDirection, yMoveEndHeight, yMoveDirection);
		return newObj;
	}
}
