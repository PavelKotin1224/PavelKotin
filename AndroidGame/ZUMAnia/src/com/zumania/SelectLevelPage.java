package com.zumania;

import java.lang.reflect.Field;
import java.util.Vector;

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
import android.widget.ImageView;

import com.zumania.R;
import com.component.CustomImageButton;
import com.game.object.BitmapObject;
import com.zumania.CommonPage;
import com.zumania.SurfaceViewRepaintListener;
import com.zumania.TimeEventListener;
import com.zumania.ZUMAnia.SelectLevelData;
import com.zumania.ZUMAnia.SplashPageData;

public class SelectLevelPage  extends CommonPage{

	Context context;
	SelectLevelData pageData;
	private Handler mHandler;
	private Bitmap loadingBackgroundBitmap = null;
	private Bitmap colorfumzumaCoverBackgroundBitmap = null;
	private Bitmap[] logoAnimationBitmaps = new Bitmap[SPLASH_IMAGE_MAX];
	
	private final static int SPLASH_ANIMATE_INTERVAL = 150;
	
	private final static int SPLASH_IMAGE_MAX = 1;
	private final static String SPLASH_IMAGE_BASE_NAME = "splash";
	
	private int bitmapIndex = 0;
	private int lastBitmapIndex = 0;
	
	Vector<CustomImageButton> slowBallObjects = new Vector<CustomImageButton>();
	
	CustomImageButton level_button1;
	CustomImageButton level_button2;
	CustomImageButton level_button3;
	CustomImageButton level_button4;
	CustomImageButton level_button5;
	CustomImageButton level_button6;
	CustomImageButton level_button7;
	CustomImageButton level_button8;
	CustomImageButton level_button9;
	CustomImageButton level_button10;
	CustomImageButton level_button11;
	CustomImageButton level_button12;
	
	CustomImageButton backButton;
	
	ImageView levelText1;
	ImageView levelText2;
	ImageView levelText3;
	ImageView levelText4;
	ImageView levelText5;
	ImageView levelText6;
	ImageView levelText7;
	ImageView levelText8;
	ImageView levelText9;
	ImageView levelText10;
	ImageView levelText11;
	ImageView levelText12;
	
	private MediaPlayer level_sound;
//	private MediaPlayer splash_sound;
	
	public SelectLevelPage(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// for designing only
        if (activity != null) {
        	pageData = gameData.selectLevelPageData;
        }
	}
	
	@Override
	protected void addEventListener() {
		level_button1.setOnClickListener(this);
		level_button2.setOnClickListener(this);
		level_button3.setOnClickListener(this);
		level_button4.setOnClickListener(this);
		level_button5.setOnClickListener(this);
		level_button6.setOnClickListener(this);
		level_button7.setOnClickListener(this);
		level_button8.setOnClickListener(this);
		level_button9.setOnClickListener(this);
		level_button10.setOnClickListener(this);
		level_button11.setOnClickListener(this);
		level_button12.setOnClickListener(this);
		
		backButton.setOnClickListener(this);
	}

	private void loadBitmap() {
		String fieldName = new String();
		int fieldId;
		Field field;
	}
	
	@Override
	public void loadPage(Object parm, Boolean isCanceled) {
		
		level_button1 = (CustomImageButton) activity.findViewById(R.id.levelButton1);
		level_button2 = (CustomImageButton) activity.findViewById(R.id.levelButton2);
		level_button3 = (CustomImageButton) activity.findViewById(R.id.levelButton3);
		level_button4 = (CustomImageButton) activity.findViewById(R.id.levelButton4);
		level_button5 = (CustomImageButton) activity.findViewById(R.id.levelButton5);
		level_button6 = (CustomImageButton) activity.findViewById(R.id.levelButton6);
		level_button7 = (CustomImageButton) activity.findViewById(R.id.levelButton7);
		level_button8 = (CustomImageButton) activity.findViewById(R.id.levelButton8);
		level_button9 = (CustomImageButton) activity.findViewById(R.id.levelButton9);
		level_button10 = (CustomImageButton) activity.findViewById(R.id.levelButton10);
		level_button11 = (CustomImageButton) activity.findViewById(R.id.levelButton11);
		level_button12 = (CustomImageButton) activity.findViewById(R.id.levelButton12);
		
		levelText1 = (ImageView) activity.findViewById(R.id.LevelText1);
		levelText2 = (ImageView) activity.findViewById(R.id.LevelText2);
		levelText3 = (ImageView) activity.findViewById(R.id.LevelText3);
		levelText4 = (ImageView) activity.findViewById(R.id.LevelText4);
		levelText5 = (ImageView) activity.findViewById(R.id.LevelText5);
		levelText6 = (ImageView) activity.findViewById(R.id.LevelText6);
		levelText7 = (ImageView) activity.findViewById(R.id.LevelText7);
		levelText8 = (ImageView) activity.findViewById(R.id.LevelText8);
		levelText9 = (ImageView) activity.findViewById(R.id.LevelText9);
		levelText10 = (ImageView) activity.findViewById(R.id.LevelText10);
		levelText11 = (ImageView) activity.findViewById(R.id.LevelText11);
		levelText12 = (ImageView) activity.findViewById(R.id.LevelText12);
		
		activity.GetState();
		int level = activity.max_level;
		
		switch(level) {
		case 0:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			levelText1.setImageResource(R.drawable.level_start0);
			break;
		case 1:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			break;
		case 2:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			break;
		case 3:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			break;
		case 4:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			break;
		case 5:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			break;
		case 6:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			level_button7.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			levelText7.setImageResource(R.drawable.level_start6);
			break;
		case 7:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			level_button7.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button8.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			levelText7.setImageResource(R.drawable.level_start6);
			levelText8.setImageResource(R.drawable.level_start7);
			break;
		case 8:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			level_button7.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button8.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button9.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			levelText7.setImageResource(R.drawable.level_start6);
			levelText8.setImageResource(R.drawable.level_start7);
			levelText9.setImageResource(R.drawable.level_start8);
			break;
		case 9:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			level_button7.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button8.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button9.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button10.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			levelText7.setImageResource(R.drawable.level_start6);
			levelText8.setImageResource(R.drawable.level_start7);
			levelText9.setImageResource(R.drawable.level_start8);
			levelText10.setImageResource(R.drawable.level_start9);
			break;
		case 10:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			level_button7.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button8.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button9.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button10.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button11.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			levelText7.setImageResource(R.drawable.level_start6);
			levelText8.setImageResource(R.drawable.level_start7);
			levelText9.setImageResource(R.drawable.level_start8);
			levelText10.setImageResource(R.drawable.level_start9);
			levelText11.setImageResource(R.drawable.level_start10);
			break;
		case 11:
		case 12:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button2.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button3.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button4.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button5.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button6.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			level_button7.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			level_button8.setButtonImages(R.drawable.level2, 0, R.drawable.level2, 0);
			level_button9.setButtonImages(R.drawable.level3, 0, R.drawable.level3, 0);
			level_button10.setButtonImages(R.drawable.level4, 0, R.drawable.level4, 0);
			level_button11.setButtonImages(R.drawable.level5, 0, R.drawable.level5, 0);
			level_button12.setButtonImages(R.drawable.level6, 0, R.drawable.level6, 0);
			
			levelText1.setImageResource(R.drawable.level_start0);
			levelText2.setImageResource(R.drawable.level_start1);
			levelText3.setImageResource(R.drawable.level_start2);
			levelText4.setImageResource(R.drawable.level_start3);
			levelText5.setImageResource(R.drawable.level_start4);
			levelText6.setImageResource(R.drawable.level_start5);
			levelText7.setImageResource(R.drawable.level_start6);
			levelText8.setImageResource(R.drawable.level_start7);
			levelText9.setImageResource(R.drawable.level_start8);
			levelText10.setImageResource(R.drawable.level_start9);
			levelText11.setImageResource(R.drawable.level_start10);
			levelText12.setImageResource(R.drawable.level_start11);
			break;
		default:
			level_button1.setButtonImages(R.drawable.level1, 0, R.drawable.level1, 0);
			levelText1.setImageResource(R.drawable.level_start0);
			break;
		}
		
		backButton = (CustomImageButton) activity.findViewById(R.id.backButton);
		backButton.setButtonImages(R.drawable.back_button, 0, R.drawable.back_button_pressed, 0);
		
		if(activity.isCheckSound == 1 && activity.isCheckMusic == 1){
			level_sound = MediaPlayer.create(this.getContext(), R.raw.main);
			Utils.startPlay(activity.isCheckSound, level_sound, true);
		}
		
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
		backButton = null;
		
		activity.GetState();
		if(activity.isCheckMusic == 1){
			Utils.releasePlay(level_sound);
		}
	}

	@Override
	public void onClick(View v) {
		
		if(v == backButton)
			activity.selectPage(R.layout.mainmenu, false, null);
		
		if (v == level_button1){
			activity.level_number = 0;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button2) {
			if (activity.max_level < 0)
				return ;
			
			activity.level_number = 1;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button3) {
			if (activity.max_level < 1)
				return ;
			activity.level_number = 2;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button4) {
			if (activity.max_level < 2)
				return ;
			
			activity.level_number = 3;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button5) {
			if (activity.max_level < 3)
				return ;
			
			activity.level_number = 4;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button6) {
			if (activity.max_level < 4)
				return ;
			
			activity.level_number = 5;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button7) {
			if (activity.max_level < 5)
				return ;
			
			activity.level_number = 6;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button8) {
			if (activity.max_level < 6)
				return ;
			
			activity.level_number = 7;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button9) {
			if (activity.max_level < 7)
				return ;
			
			activity.level_number = 8;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button10) {
			if (activity.max_level < 8)
				return ;
			
			activity.level_number = 9;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button11) {
			if (activity.max_level < 9)
				return ;
			
			activity.level_number = 10;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} else if (v == level_button12) {
			if (activity.max_level < 10)
				return ;
			
			activity.level_number = 11;
			activity.selectPage(R.layout.colorfulzuma, false, null);
		} 
	}
}
