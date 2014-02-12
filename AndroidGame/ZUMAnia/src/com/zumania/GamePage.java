package com.zumania;

/*
 * Copyright (C) 2011 MGIC
 *
 * Spider Craze 1.0
 * 
 * Spider Craze
 */

import java.util.Vector;

import com.game.object.BitmapObject;
import com.zumania.ZUMAnia;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class GamePage extends CommonPage implements SurfaceViewRepaintListener {
	Vector<BitmapObject> gameObjects = new Vector<BitmapObject>();
	
	protected static Point viewPoint = new Point(0, 0);
	protected static int screenX = 0;
	protected static int screenY = 0;
	
	public GamePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	protected synchronized void addRefObject(BitmapObject object) {
		gameObjects.add(object);
	}
	
	protected synchronized void addRefObject(BitmapObject object, int index) {
		gameObjects.insertElementAt(object, index);
	}
	
	protected synchronized void removeRefObject(BitmapObject object) {
		gameObjects.remove(object);
	}
	
	protected synchronized void removeAllRefObjects() {
		gameObjects.clear();
	}
	
	public synchronized void setViewPoint(Point point) {
		viewPoint = new Point(point);
	}
	
	public synchronized void setScreenDeviation (int x, int y) {
		screenX = x;
		screenY = y;
	}
	
	@Override
	protected void addEventListener() {

	}

	@Override
	public void loadPage(Object parm, Boolean isCanceled) {

	}

	@Override
	public void onPause() {
		
	}

	@Override
	public void onResume() {
		//add code load sound 
		
		gameData.gameStatus = ZUMAnia.GAME_START;
	}

	@Override
	public void onShow() {

	}

	@Override
	public void unloadPage() {

	}

	@Override
	public void onClick(View v) {
		
	}

	@Override
	public synchronized void onSurfaceRedraw(Canvas c, long count) {
		// Draw objects
		BitmapObject object;
		int i;
		
		for (i = 0 ; i < gameObjects.size() ; i++) {
			object = gameObjects.get(i);
			
			if(object != null)
				object.onDraw(c);
		}
	}

}
