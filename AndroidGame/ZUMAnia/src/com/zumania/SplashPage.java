package com.zumania;

import java.lang.reflect.Field;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.zumania.R;
import com.zumania.CommonPage;
import com.zumania.SurfaceViewRepaintListener;
import com.zumania.TimeEventListener;
import com.zumania.ZUMAnia.SplashPageData;

public class SplashPage  extends CommonPage implements TimeEventListener, SurfaceViewRepaintListener, OnTouchListener{

	Context context;
	SplashPageData pageData;
	private Handler mHandler;
	private Bitmap loadingBackgroundBitmap = null;
	private Bitmap colorfumzumaCoverBackgroundBitmap = null;
	private Bitmap[] logoAnimationBitmaps = new Bitmap[SPLASH_IMAGE_MAX];
	
	private final static int SPLASH_ANIMATE_INTERVAL = 150;
	
	private final static int SPLASH_IMAGE_MAX = 1;
	private final static String SPLASH_IMAGE_BASE_NAME = "splash";
	
	private int bitmapIndex = 0;
	private int lastBitmapIndex = 0;
	
	private MediaPlayer splash_sound;
	
	public SplashPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// for designing only
        if (activity != null) {
        	pageData = gameData.splashPageData;
        }
        
        setSurfaceViewRedrawListener(this, SPLASH_ANIMATE_INTERVAL);
	}
	
	@Override
	protected void addEventListener() {
		setOnTouchListener(this);
	}

	private void loadBitmap() {
		String fieldName = new String();
		int fieldId;
		Field field;
		
		for (int i = 0 ; i < SPLASH_IMAGE_MAX ; i++) {
			fieldName = String.format("%s%d", SPLASH_IMAGE_BASE_NAME, i + 1);
			try {
				field = R.drawable.class.getDeclaredField(fieldName);
				fieldId = field.getInt((Object) R.drawable.class);
				
				logoAnimationBitmaps[i] = BitmapFactory.decodeResource(activity.gameResources, fieldId);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void loadPage(Object parm, Boolean isCanceled) {
		splash_sound = MediaPlayer.create(this.getContext(), R.raw.splash);
		
		addEventListener();
		mHandler = new Handler();
		loadBitmap();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResume() {
		
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadPage() {
		
		logoAnimationBitmaps = null;
		Utils.releasePlay(splash_sound);
	}

	@Override
	public void onTimeEvent(long count) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceRedraw(Canvas c, long count) {
		
		Rect src = new Rect();
		Rect dest = new Rect(0, 0, getWidth(), getHeight());
		src.left = src.top = 0;
		src.right =  logoAnimationBitmaps[bitmapIndex].getWidth();
		src.bottom = logoAnimationBitmaps[bitmapIndex].getHeight();

		if(lastBitmapIndex == SPLASH_IMAGE_MAX) {
			c.drawBitmap(logoAnimationBitmaps[SPLASH_IMAGE_MAX - 1], src, dest, null);
			return;
		}
		
		if(bitmapIndex == SPLASH_IMAGE_MAX - 1){
			c.drawBitmap(logoAnimationBitmaps[SPLASH_IMAGE_MAX - 1], src, dest, null);
			lastBitmapIndex++;
		} else {
			c.drawBitmap(logoAnimationBitmaps[bitmapIndex], src, dest, null);
			bitmapIndex++;
		}
		
		activity.GetState();
		if(activity.isCheckSound == 1){
			Utils.startPlay(activity.isCheckMusic, splash_sound, true);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
			activity.selectPage(R.layout.mainmenu, false, null);
		
		return false;
	}

	@Override
	public void onClick(View v) {
	}

}
