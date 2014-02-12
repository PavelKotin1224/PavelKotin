package com.zumania;

import com.component.CustomImageButton;
import com.zumania.R;
import com.zumania.ZUMAnia;
import com.zumania.CommonPage;
import com.zumania.ExitDialog;
import com.zumania.MainPage;
import com.zumania.MyDialogListener;
import com.zumania.Utils;
import com.zumania.ZUMAnia.MainPageData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class MainPage extends CommonPage{

	MainPageData pageData;
	Context context;
	
	CustomImageButton startButton;
	CustomImageButton optionButton;
	CustomImageButton scoreButton;
	CustomImageButton aboutButton;
	CustomImageButton exitButton;
	CustomImageButton helpButton;
	
	private MediaPlayer main_sound;
	
	public MainPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;

        if (activity != null) {
        	pageData = gameData.mainPageData;
        }
	}

	@Override
	protected void addEventListener() {
		startButton.setOnClickListener(this);
		optionButton.setOnClickListener(this);
		helpButton.setOnClickListener(this);
		scoreButton.setOnClickListener(this);
		aboutButton.setOnClickListener(this);
		exitButton.setOnClickListener(this);
	}

	@Override
	public void loadPage(Object parm, Boolean isCanceled) {
		startButton = (CustomImageButton) activity.findViewById(R.id.start_button);
		optionButton = (CustomImageButton) activity.findViewById(R.id.option_button);
		helpButton = (CustomImageButton) activity.findViewById(R.id.help_button);
		scoreButton = (CustomImageButton) activity.findViewById(R.id.score_button);
		aboutButton = (CustomImageButton) activity.findViewById(R.id.about_button);
		exitButton = (CustomImageButton) activity.findViewById(R.id.exit_button);

		startButton.setButtonImages(R.drawable.start_button, 0, R.drawable.start_button_pressed, 0);
		optionButton.setButtonImages(R.drawable.option_button, 0, R.drawable.option_button_pressed, 0);
		scoreButton.setButtonImages(R.drawable.score_button, 0, R.drawable.score_button_pressed, 0);
		helpButton.setButtonImages(R.drawable.help_button, 0, R.drawable.help_button_pressed, 0);
		aboutButton.setButtonImages(R.drawable.about_button, 0, R.drawable.about_button_pressed, 0);
		exitButton.setButtonImages(R.drawable.exit_button, 0, R.drawable.exit_button_pressed, 0);
		
		if(Utils.readFile(ZUMAnia.path, ZUMAnia.dataFilename) == null){
//			continueButton.setVisibility(GONE);
		}
		
		activity.GetState();
		if(activity.isCheckSound == 1 && activity.isCheckMusic == 1){
			main_sound = MediaPlayer.create(this.getContext(), R.raw.main);
			Utils.startPlay(activity.isCheckSound, main_sound, true);
		}
		
		addEventListener();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onShow() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadPage() {
		activity.GetState();
		if(activity.isCheckSound == 1){
			Utils.releasePlay(main_sound);
		}
	}

	@Override
	public void onClick(View v) {
		if (v == startButton){
			activity.selectPage(R.layout.selectlevel, false, null);
		} else if (v == optionButton){
			activity.selectPage(R.layout.setting, false, null);
		} else if (v == exitButton){
			ExitDialog dialog = new ExitDialog(context,new MyDialogListener() {

				@Override
				public void onCancelClick() {
					
				}

				@Override
				public void onOkClick() {
					try {
						MainPage.this.finalize();
						activity.finish();
					} catch (Throwable e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		);		
			dialog.show();
		} else if (v == scoreButton){
			activity.selectPage(R.layout.ranking, false, null);
		} else if (v == helpButton) {
			activity.selectPage(R.layout.help, false, null);
		} else if (v == aboutButton) {
			activity.selectPage(R.layout.about, false, null);
		}
	}
}
