package com.game.object;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class BitmapObject {

	protected Activity activity;
	
	protected String name;

	protected Bitmap bgBitmap = null;
	
	protected Paint paint = null;
	
	protected Point2D position2D;
	
	protected float width;
	
	protected float height;

	private int colorVal;
	
	private float xStartPos;
	
	private float xEndPos;
	
	private float yStartPos;
	
	private float yEndPos;

	private int character;
	
	public int characterKind;
	
	public boolean characterFlag;
	
	private int loadIndex;

	public BitmapObject(Activity activity, String name, Point2D location, float width, float height, Bitmap bitmap) {
		
		this.activity = activity;
		this.name = name;
		this.position2D = location.clone();
		this.width = width;
		this.height = height;
	
		characterFlag = false;
		setBitmap(bitmap);
	}
	
	public void removeObject() {
		activity = null;
		name = null;
		bgBitmap = null;
		paint = null;
		position2D = null;
		width = 0;
		height = 0;
	}
	
	public synchronized void setBitmap(Bitmap bitmap) {
		
		bgBitmap = bitmap;
	}
	
	public synchronized void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Point2D getPosition() {
		return position2D;
	}
	
	public MoveObject getMove() {
		return null;
	}
	
	public void setColorVal(int color) {
		colorVal = color;
	}
	
	public int getColorVal() {
		return colorVal;
	}

	public void setXStartPos(float startPos) {
		xStartPos = startPos;
	}
	
	public float getXStartPos() {
		return xStartPos;
	}

	public void setXEndPos(float endPos) {
		xEndPos = endPos;
	}
	
	public float getXEndPos() {
		return xEndPos;
	}

	public void setYStartPos(float startPos) {
		yStartPos = startPos;
	}
	
	public float getYStartPos() {
		return yStartPos;
	}

	public void setYEndPos(float endPos) {
		yEndPos = endPos;
	}
	
	public float getYEndPos() {
		return yEndPos;
	}

	public void setCharacter(int cha) {
		character = cha;
	}
	
	public int getCharacter() {
		return character;
	}
	
	public void setLineIndex(int nIndex) {
		loadIndex = nIndex;
	}
	
	public int getLineIndex() {
		return loadIndex;
	}
	
	public synchronized void onDraw(Canvas c) {

		if (bgBitmap == null)
			return;
		
		Rect src, dest;
		src = new Rect(0, 0, bgBitmap.getWidth(), bgBitmap.getHeight());
		c.drawBitmap(bgBitmap, position2D.x, position2D.y, paint);
	}
}
