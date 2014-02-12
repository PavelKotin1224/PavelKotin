package com.zumania;

import com.zumania.R;
import com.component.CustomImageButton;
import com.zumania.ZUMAnia.HelpPageData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class HelpPage extends CommonPage {

	HelpPageData 		pageData;
	CustomImageButton 	backButton;
	TextView			helpView;
	
	public HelpPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (activity != null) {
        	pageData = gameData.helpPageData;
        }
	}

	@Override
	protected void addEventListener() {
		backButton.setOnClickListener(this);
		
	}

	@Override
	public void loadPage(Object parm, Boolean isCanceled) {
		if (isLoaded)
			return;
		
		backButton = (CustomImageButton) activity.findViewById(R.id.back_button);
		helpView = (TextView) activity.findViewById(R.id.help_textView);
		backButton.setButtonImages(R.drawable.back_button, 0, R.drawable.back_button_pressed, 0);
		
		helpView.setText("To win the game you must clear all of the balls before they reach the breakthrough.\n"
				+ "Clear balls by connecting three or more of the same colour in a row to create a set.\n\n"
				+ "By touching the target on the screen. aim the frog and shoot a ball.\n"
				+ "The small ball in the back of the frog shows the colour of the next ball.\n"
				+ "You can swap the curent ball with the next ball by touching the frog.\n\n"
				+ "At the left bottom of the screen is a meter that fills as balls are appered.\n\n"
				+ "If the balls reach the breakthrough you lose 1 life.\n"
				+ "The game is over when you have no more lives.\n\n"
				+ "Powerup Balls:\n"
				+ "Triggered by detonation.\n"
				+ "Slowdown: All balls will slow down.\n"
				+ "Explosion: Destroys all ball in radius.\n"
				+ "Reverse: Causes balls to move backwards for a moment.\n");
		
		if (activity.gameWidth == 800 && activity.gameHeight == 480)
			helpView.setTextSize(12.0f);
		else if (activity.gameWidth == 480 && activity.gameHeight == 320)
			helpView.setTextSize(11.0f);
		else if (activity.gameWidth == 320 && activity.gameHeight == 240)
			helpView.setTextSize(10.0f);
		
		addEventListener();
		
		isLoaded = true;
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
		backButton = null;		
	}

	@Override
	public void onClick(View v) {
		activity.selectPage(R.layout.mainmenu, true, null);		
	}

}
