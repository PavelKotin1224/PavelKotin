package com.zumania;

/*
 * Copyright (C) 2011 MGIC
 *
 * Spider Craze 1.0
 * 
 * Spider Craze
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameCanvas extends SurfaceView implements SurfaceHolder.Callback {

	SurfaceHolder gameSurface;
	
	// Screen Dimension
	public int gameWidth, gameHeight;
	
	Paint fillPaint = new Paint();
	
	public GameCanvas(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		gameSurface = getHolder();
		gameSurface.addCallback(this);
		
		setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
 		gameWidth = width;
		gameHeight = height;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
	}

	public void draw(SurfaceViewRepaintListener listener, long count) {
		
		Canvas gameBG = null;
		
		if (listener == null)
			return;
		
		try {
			synchronized (gameSurface) {
				gameBG = gameSurface.lockCanvas();
				if (gameBG == null)
					return;
				
				// Clear background
				fillPaint.setColor(Color.WHITE);
				gameBG.drawRect(0, 0, gameWidth, gameHeight, fillPaint);
				
				listener.onSurfaceRedraw(gameBG, count);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (gameBG != null) {
				gameSurface.unlockCanvasAndPost(gameBG);
			}
		}
	}
}
