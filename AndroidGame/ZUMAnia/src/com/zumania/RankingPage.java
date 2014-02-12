package com.zumania;

import java.lang.reflect.Field;

import com.component.CustomImageButton;
import com.zumania.R;
import com.zumania.ZUMAnia.RankingPageData;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class RankingPage extends CommonPage {
	
	RankingPageData pageData;
	
	CustomImageButton 	backButton;
	ImageView			firstNumImageView;
	ImageView			secondNumImageView;
	ImageView			thirdNumImageView;
	ImageView			fourNumImageView;
	ImageView			fiveNumImageView;
	ImageView			sixNumImageView;
	ImageView			firstLevelView;
	ImageView			secLevelView;
	
	private final static String SCORE_NUM_NAME = "score_number";
	
	public RankingPage(Context context, AttributeSet attrs) {
		super(context, attrs);
        if (activity != null) {
        	pageData = gameData.rankingPageData;
        }		
	}

	@Override
	protected void addEventListener() {
		backButton.setOnClickListener(this);
	}

	@Override
	public void loadPage(Object parm, Boolean isCanceled) {

		backButton = (CustomImageButton) activity.findViewById(R.id.back_button);
		
		firstNumImageView = (ImageView) activity.findViewById(R.id.firstImageView);
		secondNumImageView = (ImageView) activity.findViewById(R.id.secondImageView);
		thirdNumImageView = (ImageView) activity.findViewById(R.id.thirdImageView);
		fourNumImageView = (ImageView) activity.findViewById(R.id.fourthImageView);
		fiveNumImageView = (ImageView) activity.findViewById(R.id.fifthImageView);
		sixNumImageView = (ImageView) activity.findViewById(R.id.sixthImageView);
		firstLevelView = (ImageView) activity.findViewById(R.id.firstLevelView);
		secLevelView = (ImageView) activity.findViewById(R.id.secLevelView);
		backButton.setButtonImages(R.drawable.back_button, 0, R.drawable.back_button_pressed, 0);

		firstNumImageView.setVisibility(GONE);
		secondNumImageView.setVisibility(GONE);
		thirdNumImageView.setVisibility(GONE);
		fourNumImageView.setVisibility(GONE);
		fiveNumImageView.setVisibility(GONE);
		sixNumImageView.setVisibility(GONE);
		
		//show max score
		int scoreStringCount = String.valueOf(ZUMAniaGamePage.maxScore).length();
		Bitmap bitmap = null;
		
		for (int i = scoreStringCount - 1; i >= 0; i--) {

			int scoreNumber = Integer.parseInt(String.valueOf(ZUMAniaGamePage.maxScore)
					.substring(i, i + 1));

			String fieldName = SCORE_NUM_NAME;
			fieldName += String.valueOf(scoreNumber);

			Field field;
			int fieldId = 0;
			try {
				field = R.drawable.class.getDeclaredField(fieldName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(),
					fieldId);
			
			switch(i)
			{
			case 5: 
				firstNumImageView.setVisibility(VISIBLE);
				firstNumImageView.setImageBitmap(bitmap);
				break;
			case 4:
				secondNumImageView.setVisibility(VISIBLE);
				secondNumImageView.setImageBitmap(bitmap);
				break;
			case 3:
				thirdNumImageView.setVisibility(VISIBLE);
				thirdNumImageView.setImageBitmap(bitmap);
				break;
			case 2:
				fourNumImageView.setVisibility(VISIBLE);
				fourNumImageView.setImageBitmap(bitmap);
				break;
			case 1: 
				fiveNumImageView.setVisibility(VISIBLE);
				fiveNumImageView.setImageBitmap(bitmap);
				break;
			case 0: 
				sixNumImageView.setVisibility(VISIBLE);
				sixNumImageView.setImageBitmap(bitmap);
				break;
			default:
					break;
					
			}
		}

		//show max level
		firstLevelView.setVisibility(GONE);
		secLevelView.setVisibility(GONE);
		
		int levelStringCount = String.valueOf(ZUMAniaGamePage.maxlevel).length();
		Bitmap levelBitmap = null;
		
		for (int i = levelStringCount - 1; i >= 0; i--) {

			int scoreNumber = Integer.parseInt(String.valueOf(ZUMAniaGamePage.maxlevel)
					.substring(i, i + 1));

			String fieldName = SCORE_NUM_NAME;
			fieldName += String.valueOf(scoreNumber);

			Field field;
			int fieldId = 0;
			try {
				field = R.drawable.class.getDeclaredField(fieldName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			levelBitmap = BitmapFactory.decodeResource(activity.getResources(),
					fieldId);
			
			switch(i)
			{
			case 1: 
				secLevelView.setVisibility(VISIBLE);
				secLevelView.setImageBitmap(levelBitmap);
				break;
			case 0: 
				firstLevelView.setVisibility(VISIBLE);
				firstLevelView.setImageBitmap(levelBitmap);
				break;
			default:
					break;
					
			}
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
		backButton = null;
	}

	public void onClick(View v) {
		
		if(v == backButton){
			activity.selectPage(R.layout.mainmenu, true, null);
		} 
	}
	
}	