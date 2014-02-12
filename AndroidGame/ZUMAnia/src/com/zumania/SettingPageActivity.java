package com.zumania;

import com.zumania.R;
import com.component.CustomImageButton;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class SettingPageActivity extends Activity implements View.OnClickListener{

	CustomImageButton vibrationButton;
	CustomImageButton musicButton;
	CustomImageButton soundButton;
	CustomImageButton backButton;
	
	public int isCheckSoundVal;
	public int isCheckMusicVal;
	public int isCheckVibrationVal;
	
	public final static String VALUE_COMPARE_STR_TRUE = "true";
	public final static String VALUE_COMPARE_STR_FALSE= "false";
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		loadPage();
	}
	
	public void unloadPage(){
		vibrationButton = null;
		musicButton = null;
		soundButton = null;
		backButton = null;
		
	}
	
	public void loadPage() {
    	SharedPreferences mPreferences = getSharedPreferences("shareInfo", 0);
    	isCheckMusicVal= mPreferences.getInt("music_option", 0);   	
    	isCheckSoundVal = mPreferences.getInt("sound_option", 0);
    	isCheckVibrationVal = mPreferences.getInt("vibration_option", 0);
    	
		vibrationButton = (CustomImageButton) findViewById(R.id.vibration_check_button);
		musicButton = (CustomImageButton) findViewById(R.id.music_check_button);
		soundButton = (CustomImageButton) findViewById(R.id.sound_check_button);
		backButton = (CustomImageButton) findViewById(R.id.back_button);
		
		backButton.setButtonImages(R.drawable.back_button, 0, R.drawable.back_button_pressed, 0);

		if(isCheckVibrationVal == 1){
			vibrationButton.setBackgroundResource(R.drawable.vibration_on_button);
		} else {
			vibrationButton.setBackgroundResource(R.drawable.vibration_off_button);
		}
		
		if(isCheckSoundVal == 1){
			soundButton.setBackgroundResource(R.drawable.vibration_on_button);
		} else {
			soundButton.setBackgroundResource(R.drawable.vibration_off_button);
		}
		
		if(isCheckMusicVal == 1){
			musicButton.setBackgroundResource(R.drawable.vibration_on_button);
		} else {
			musicButton.setBackgroundResource(R.drawable.vibration_off_button);
		}
		
		addEventListener();
	}
	protected void onPause() {
        super.onPause();
 	}
	protected void onResume() {
        super.onResume();
 	}
	protected void onRestart() {
        super.onRestart();
 	}
	
	 protected void onDestroy() {
		 
		 unloadPage();
	     super.onDestroy();
	 }
	protected void addEventListener() {
		soundButton.setOnClickListener(this);
		musicButton.setOnClickListener(this);
		vibrationButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
	}
	
	 public void destroyApp() {
	    	try{
	            System.gc();
	        }catch(Exception e){}
			finish();
	    }

	@Override
	public void onClick(View view) {
		if (view == soundButton) {
			if (isCheckSoundVal == 1) {
				isCheckSoundVal = 0;
				soundButton.setBackgroundResource(R.drawable.vibration_off_button);
			} else {
				isCheckSoundVal = 1;
				soundButton.setBackgroundResource(R.drawable.vibration_on_button);
			}
		} else if (view == vibrationButton) {
			if (isCheckVibrationVal == 1) {
				isCheckVibrationVal = 0;
				vibrationButton.setBackgroundResource(R.drawable.vibration_off_button);
			} else {
				isCheckVibrationVal = 1;
				ZUMAnia.activity.vibrator.vibrate(1000);
				vibrationButton.setBackgroundResource(R.drawable.vibration_on_button);
			}

		}else if (view == musicButton) {
			if (isCheckMusicVal == 1) {
				isCheckMusicVal = 0;
				musicButton.setBackgroundResource(R.drawable.vibration_off_button);
			} else {
				isCheckMusicVal = 1;
				musicButton.setBackgroundResource(R.drawable.vibration_on_button);
			}

		}  else if(view == backButton){
			
	        SharedPreferences  mPreferences = getSharedPreferences("shareInfo", 0);
			SharedPreferences.Editor editor = mPreferences.edit();
			editor.putInt("music_option", isCheckMusicVal);
			editor.putInt("sound_option", isCheckSoundVal);
			editor.putInt("vibration_option", isCheckVibrationVal);

			editor.commit();
			try {
				destroyApp();
				this.finalize();
				this.finish();
				
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	
}
