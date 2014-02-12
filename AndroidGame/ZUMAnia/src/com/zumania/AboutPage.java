package com.zumania;

import com.zumania.R;
import com.component.CustomImageButton;
import com.zumania.ZUMAnia.AboutPageData;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

public class AboutPage extends CommonPage {

	AboutPageData 		pageData;
	CustomImageButton 	backButton;
	TextView			aboutView;
	
	public AboutPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (activity != null) {
        	pageData = gameData.aboutPageData;
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
		aboutView = (TextView) activity.findViewById(R.id.about_textView);
		backButton.setButtonImages(R.drawable.back_button, 0, R.drawable.back_button_pressed, 0);

		aboutView.setText("ZUMAnia Developed and published by XXX.\n\n" +
						  "Copyright(c)2011. All Rights Reserved.");
		aboutView.setTextSize(20.0f);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		activity.selectPage(R.layout.mainmenu, true, null);			
	}

}
