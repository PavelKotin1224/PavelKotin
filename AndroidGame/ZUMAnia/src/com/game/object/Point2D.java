package com.game.object;

public class Point2D {
	public float x;
	public float y;
	
	public Point2D(){
		
	}
	
	public Point2D(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public void setPos(Point2D src) {
		x = src.x;
		y = src.y;
	}
	
	public int getDistance(Point2D other) {
		int distance;
		distance = (int)Math.sqrt(Math.pow((other.x - x), 2) + Math.pow((other.y - y), 2));
		
		return distance;
	}
	
	public Point2D clone() {
		Point2D newObj = new Point2D(x, y);
		
		return newObj;
	}

}
