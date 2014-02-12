package com.component;

/*
 * Copyright (C) 2011 MGIC
 *
 * app.component 1.0
 * 
 * Application Component Package
 */

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Button;

public class CustomImageButton extends Button {
	public int active_img;
	public int deactive_img;
	public int focus_img;
	public int down_img;
	
	public CustomImageButton(Context context) {
		super(context);
		
		recheckImages();
		
		updateButtonImage();
	}
	
	public CustomImageButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		recheckImages();
	}
	
	public void setButtonImages(int active, int deactive, int down, int focus) {
		active_img = active;
		deactive_img = deactive;
		down_img = down;
		focus_img = focus;
		recheckImages();
		
		updateButtonImage();
	}
	
	private void recheckImages() {
		if (active_img == 0)
			return;
		
		if (deactive_img == 0)
			deactive_img = active_img;
		
		if (focus_img == 0)
			focus_img = active_img;
		
		if (down_img == 0)
			down_img = deactive_img;
	}

	@Override
	public void setEnabled(boolean enabled) {
		if (active_img != 0) {
			if (enabled) {
				if (isFocused())
					setBackgroundResource(focus_img);
				else
					setBackgroundResource(active_img);
			} else {
				setBackgroundResource(deactive_img);
			}
		}
		super.setEnabled(enabled);
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (active_img != 0 && isEnabled()) {
			if (event.getAction() == MotionEvent.ACTION_DOWN)
				setBackgroundResource(down_img);
			else
				setBackgroundResource(focus_img);
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
			if (active_img != 0 && isEnabled()) {
				if (event.getAction() == KeyEvent.ACTION_UP) {
					setBackgroundResource(focus_img);
				} else if (event.getAction() == KeyEvent.ACTION_DOWN) {
					setBackgroundResource(down_img);
				}
			}
		}
		
		return super.dispatchKeyEvent(event);
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction,
			Rect previouslyFocusedRect) {
		if (active_img != 0 && isEnabled()) {
			if (focused) {
				setBackgroundResource(focus_img);
			} else {
				setBackgroundResource(active_img);
			}
		}
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
	}

	private void updateButtonImage() {
		if (isEnabled()) {
			if (isFocused())
				setBackgroundResource(focus_img);
			else
				setBackgroundResource(active_img);
		} else {
			setBackgroundResource(deactive_img);
		}
	}
}
