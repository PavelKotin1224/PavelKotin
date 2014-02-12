package com.zumania;

/*
 * Copyright (C) 2011 MGIC
 *
 * Spider Craze 1.0
 * 
 * Spider Craze
 */


import com.zumania.ZUMAnia.GameData;

import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public abstract class CommonPage extends View implements View.OnClickListener {
	/** Owner Activity */
	ZUMAnia activity;
	
	/** Global Game Data */
	public static GameData gameData;
	
	/** Page Viewer */
	public int prevPageId;
	
	/** Page Loading Flag */
	protected boolean isLoaded = false;
	
	/** Page event listener */
	private SurfaceViewRepaintListener surfaceRepaintListener;
	private long surfaceRepaintInterval;
	
	private TimeEventListener timeEventListener;
	private long timeEventInterval;
	
	public CommonPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        activity = ZUMAnia.activity;
        
        // It's only needed by designing
        if (activity != null)
        	gameData = activity.gameData;
	}
    
    /**
     * Set the redraw listener for surface view
     * @return
     */
    public synchronized boolean setSurfaceViewRedrawListener(SurfaceViewRepaintListener listener, long mills) {
    	if (isLoaded)
    		return false;
    	
    	surfaceRepaintListener = listener;
    	if (mills <= 0) {
    		surfaceRepaintListener = null;
    		surfaceRepaintInterval = 0;
    	} else {
    		surfaceRepaintListener = listener;
    		surfaceRepaintInterval = mills;
    	}

    	return true;
    }
    
    /**
     * Get the redraw listener for surface view
     * @return
     */
    public synchronized SurfaceViewRepaintListener getSurfaceViewRedrawListener() {
    	return surfaceRepaintListener;
    }
    
    /**
     * Get the redraw interval for surface view
     * @return
     */
    public synchronized long getSurfaceViewRedrawInterval() {
    	return surfaceRepaintInterval;
    }
    
    /**
     * Set the time event listener
     * @param listener
     */
    public synchronized void setTimeEventListener(TimeEventListener listener, long mills) {
    	if (mills <= 0) {
    		timeEventListener = null;
    		timeEventInterval = 0;
    	} else {
    		timeEventListener = listener;
    		timeEventInterval = mills;
    	}
    }
    
    /**
     * Get the time event listener
     * @return
     */
    public synchronized TimeEventListener getTimeEventListener() {
    	return timeEventListener;
    }
    
    /**
     * Get the time event interval
     * @return
     */
    public synchronized long getTimeEventInterval() {
    	return timeEventInterval;
    }
    
	/**
	 * Loading Page Callback
	 */
	public abstract void loadPage(Object parm, Boolean isCanceled);

	/**
	 * Unloading Page Callback
	 */
	public abstract void unloadPage();

	/**
	 * Dialog Event
	 */
	public Dialog onCreateDialog (int id) {
		return null;
	}
	
	public void onPrepareDialog(int id, Dialog dialog) {
		return;
	}
	/**
	 * Showing Page Callback
	 */
	public abstract void onShow();
	
	/**
	 * Handle Draw Event
	 */
	protected void onDraw() {
		
	}
	
	/**
	 * Pause Event on Activity
	 */
	public abstract void onPause();
	
	/**
	 * Resume Event on Activity
	 */
	public abstract void onResume();
	
	/**
	 * Add Event Listener
	 */
	protected abstract void addEventListener();
}
