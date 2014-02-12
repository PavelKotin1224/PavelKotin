package com.zumania;

/*
 * Copyright (C) 2011 MGIC
 *
 * Spider Craze 1.0
 * 
 * Spider Craze
 */

import com.component.CustomImageButton;
import com.zumania.R;
import com.zumania.ZUMAnia.SettingPageData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class SettingPage extends CommonPage {

	SettingPageData pageData;

	CustomImageButton vibrationButton;
	CustomImageButton musicButton;
	CustomImageButton soundButton;

	CustomImageButton backButton;

	public final static String VALUE_COMPARE_STR_TRUE = "true";
	public final static String VALUE_COMPARE_STR_FALSE = "false";

	public SettingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (activity != null) {
			pageData = gameData.settingPageData;
		}
	}

	@Override
	protected void addEventListener() {
		soundButton.setOnClickListener(this);
		musicButton.setOnClickListener(this);
		vibrationButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}

	@Override
	public void loadPage(Object parm, Boolean isCanceled) {
		activity.GetState();
		vibrationButton = (CustomImageButton) activity
				.findViewById(R.id.vibration_check_button);
		musicButton = (CustomImageButton) activity
				.findViewById(R.id.music_check_button);
		soundButton = (CustomImageButton) activity
				.findViewById(R.id.sound_check_button);
		backButton = (CustomImageButton) activity
				.findViewById(R.id.back_button);

		backButton.setButtonImages(R.drawable.back_button, 0,
				R.drawable.back_button_pressed, 0);

		if (activity.isCheckVibration == 1) {
			vibrationButton
					.setBackgroundResource(R.drawable.vibration_on_button);
		} else {
			vibrationButton
					.setBackgroundResource(R.drawable.vibration_off_button);
		}

		if (activity.isCheckSound == 1) {
			soundButton.setBackgroundResource(R.drawable.vibration_on_button);
		} else {
			soundButton.setBackgroundResource(R.drawable.vibration_off_button);
		}

		if (activity.isCheckMusic == 1) {
			musicButton.setBackgroundResource(R.drawable.vibration_on_button);
		} else {
			musicButton.setBackgroundResource(R.drawable.vibration_off_button);
		}

		addEventListener();
	}

	@Override
	public void onPause() {

	}

	@Override
	public void onResume() {

	}

	@Override
	public void onShow() {

	}

	@Override
	public void unloadPage() {
		soundButton = null;
		vibrationButton = null;
		musicButton = null;
		backButton = null;
	}

	@Override
	public void onClick(View v) {

		if (v == soundButton) {
			if (activity.isCheckSound == 1) {
				activity.isCheckSound = 0;
				soundButton
						.setBackgroundResource(R.drawable.vibration_off_button);
			} else {
				activity.isCheckSound = 1;
				soundButton
						.setBackgroundResource(R.drawable.vibration_on_button);
			}
		} else if (v == vibrationButton) {
			if (activity.isCheckVibration == 1) {
				activity.isCheckVibration = 0;
				vibrationButton
						.setBackgroundResource(R.drawable.vibration_off_button);
			} else {
				activity.isCheckVibration = 1;
				activity.vibrator.vibrate(1000);
				vibrationButton
						.setBackgroundResource(R.drawable.vibration_on_button);
			}

		} else if (v == musicButton) {
			if (activity.isCheckMusic == 1) {
				activity.isCheckMusic = 0;
				musicButton
						.setBackgroundResource(R.drawable.vibration_off_button);
			} else {
				activity.isCheckMusic = 1;
				musicButton
						.setBackgroundResource(R.drawable.vibration_on_button);
			}

		} else if (v == backButton) {
			activity.SaveState();
			activity.selectPage(R.layout.mainmenu, false, null);
		}
	}
}
