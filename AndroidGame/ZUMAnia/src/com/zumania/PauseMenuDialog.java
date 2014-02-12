
package com.zumania;

import com.component.CustomImageButton;
import com.zumania.R;
import com.zumania.MyDialogListener;
import com.zumania.ZUMAnia.GameData;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

import android.app.AlertDialog;
import android.app.Dialog;

public class PauseMenuDialog extends AlertDialog  implements OnClickListener  {

	private CustomImageButton continueBtn;
	private CustomImageButton exitBtn;
	private CustomImageButton optionBtn;
	
	private ZUMAniaGamePage colorfulZumaActivity;
	
	public boolean isSound = true;
	public boolean isVibration = true;
	public boolean isMusic = true;
	
	Context context;
	public final static String VALUE_COMPARE_STR_TRUE = "true";
	public final static String VALUE_COMPARE_STR_FALSE= "false";
	
	protected PauseMenuDialog(Context context, ZUMAniaGamePage activity) {
		super(context);
		colorfulZumaActivity = activity;
		this.context = context;
	}
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.pausemenu);
		
		continueBtn = (CustomImageButton) findViewById(R.id.continue_Btn);
		optionBtn = (CustomImageButton) findViewById(R.id.option_Btn);
		exitBtn = (CustomImageButton) findViewById(R.id.exit_Btn);

		continueBtn.setButtonImages(R.drawable.continue_button, 0,
				R.drawable.continue_button_pressed, 0);
		optionBtn.setButtonImages(R.drawable.pause_option_button, 0,
				R.drawable.pause_option_button_pressed, 0);
		exitBtn.setButtonImages(R.drawable.pause_exit_button, 0,
				R.drawable.pause_exit_button_pressed, 0);

		continueBtn.setOnClickListener(this);
		exitBtn.setOnClickListener(this);
		optionBtn.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View view) {
		
		if(view == continueBtn){
			ZUMAniaGamePage.isIntentPause = false;
			
//			CommonPage.gameData.currPage.page.onResume();
			if (ZUMAniaGamePage.isShinerStatus) {
				colorfulZumaActivity.gameData.gameStatus = ZUMAnia.GAME_NONE;	
			} else {
				colorfulZumaActivity.gameData.gameStatus = ZUMAnia.GAME_START;
			}
			
			ZUMAniaGamePage.bMenuBtnPressed = false;
			
			dismiss();
			
		}else if(view == optionBtn){

			ZUMAniaGamePage.bMenuBtnPressed = false;

			dismiss();
			Intent intent = new Intent(context, SettingPageActivity.class);
			context.startActivity(intent);
			
		}
		else if(view == exitBtn)
		{	
			ExitDialog dialog = new ExitDialog(context,new MyDialogListener() {
				@Override
				public void onCancelClick() {
					
				}

				@Override
				public void onOkClick() {
//					try {
//						this.finalize();
//					} catch (Throwable e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					android.os.Process.killProcess(android.os.Process.myPid());
					ZUMAnia.activity.SaveState();
					ZUMAnia.activity.selectPage(R.layout.mainmenu, false, null);
					ZUMAnia.bExitDlg = true;
					dismiss();
				}
			}
			);
			dialog.show();
		}
	}
}
