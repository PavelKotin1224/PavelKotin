package com.zumania;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.Vector;
import java.lang.Math;

import com.game.object.BitmapObject;
import com.game.object.Point2D;
import com.game.object.SpeedObject;
import com.zumania.ZUMAnia;
import com.zumania.GamePage;
import com.zumania.SurfaceViewRepaintListener;
import com.zumania.ZUMAnia.ColorfulZumaPageData;
import com.zumania.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnTouchListener;
import android.graphics.Matrix;
import android.media.MediaPlayer;

public class ZUMAniaGamePage extends GamePage implements OnTouchListener,
		SurfaceViewRepaintListener {

	ColorfulZumaPageData pageData;
	Context context;

	/** Other info **/
	private boolean levelFlag = false;
	private boolean settting_flag = false;
	private Point prevPoint = new Point();
	private double bulletRate;
	private static final float scaleMatrix = 1.0f;

	private boolean bBulletFlag = false;
	private boolean bHitFlag = false;
	public static boolean isShinerStatus = true;
	
	private boolean bBrokenFlag = false;
	private int GAME_REFRESH_RATE = 50;
	private int CHARACTER_TIME = 5000;
	private long characterStartTime = 0;

	private int hitNum = 0;
	private int hittmpNum = 0;
	private int hitColor = 0;

	int pointX = 0;
	int pointY = 0;
	double angle = 90;
	double tangle = 90;
	
	private int level = 1;
	// private int life = 0;
	public static int maxlevel = 0;
	private int xPressed;
	private int yPressed;
	private float xInsertBallPos = 0;
	private float yInsertBallPos = 0;
	private float xInsertStartPos = 0;
	private float yInsertStartPos = 0;
	private float xInsertEndPos = 0;
	private float yInsertEndPos = 0;

	private int insertLine = 0;
	private int bulletBallMoveCount = 0;
	private int firstBallCount = 20;

	private int load1[][] = { { 0, 50 }, { 592, 50 }, { 620, 82 },
			{ 582, 108 }, { 540, 120 }, { 120, 216 }, { 88, 250 },
			{ 124, 272 }, { 430, 272 }, { 472, 284 }, { 640, 370 } };

	private int load2[][] = { { 126, 0 }, { 74, 138 }, { 64, 196 },
			{ 80, 286 }, { 124, 328 }, { 180, 346 }, { 242, 334 },
			{ 288, 300 }, { 450, 94 }, { 476, 78 }, { 504, 74 }, { 534, 82 },
			{ 550, 104 }, { 470, 264 }, { 462, 296 }, { 480, 316 },
			{ 506, 332 }, { 660, 396 } };

	private int load3[][] = { { 0, 400 }, { 230, 70 }, { 254, 60 },
			{ 282, 50 }, { 310, 60 }, { 320, 88 }, { 340, 226 }, { 356, 252 },
			{ 386, 254 }, { 406, 232 }, { 444, 92 }, { 460, 66 }, { 486, 54 },
			{ 506, 74 }, { 700, 402 } };

	private int load4[][] = { { 332, 0 }, { 248, 34 }, { 104, 156 },
			{ 40, 246 }, { 48, 304 }, { 62, 332 }, { 84, 352 }, { 106, 366 },
			{ 138, 382 }, { 166, 380 }, { 194, 374 }, { 232, 364 },
			{ 250, 350 }, { 562, 124 }, { 602, 130 }, { 620, 154 },
			{ 616, 182 }, { 600, 204 }, { 490, 284 }, { 486, 314 },
			{ 500, 340 }, { 680, 412 } };

	private int load5[][] = { { 80, 394 }, { 108, 102 }, { 120, 74 },
			{ 150, 64 }, { 364, 72 }, { 388, 88 }, { 400, 114 }, { 424, 254 },
			{ 444, 276 }, { 472, 276 }, { 490, 216 }, { 574, 94 }, { 604, 94 },
			{ 630, 110 }, { 650, 134 }, { 650, 400 } };

	private int load6[][] = { { 6, 396 }, { 310, 54 }, { 330, 34 },
			{ 360, 24 }, { 390, 20 }, { 414, 30 }, { 448, 50 }, { 478, 100 },
			{ 480, 130 }, { 470, 160 }, { 440, 164 }, { 330, 164 },
			{ 304, 180 }, { 280, 196 }, { 266, 226 }, { 280, 252 },
			{ 514, 252 }, { 542, 270 }, { 564, 288 }, { 666, 400 } };

	private Canvas canvas = null;
	private int sameBallCount = 0;
	private int sameBallColor = 0;
	private boolean makeBulletFlag = true;
	private boolean isCompleteFlag = true;
	private boolean reloadFlag = false;

	private float xEndPipe = 0;
	private float yEndPipe = 0;
	
	/** background bitmap info **/
	Bitmap startLevelBitmap = null;
	Bitmap endPipeBitmap = null;
	Bitmap backgroundBitmap = null;
	Bitmap levelBitmap = null;
	Bitmap gamebg = null;
	Bitmap pauseBtnNormalBitmap = null;
	Bitmap pauseBtnPressedBitmap = null;
	Bitmap missionCompleteBitmap = null;
	Bitmap missionFailBitmap = null;
	Bitmap gameOverBitmap = null;

	public static boolean bMenuBtnPressed = false;
	public static boolean bBackFlag = false;

	private final static int BACKGROUND_BITMAP_WIDTH = ZUMAnia.gameWidth;
	private final static int BACKGROUND_BITMAP_HEIGHT = ZUMAnia.gameHeight;

	// sound info
	private MediaPlayer background_sound = null;
	private MediaPlayer bullet_sound = null;
	private MediaPlayer last_sound = null;
	private MediaPlayer break_sound = null;
	private MediaPlayer putbullet_sound = null;

	private final static String END_PIPE_NAME = "end_pipe";
	// broken image info
	Vector<BitmapObject> brokenObjects = new Vector<BitmapObject>();
	private final static String BROKEN_IMAGE_NAME = "bombbroken";

	private int BROKEN_BALL_WIDTH = 242;
	private int BROKEN_BALL_HEIGHT = 250;

	private final static String GUN_IMAGE_NAME = "gun";
	private final static String BALL_IMAGE_NAME = "ball";
	private final static String BULLET_IMAGE_NAME = "ball";
	private final static String SHINER_IMAGE_NAME = "shiner";
	private final static String BACK_BALL_NAME = "characterback";
	private final static String BOMB_BALL_NAME = "characterbomb";
	private final static String SLOW_BALL_NAME = "characterslow";
	private final static String START_LEVEL_NAME = "level_start";
	private final static String SCORE_NUMBER_NAME = "score_number";
	private final static String BREAK_BALL_IMAGE_NAME = "breakball";
	private final static String LEVEL_BACKGROUND_NAME = "game_background";
	private final static String GUN_PREV_BALL_IMAGE_NAME = "frogprevball";
	private final static String GUN_NEXT_BALL_IMAGE_NAME = "frognextball";

	/** gun object info **/
	BitmapObject gunObject = null;
	Vector<Bitmap> gunBitmaps = new Vector<Bitmap>();

	private final static int GUN_IMAGE_COUNT = 1;

	private float GUN_IMAGE_WIDTH = 0;
	private float GUN_IMAGE_HEIGHT = 0;
	private int GUN_IMAGE_VARIATION = 50;

	/** ball object info **/
	Vector<Bitmap> ballBitmaps = new Vector<Bitmap>();
	Vector<Bitmap> remainBallBitmaps = new Vector<Bitmap>();
	Vector<BitmapObject> ballObjects = new Vector<BitmapObject>();

	private final static int BALL_IMAGE_COUNT = 5;
	private int SAME_BALL_IMAGE_COUNT = 5;

	private float BALL_IMAGE_WIDTH = 0;
	private float BALL_IMAGE_HEIGHT = 0;

	private final static int BALL_WRIGGLE_SPEED = 2;
	private int BALL_SPEED = 10;
	private int BALL_COUNT = 80;
	private int BALL_TEMP_COUNT = 1;
	private int SHINER_COUNT = 0;

	// character ball info
	private final static int CHARACTER_DEFAULT = 0;
	private final static int CHARACTER_BOMB = 1;
	private final static int CHARACTER_SLOW = 2;
	private final static int CHARACTER_BACK = 3;
	private final static int LEVEL_FAILED_VIBRATION_TIME = 3000;

	private int characterNum = 0;

	private boolean characterFlag = false;

	private int characterKind = 0;

	private int xBombPos = 0;

	private int yBombPos = 0;

	// bomb character objects info
	Vector<Bitmap> bombBallBitmaps = new Vector<Bitmap>();

	private final static int BOMB_BALL_COUNT = 6;

	private float BOMB_BALL_WIDTH = 0;
	private float BOMB_BALL_HEIGHT = 0;

	// slow character objects info
	Vector<Bitmap> slowBallBitmaps = new Vector<Bitmap>();
	private final static int SLOW_BALL_COUNT = 6;

	private float SLOW_BALL_WIDTH = 0;
	private float SLOW_BALL_HEIGHT = 0;

	// back character objects info
	Vector<Bitmap> backBallBitmaps = new Vector<Bitmap>();

	private final static int BACK_BALL_COUNT = 6;

	private float BACK_BALL_WIDTH = 0;
	private float BACK_BALL_HEIGHT = 0;

	// break ball objects
	Vector<Bitmap> breakBallBitmaps = new Vector<Bitmap>();
	Vector<BitmapObject> breakBallObjects = new Vector<BitmapObject>();

	private final static int BREAK_BALL_IMAGE_COUNT = 6;

	private float BREAK_BALL_IMAGE_WIDTH = 0;
	private float BREAK_BALL_IMAGE_HEIGHT = 0;

	// shiner ball objects
	Vector<Bitmap> shinerBitmaps = new Vector<Bitmap>();
	Vector<BitmapObject> shinerObjects = new Vector<BitmapObject>();
	Vector<BitmapObject> tmpObjects = new Vector<BitmapObject>();

	private final static int SHINER_IMAGE_COUNT = 4;

	private float SHINER_IMAGE_WIDTH = 0;
	private float SHINER_IMAGE_HEIGHT = 0;

	private int SHINER_SPEED = 6;

	/** bullet object info **/

	Vector<Bitmap> bulletBitmaps = new Vector<Bitmap>();
	BitmapObject bulletObject = null;
	BitmapObject missionObject = null;
	BitmapObject gameOverObject = null;
	private final static int BULLET_IMAGE_COUNT = 5;

	private float BULLET_IMAGE_WIDTH = 0;
	private float BULLET_IMAGE_HEIGHT = 0;
	private int BULLET_SPEED = 50;

	public static boolean isIntentPause = false;

	/** line info **/
	Vector<lineObj> levelLine = new Vector<lineObj>();
	Vector<lineObj> levelLine1 = new Vector<lineObj>();
	Vector<lineObj> levelLine2 = new Vector<lineObj>();
	Vector<lineObj> levelLine3 = new Vector<lineObj>();
	Vector<lineObj> levelLine4 = new Vector<lineObj>();
	Vector<lineObj> levelLine5 = new Vector<lineObj>();
	Vector<lineObj> levelLine6 = new Vector<lineObj>();

	Vector<Vector<lineObj>> levelLines = new Vector<Vector<lineObj>>();

	// Game Status
	public static final int GAME_NONE = 0;
	public static final int GAME_START = 1;
	public static final int GAME_GO = 2;
	public static final int GAME_BEETLE_DIE = 3;
	public static final int GAME_MISSION_FAIL = 4;
	public static final int GAME_MISSION_COMPLETE = 5;
	public static final int GAME_OVER = 6;
	public static final int CONGRATULATIONS = 7;
	public static final int GAME_SETP_WIN = 8;
	public static final int GAME_LEVEL_WIN = 9;
	public static final int GAME_SHINER = 10;
	public static final int GAME_BALL_INSERT = 11;

	// score
	Vector<Bitmap> scoreNumberBitmaps = new Vector<Bitmap>();
	private final static int SCORE_NUMBER_BITMAP_WIDTH = 22;
	private final static int SCORE_NUMBER_ONE_BITMAP_WIDTH = 25;
	private final static int SCORE_NUMBER_BITMAP_HEIGHT = 21;
	private static final int SCORE_NUMBER_BITMAP_X_END = 135;
	private static final int SCORE_NUMBER_BITMAP_INTERVAL_WIDTH = 1;
	private final static int SCORE_NUMBER_BITMAP_FIRST_Y = 450;
	private final static int ONE_HIT_SCORE = 10;
	public static int maxScore = 0;
	public static int tempScore = 0;
	public static float SCORE_IMAGE_WIDTH = 0;
	public static float SCORE_IMAGE_HEIGHT = 0;
	
	public static PauseMenuDialog gPauseMenuDlg = null;

	int speedFlag = -1;

	// life Frog
	Bitmap lifeFrogBitmap = null;
	private float lifeFrog_BITMAP_WIDTH = 30;
	private float lifeFrog_BITMAP_HEIGHT = 30;

	// progress
	Bitmap progressBitmap = null;

	//target line
	Bitmap targetLineBitmap = null;
	
	// frog's ball
	Bitmap prevBallBitmap = null;
	Bitmap nextBallBitmap = null;
	int prevIndex = 0;
	int nextIndex = 0;
	int tempIndex = 0;
	Boolean bChangeGunBall = false;
	Bitmap prevImage;
	// shot ball point

	int initXPoint = 0;
	
	// int []remainColorArray = new Vector<int>();
	Vector<Integer> remainColorArray = new Vector<Integer>();

	private class lineObj {
		float x;
		float y;

		public lineObj(float xPos, float yPos) {
			x = xPos;
			y = yPos;
		}
	}

	public ZUMAniaGamePage(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		if (activity != null) {
			pageData = gameData.colorfulzumaPageData;
		}

		isIntentPause = false;
		addEventListener();
	}

	@Override
	protected void addEventListener() {
		super.addEventListener();

		// Add surface listener
		setSurfaceViewRedrawListener(this, GAME_REFRESH_RATE);

		// Add touch listener
		setOnTouchListener(this);

		// Add click listener
		setOnClickListener(this);

	}

	public void onPause() {
		isIntentPause = true;
		gameData.gameStatus = ZUMAnia.GAME_PAUSED;
		super.onPause();
	}

	/*
	 * All Image load
	 */
	private void loadBitmap() {
		Bitmap bitmap = null;
		String imageName = "";
		String fieldName = "";
		Field field;
		int fieldId = 0;

		// load background image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.game_background);

		backgroundBitmap = Bitmap.createScaledBitmap(bitmap,
				BACKGROUND_BITMAP_WIDTH, BACKGROUND_BITMAP_HEIGHT, true);
		
		bitmap.recycle();

		// load start level bitmap startLevelBitmap
		imageName = START_LEVEL_NAME;
		imageName += String.valueOf(activity.level_number);

		fieldId = 0;

		try {
			field = R.drawable.class.getDeclaredField(imageName);
			fieldId = field.getInt((Object) R.drawable.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

		startLevelBitmap = Bitmap.createScaledBitmap(bitmap, (int)(150 * activity.xScreenSize), (int)(40 * activity.yScreenSize), true);

		bitmap.recycle();
		
		// load end pipe image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.end_pipe);

		endPipeBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(),
				bitmap.getHeight(), true);

		bitmap.recycle();
		// level background
		imageName = LEVEL_BACKGROUND_NAME;
		imageName += String.valueOf(activity.level_number % 6);

		fieldId = 0;

		try {
			field = R.drawable.class.getDeclaredField(imageName);
			fieldId = field.getInt((Object) R.drawable.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

		levelBitmap = Bitmap.createScaledBitmap(bitmap,
				BACKGROUND_BITMAP_WIDTH, BACKGROUND_BITMAP_HEIGHT, true);
		bitmap.recycle();
		
		// load game bg
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.game_bg);

		gamebg = Bitmap.createScaledBitmap(bitmap, BACKGROUND_BITMAP_WIDTH,
				BACKGROUND_BITMAP_HEIGHT, true);
		bitmap.recycle();
		
		// load mission complete image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.complete_level);

		missionCompleteBitmap = Bitmap.createScaledBitmap(bitmap, (int)(400 * activity.xScreenSize), (int)(bitmap
				.getHeight() * activity.yScreenSize), true);

		bitmap.recycle();
		// load mission fail image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.fail_level);

		missionFailBitmap = Bitmap.createScaledBitmap(bitmap, (int)(400 * activity.xScreenSize), (int)(bitmap
				.getHeight() * activity.yScreenSize), true);
		bitmap.recycle();
		//load game over image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.game_over);

		gameOverBitmap = Bitmap.createScaledBitmap(bitmap, (int)(400 * activity.xScreenSize), (int)(bitmap
				.getHeight() * activity.yScreenSize), true);
		
		bitmap.recycle();
		// load menu button image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.pause_button);

		pauseBtnNormalBitmap = Bitmap.createScaledBitmap(bitmap, (int)(43 * activity.xScreenSize), (int)(32 * activity.yScreenSize), true);
		bitmap.recycle();
		
		//
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.pause_button_pressed);

		pauseBtnPressedBitmap = Bitmap.createScaledBitmap(bitmap, (int)(43 * activity.xScreenSize), (int)(32 * activity.yScreenSize), true);
		bitmap.recycle();
		
		// progress image
		bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.progress);
		
		progressBitmap = Bitmap.createScaledBitmap(bitmap, (int)(30 * activity.xScreenSize), (int)(10 * activity.yScreenSize), true);
		bitmap.recycle();
		
		// target line image
		bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.dot);
		
		targetLineBitmap = Bitmap.createScaledBitmap(bitmap, (int)(90 * activity.xScreenSize), (int)(800 * activity.yScreenSize), true);
		bitmap.recycle();
		
		// life Frog image
		bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.life);
		
		lifeFrog_BITMAP_WIDTH = 30 * activity.xScreenSize;
		lifeFrog_BITMAP_HEIGHT = 30 * activity.xScreenSize;
		
		lifeFrogBitmap = Bitmap.createScaledBitmap(bitmap, (int)lifeFrog_BITMAP_WIDTH, (int)lifeFrog_BITMAP_HEIGHT, true);
		bitmap.recycle();
		
		// load gun image
		for (int i = 1; i <= GUN_IMAGE_COUNT; i++) {

			imageName = GUN_IMAGE_NAME;
			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

			GUN_IMAGE_WIDTH = 90 * activity.xScreenSize;
			GUN_IMAGE_HEIGHT = 90 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) GUN_IMAGE_WIDTH, (int) GUN_IMAGE_HEIGHT, true);

			gunBitmaps.add(tmp);
		}

		// load ball image
		for (int i = 1; i <= BALL_IMAGE_COUNT; i++) {

			imageName = BALL_IMAGE_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

			BALL_IMAGE_WIDTH = 30 * activity.xScreenSize;
			BALL_IMAGE_HEIGHT = 30 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) BALL_IMAGE_WIDTH, (int) BALL_IMAGE_HEIGHT, true);
			ballBitmaps.add(tmp);
		}

		for (int i = 0; i <= 9; i++) {

			fieldName = "";
			fieldName = SCORE_NUMBER_NAME;
			fieldName += String.valueOf(i);

			fieldId = 0;
			try {
				field = R.drawable.class.getDeclaredField(fieldName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(),	fieldId);
			
			SCORE_IMAGE_WIDTH = 22 * activity.xScreenSize;
			SCORE_IMAGE_HEIGHT = 27 * activity.yScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) SCORE_IMAGE_WIDTH, (int) SCORE_IMAGE_HEIGHT, true);
			
			scoreNumberBitmaps.add(tmp);
		}

		// load break ball image
		for (int i = 1; i <= BREAK_BALL_IMAGE_COUNT; i++) {

			imageName = BREAK_BALL_IMAGE_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(),
					fieldId);

			BREAK_BALL_IMAGE_WIDTH = 30 * activity.xScreenSize;
			BREAK_BALL_IMAGE_HEIGHT = 30 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) BREAK_BALL_IMAGE_WIDTH, (int) BREAK_BALL_IMAGE_HEIGHT, true);
			breakBallBitmaps.add(tmp);
		}

		// load shiner ball image
		for (int i = 1; i <= SHINER_IMAGE_COUNT; i++) {

			imageName = SHINER_IMAGE_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(),
					fieldId);

			SHINER_IMAGE_WIDTH = 35 * activity.xScreenSize;
			SHINER_IMAGE_HEIGHT = 35 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) SHINER_IMAGE_WIDTH, (int) SHINER_IMAGE_HEIGHT, true);

			shinerBitmaps.add(tmp);
		}

		// load bullet image
		for (int i = 1; i <= BULLET_IMAGE_COUNT; i++) {

			imageName = BULLET_IMAGE_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;
			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

			BULLET_IMAGE_WIDTH = 30 * activity.xScreenSize;
			BULLET_IMAGE_HEIGHT = 30 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) BULLET_IMAGE_WIDTH, (int) BULLET_IMAGE_HEIGHT, true);

			bulletBitmaps.add(tmp);
		}

		// load bomb character ball image
		for (int i = 1; i <= BOMB_BALL_COUNT; i++) {

			imageName = BOMB_BALL_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

			BOMB_BALL_WIDTH = 30 * activity.xScreenSize;
			BOMB_BALL_HEIGHT = 30 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) BOMB_BALL_WIDTH, (int) BOMB_BALL_HEIGHT, true);

			bombBallBitmaps.add(tmp);
		}

		// load slow character ball image
		for (int i = 1; i <= SLOW_BALL_COUNT; i++) {

			imageName = SLOW_BALL_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

			SLOW_BALL_WIDTH = 30 * activity.xScreenSize;
			SLOW_BALL_HEIGHT = 30 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) SLOW_BALL_WIDTH, (int) SLOW_BALL_HEIGHT, true);

			slowBallBitmaps.add(tmp);
		}

		// load back character ball image
		for (int i = 1; i <= BACK_BALL_COUNT; i++) {

			imageName = BACK_BALL_NAME;
			imageName += String.valueOf(i);

			fieldId = 0;

			try {
				field = R.drawable.class.getDeclaredField(imageName);
				fieldId = field.getInt((Object) R.drawable.class);
			} catch (Exception e) {
				e.printStackTrace();
			}

			bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

			BACK_BALL_WIDTH = 30 * activity.xScreenSize;
			BACK_BALL_HEIGHT = 30 * activity.xScreenSize;

			Bitmap tmp = Bitmap.createScaledBitmap(bitmap, (int) BACK_BALL_WIDTH, (int) BACK_BALL_HEIGHT, true);

			backBallBitmaps.add(tmp);
		}

		for (int i = 0; i < load1.length; i++) {
			lineObj level1 = new lineObj(load1[i][0] * activity.xScreenSize, load1[i][1] * activity.yScreenSize);
			levelLine1.add(level1);
		}
		
		levelLines.add(levelLine1);

		for (int i = 0; i < load2.length; i++) {
			lineObj level2 = new lineObj(load2[i][0] * activity.xScreenSize, load2[i][1] * activity.yScreenSize);
			levelLine2.add(level2);
		}
		levelLines.add(levelLine2);

		for (int i = 0; i < load3.length; i++) {
			lineObj level3 = new lineObj(load3[i][0] * activity.xScreenSize, load3[i][1] * activity.yScreenSize);
			levelLine3.add(level3);
		}
		
		levelLines.add(levelLine3);

		for (int i = 0; i < load4.length; i++) {
			lineObj level4 = new lineObj(load4[i][0] * activity.xScreenSize, load4[i][1] * activity.yScreenSize);
			levelLine4.add(level4);
		}
		levelLines.add(levelLine4);

		for (int i = 0; i < load5.length; i++) {
			lineObj level5 = new lineObj(load5[i][0] * activity.xScreenSize, load5[i][1] * activity.yScreenSize);
			levelLine5.add(level5);
		}
		levelLines.add(levelLine5);

		for (int i = 0; i < load6.length; i++) {
			lineObj level6 = new lineObj(load6[i][0] * activity.xScreenSize, load6[i][1] * activity.yScreenSize);
			levelLine6.add(level6);
		}
		levelLines.add(levelLine6);
		
		BULLET_SPEED = (int)(50 * activity.xScreenSize);
	}

	private Bitmap rotateImage(Bitmap srcBitmap, float scaleRate, int srcX, int srcY, int srcW, int srcH, int degree) {
		Matrix matrix = new Matrix();
		matrix.postScale(scaleRate, scaleMatrix);
		matrix.postRotate(degree);

		return Bitmap.createBitmap(srcBitmap, srcX, srcY, srcW, srcH, matrix, true);
	}

	private void loadSound() {
		
		if (!reloadFlag) {
			if (background_sound == null)
				background_sound = MediaPlayer.create(this.context, R.raw.gamepage);
			
			if (bullet_sound == null)
				bullet_sound = MediaPlayer.create(this.context, R.raw.bullet);
			
			if (last_sound == null)
				last_sound = MediaPlayer.create(this.context, R.raw.last);
			
			if (break_sound == null)
				break_sound = MediaPlayer.create(this.context, R.raw.ballbreak);
			
			if (putbullet_sound == null)
				putbullet_sound = MediaPlayer.create(this.context, R.raw.putbullet);
			reloadFlag = true;
		}
		if(isIntentPause)
		{
			if (background_sound == null)
				background_sound = MediaPlayer.create(this.context, R.raw.gamepage);
			
			if (bullet_sound == null)
				bullet_sound = MediaPlayer.create(this.context, R.raw.bullet);
			
			if (last_sound == null)
				last_sound = MediaPlayer.create(this.context, R.raw.last);
			
			if (break_sound == null)
				break_sound = MediaPlayer.create(this.context, R.raw.ballbreak);
			
			if (putbullet_sound == null)
				putbullet_sound = MediaPlayer.create(this.context, R.raw.putbullet);
			reloadFlag = true;
		}
	}

	//draw gum bitmap
	private void draw_arrow(Canvas g) {
		Bitmap image;

		image = rotateImage(gunBitmaps.get(0), 1, 0, 0, (int)(90 * activity.xScreenSize), (int)(90 * activity.xScreenSize), (int) angle - 90);
		pointX = ZUMAnia.gameWidth / 2;
		pointY = ZUMAnia.gameHeight;

		if (bChangeGunBall == false) {
			prevImage = image;
			g.drawBitmap(image, pointX - image.getWidth() / 2, (int)(430 * activity.yScreenSize) - image.getHeight() / 2, null);
		} else
			g.drawBitmap(prevImage, pointX - prevImage.getWidth() / 2, (int)(430 * activity.yScreenSize) - prevImage.getHeight() / 2, null);
	}
	
	
	//draw life frog
	private void draw_lifeFrog(Canvas c) {
		Rect dest = null;
		int initX = (int)(470 * activity.xScreenSize);
		int interval = (int)(60 * activity.xScreenSize);

		for (int i = pageData.life - 1; i >= 0; i--) {
			dest = new Rect(initX + interval * i, (int)(445 * activity.yScreenSize), initX + interval * i + (int)lifeFrog_BITMAP_WIDTH, (int)(445 * activity.yScreenSize) + (int)lifeFrog_BITMAP_HEIGHT);
			c.drawBitmap(lifeFrogBitmap, null, dest, null);
		}
	}

	@Override
	public void unloadPage() {
		super.unloadPage();
		unLoadBitmap();
		removeAllRefObjects();
		unloadSound();
		isLoaded = false;
	}

	@Override
	public boolean onKeyPreIme(int keyCode, KeyEvent msg) {
		if (msg.getAction() == KeyEvent.ACTION_DOWN)
			if ((keyCode == KeyEvent.KEYCODE_HOME) || (keyCode == KeyEvent.KEYCODE_MENU) || (keyCode == KeyEvent.KEYCODE_BACK)) {
				return super.onKeyDown(keyCode, msg);
			}
		return false;
	}

	private void unloadSound() {
		Utils.releasePlay(background_sound);
		Utils.releasePlay(bullet_sound);
		Utils.releasePlay(last_sound);
		Utils.releasePlay(break_sound);
		Utils.releasePlay(putbullet_sound);
	}

	public void onResume() {
		super.onResume();

		loadSound();

		activity.GetState();
		if (activity.isCheckSound == 1 && activity.isCheckMusic == 1) {
			Utils.startPlay(activity.isCheckSound, background_sound, true);
		}
		else
			Utils.stopPlay(background_sound);

		if (isIntentPause) {
			ZUMAniaGamePage.isIntentPause = false;

			settting_flag = true;
			activity.GetState();
			if (activity.isCheckSound == 1 && activity.isCheckMusic == 1)
				Utils.startPlay(activity.isCheckSound, background_sound, true);
			else
				Utils.pausePlay(background_sound);
		} else {
			// gameData.gameStatus = ColorfulZuma.GAME_START;
		}
	}

	private void unLoadBitmap() {
		// unload background image
		if (backgroundBitmap != null) {
			backgroundBitmap.recycle();
			backgroundBitmap = null;
		}
		
		if (startLevelBitmap != null) {
			startLevelBitmap.recycle();
			startLevelBitmap = null;
		}
		
		if (endPipeBitmap != null) {
			endPipeBitmap.recycle();
			endPipeBitmap = null;
		}
		
		if (levelBitmap != null) {
			levelBitmap.recycle();
			levelBitmap = null;
		}

		if (gamebg != null) {
			gamebg.recycle();
			gamebg = null;
		}

		if (missionCompleteBitmap != null) {
			missionCompleteBitmap.recycle();
			missionCompleteBitmap = null;
		}
		
		if (missionFailBitmap != null) {
			missionFailBitmap.recycle();
			missionFailBitmap = null;
		}
		
		if (gameOverBitmap != null) {
			gameOverBitmap.recycle();
			gameOverBitmap = null;
		}

		if (pauseBtnNormalBitmap != null) {
			pauseBtnNormalBitmap.recycle();
			pauseBtnNormalBitmap = null;
		}

		if (pauseBtnPressedBitmap != null) {
			pauseBtnPressedBitmap.recycle();
			pauseBtnPressedBitmap = null;
		}
		
		if (progressBitmap != null) {
			progressBitmap.recycle();
			progressBitmap = null;
		}
		
		if (lifeFrogBitmap != null) {
			lifeFrogBitmap.recycle();
			lifeFrogBitmap = null;
		}

		//unload
		for (int i = 0; i < gunBitmaps.size(); i++) {
			if(gunBitmaps.get(i) != null) {
				gunBitmaps.get(i).recycle();				
			}
		}
		gunBitmaps.clear();

		for (int i = 0; i < ballBitmaps.size(); i++) {
			if(ballBitmaps.get(i) != null) {
				ballBitmaps.get(i).recycle();				
			}
		}
		ballBitmaps.clear();

		for (int i = 0; i < scoreNumberBitmaps.size(); i++) {
			if(scoreNumberBitmaps.get(i) != null) {
				scoreNumberBitmaps.get(i).recycle();				
			}
		}
		scoreNumberBitmaps.clear();
		
		for (int i = 0; i < breakBallBitmaps.size(); i++) {
			if(breakBallBitmaps.get(i) != null) {
				breakBallBitmaps.get(i).recycle();				
			}
		}
		breakBallBitmaps.clear();

		for (int i = 0; i < shinerBitmaps.size(); i++) {
			if(shinerBitmaps.get(i) != null) {
				shinerBitmaps.get(i).recycle();				
			}
		}
		shinerBitmaps.clear();
		
		for (int i = 0; i < bulletBitmaps.size(); i++) {
			if(bulletBitmaps.get(i) != null) {
				bulletBitmaps.get(i).recycle();				
			}
		}
		bulletBitmaps.clear();
		
		for (int i = 0; i < bombBallBitmaps.size(); i++) {
			if(bombBallBitmaps.get(i) != null) {
				bombBallBitmaps.get(i).recycle();				
			}
		}
		bombBallBitmaps.clear();

		for (int i = 0; i < slowBallBitmaps.size(); i++) {
			if(slowBallBitmaps.get(i) != null) {
				slowBallBitmaps.get(i).recycle();				
			}
		}
		slowBallBitmaps.clear();
		
		for (int i = 0; i < backBallBitmaps.size(); i++) {
			if(backBallBitmaps.get(i) != null) {
				backBallBitmaps.get(i).recycle();				
			}
		}
		backBallBitmaps.clear();
		
		for (int i = 0; i < shinerObjects.size(); i++) {
			if(shinerObjects.get(i) != null) {
				shinerObjects.get(i).removeObject();
				shinerObjects.remove(i);
			}
		}
		shinerObjects.clear();
		
		for (int i = 0; i < levelLine1.size(); i++) {
			if(levelLine1.get(i) != null) {
				levelLine1.remove(i);
			}
		}
		levelLine1.clear();
		
		for (int i = 0; i < levelLine2.size(); i++) {
			if(levelLine2.get(i) != null) {
				levelLine2.remove(i);
			}
		}
		levelLine2.clear();
		
		for (int i = 0; i < levelLine3.size(); i++) {
			if(levelLine3.get(i) != null) {
				levelLine3.remove(i);
			}
		}
		levelLine3.clear();
		
		for (int i = 0; i < levelLine4.size(); i++) {
			if(levelLine4.get(i) != null) {
				levelLine4.remove(i);
			}
		}
		levelLine4.clear();

		for (int i = 0; i < levelLine5.size(); i++) {
			if(levelLine5.get(i) != null) {
				levelLine5.remove(i);
			}
		}
		levelLine5.clear();

		for (int i = 0; i < levelLine6.size(); i++) {
			if(levelLine6.get(i) != null) {
				levelLine6.remove(i);
			}
		}
		levelLine6.clear();
		
		for (int i = 0; i < levelLines.size(); i++) {
			if(levelLines.get(i) != null) {
				levelLines.remove(i);
			}
		}
		levelLines.clear();
	}

	//initialize game data
	private void initGameData() {
		levelFlag = false;
		
		if (activity.level_number > 5) {
			levelFlag = true;
		}
		
		//get current load position
		levelLine = levelLines.get(activity.level_number % 6);
		lineObj lastLine = levelLine.lastElement();
		
		xEndPipe = lastLine.x - 30;
		yEndPipe = lastLine.y - 30;

		SHINER_COUNT = 0;
		BALL_TEMP_COUNT = 0;
		SAME_BALL_IMAGE_COUNT = 5;

		gameData.gameStatus = ZUMAnia.GAME_NONE;
		moveStatus(GAME_SHINER);
		init_status();
		
		bBulletFlag = false;
		bHitFlag = false;
		sameBallCount = 0;
		sameBallColor = 0;
		makeBulletFlag = true;
		isCompleteFlag = true;
		bChangeGunBall = false; 
		isShinerStatus = true;
		angle = 90;
		tangle = 90;
		characterNum = 0;
	}
	
	private void makeShinerObjs() {
		levelLine = levelLines.get(activity.level_number % 6);

		lineObj lineObj = levelLine.get(0);
		lineObj nextLineObj = levelLine.get(1);

		if (SHINER_COUNT < 4) {
			Point2D init_point = new Point2D(lineObj.x, lineObj.y);
			SpeedObject init_speed = new SpeedObject(0, 5, 0, 0, 1);

			BitmapObject object = new BitmapObject(activity, SHINER_IMAGE_NAME, init_point, (int) SHINER_IMAGE_WIDTH, (int) SHINER_IMAGE_HEIGHT, shinerBitmaps.get(SHINER_COUNT));

			object.setXStartPos(lineObj.x);
			object.setXEndPos(nextLineObj.x);
			object.setYStartPos(lineObj.y);
			object.setYEndPos(nextLineObj.y);

			object.setLineIndex(1);
			object.getPosition().setPos(init_point);
			shinerObjects.add(object);

			SHINER_COUNT++;
		}
	}

	private void moveShinerObjs(Canvas c) {
		int lineIndex = 0;
		int moveWidth = 0;
		int moveYWidth = 0;
		double rate = 0;

		c.drawBitmap(startLevelBitmap, BACKGROUND_BITMAP_WIDTH / 2	- startLevelBitmap.getWidth() / 2, BACKGROUND_BITMAP_HEIGHT / 2 - startLevelBitmap.getHeight() / 2, null);

		int i = 0;
		for (i = 0; i < 10; i++) {
			for (int j = shinerObjects.size() - 1; j >= 0; j--) {
				moveWidth = SHINER_SPEED;
				moveYWidth = SHINER_SPEED;

				BitmapObject shinerObj = shinerObjects.get(j);

				if (shinerObj != null) {
					Point2D shinerPoint = shinerObj.getPosition();
					rate = Math.atan2(shinerObj.getYEndPos() - shinerObj.getYStartPos(), shinerObj.getXEndPos() - shinerObj.getXStartPos());

					if ((shinerPoint.x + Math.cos(rate) * SHINER_SPEED > shinerObj.getXEndPos() && shinerObj.getXEndPos() > shinerObj.getXStartPos())
							|| (shinerPoint.x - Math.cos(rate) * SHINER_SPEED < shinerObj.getXEndPos() && shinerObj.getXStartPos() > shinerObj.getXEndPos())
							|| (shinerPoint.y + Math.sin(rate) * SHINER_SPEED) > shinerObj.getYEndPos()
							&& shinerObj.getYEndPos() > shinerObj.getYStartPos()
							|| (shinerPoint.y - Math.sin(rate) * SHINER_SPEED) < shinerObj.getYEndPos()&& shinerObj.getYStartPos() > shinerObj.getYEndPos()) {

						lineIndex = shinerObj.getLineIndex();

						if (lineIndex + 1 == levelLine.size()) {
							if (shinerObjects.size() == 1) {
								shinerObjects.get(0).removeObject();
								shinerObjects.remove(0);
								gameData.gameStatus = ZUMAnia.GAME_START;

								moveStatus(GAME_START);
								init_status();
								removeAllRefObjects();
								isShinerStatus = false;
								return;
							}

							shinerObjects.get(j).removeObject();
							shinerObjects.remove(j);
							continue;
						}

						shinerPoint.x = shinerObj.getXEndPos();
						shinerPoint.y = shinerObj.getYEndPos();

						lineObj obj = levelLine.get(shinerObj.getLineIndex() + 1);

						shinerObj.setXStartPos(shinerObj.getXEndPos());
						shinerObj.setXEndPos(obj.x);
						shinerObj.setYStartPos(shinerObj.getYEndPos());
						shinerObj.setYEndPos(obj.y);
						shinerObj.setLineIndex(shinerObj.getLineIndex() + 1);
					} else {
						shinerPoint.x = (float) (shinerPoint.x + Math.cos(rate)* moveWidth);
						shinerPoint.y = (float) (shinerPoint.y + Math.sin(rate)* moveYWidth);
					}

					shinerObj.getPosition().setPos(shinerPoint);
				}
			}

			double makeRate = Math.atan2(levelLine.get(1).y - levelLine.get(0).y, levelLine.get(1).x - levelLine.get(0).x);
			if ((Math.abs(shinerObjects.get(shinerObjects.size() - 1).getPosition().x - levelLine.get(0).x) > Math.abs(BALL_IMAGE_WIDTH * Math.cos(rate))) ||
					(Math.abs(shinerObjects.get(shinerObjects.size() - 1).getPosition().y - levelLine.get(0).y) > Math.abs(BALL_IMAGE_WIDTH * Math.sin(rate)))) {
				makeShinerObjs();
			}
		}

		if (shinerObjects.size() == 0)
			makeShinerObjs();

		removeAllRefObjects();
		
		for (int j = 0; j < shinerObjects.size(); j++)
			addRefObject(shinerObjects.get(j));
	}
	
	private void deleteBreakBallObjs() {
		breakBallObjects.removeAllElements();
	}

	private void makeLevelStartObj() {
		// levelBitmap
	}

	private void makeBombBrokenBall() {
		Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
				R.drawable.bombbroken);
		
		Bitmap bombBroken = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
		
		Point2D init_point = new Point2D(0, 0);
		SpeedObject init_speed = new SpeedObject(0, 5, 0, 0, 1);
		
		BitmapObject object = new BitmapObject(activity, BROKEN_IMAGE_NAME,
				init_point, BROKEN_BALL_WIDTH, BROKEN_BALL_HEIGHT,
				bombBroken);
		
		object.getPosition().setPos(init_point);
		brokenObjects.add(object);
	}
	
	private void makeBallObjs() {
		if (BALL_TEMP_COUNT < BALL_COUNT) {
			lineObj levelLineObj = levelLine.get(0);
			lineObj nextLineObj = levelLine.get(1);

			int nRand = (int) (Math.random() * 10000) % BALL_IMAGE_COUNT;
			
			if (levelFlag)
				SAME_BALL_IMAGE_COUNT = 3;
			
			int nSameBallCount = (int) (Math.random() * 10000) % SAME_BALL_IMAGE_COUNT;

			if (sameBallCount == 0) {
				sameBallCount = nSameBallCount;
				sameBallColor = nRand;
			} else {
				sameBallCount--;
				nRand = sameBallColor;
			}

			Point2D init_point = new Point2D(levelLineObj.x, levelLineObj.y);
			SpeedObject init_speed = new SpeedObject(0, 5, 0, 0, 3);

			Bitmap bitmap = null;
			boolean bCharacterFlag = false;
			int kind = 0;

			if (BALL_TEMP_COUNT ==  36 || BALL_TEMP_COUNT == 60) { // back character
				bCharacterFlag = true;
				bitmap = backBallBitmaps.get(nRand);
				kind = CHARACTER_BACK;
			} else if (BALL_TEMP_COUNT == 25 || BALL_TEMP_COUNT == 70) { // slow character
				bCharacterFlag = true;
				bitmap = slowBallBitmaps.get(nRand);
				kind = CHARACTER_SLOW;
			} else if (characterNum == 10 || BALL_TEMP_COUNT == 48) { // bomb
				bCharacterFlag = true;
				bitmap = bombBallBitmaps.get(nRand);
				kind = CHARACTER_BOMB;
			} else {
				bitmap = ballBitmaps.get(nRand);
			}

			BitmapObject object = new BitmapObject(activity, BALL_IMAGE_NAME, init_point, BALL_IMAGE_WIDTH, BALL_IMAGE_HEIGHT, bitmap);

			object.setXStartPos(levelLineObj.x);
			object.setXEndPos(nextLineObj.x);
			object.setYStartPos(levelLineObj.y);
			object.setYEndPos(nextLineObj.y);
			object.setLineIndex(1);

			object.setColorVal(nRand);
			object.characterFlag = bCharacterFlag;
			object.characterKind = kind;
			object.getPosition().setPos(init_point);

			ballObjects.add(object);
			BALL_TEMP_COUNT++;
			characterNum++;
		}
	}

	private void isMissionComplete() {
		if (BALL_TEMP_COUNT == BALL_COUNT && ballObjects.size() == 0) {
			if (isCompleteFlag) {
				moveStatus(GAME_MISSION_COMPLETE);
				activity.level_number++;
				
				pageData.score = tempScore;
				activity.SaveState();
				
				characterStartTime = System.currentTimeMillis();
			} else {
				if (pageData.life == 1){
					moveStatus(GAME_OVER);
				}
				else {
					moveStatus(GAME_MISSION_FAIL);
					pageData.score = tempScore;
				}
				
				activity.GetState();
				Utils.startVibration(activity.isCheckVibration, activity.vibrator, LEVEL_FAILED_VIBRATION_TIME);
				characterStartTime = System.currentTimeMillis();
				pageData.life--;
			}
		}
	}

	private void moveBall() {
		if (pageData.status != GAME_GO	|| (characterFlag && characterKind == CHARACTER_BACK))
			return;

		int speed = 1;
		double xWidth = 0;
		double yWidth = 0;
		boolean bFlag = false;
		int tmpHitNum = 0;

		for (int i = 0; i < BALL_SPEED; i++) {
			for (int ballCount = ballObjects.size() - 1; ballCount >= 0; ballCount--) {
				bFlag = false;
				BitmapObject ballObj = ballObjects.get(ballCount);
				Point2D ballPoint = ballObj.getPosition();

				double rate = Math.atan2(Math.abs(ballObj.getYEndPos() - ballObj.getYStartPos()), Math.abs(ballObj.getXEndPos() - ballObj.getXStartPos()));
				
				if (ballCount > 0) {
					BitmapObject nextObj = ballObjects.get(ballCount - 1);
					Point2D nextPoint = nextObj.getPosition();

					xWidth = ballPoint.x - nextPoint.x;
					yWidth = ballPoint.y - nextPoint.y;
					double ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

					int nWidth = 0;
					
					if (activity.xScreenSize == 1) {
						nWidth = 30;
					} else if (activity.xScreenSize == 0.6) {
						nWidth = 18;
					} else if (activity.xScreenSize == 0.4) {
						nWidth = 12;
					} else {
						nWidth = (int)(activity.xScreenSize * 32);
					}
					
					if ((int)ballWidth > (int)nWidth) {
						tmpHitNum = ballCount;
						bFlag = true;
					} else {
						if (tmpHitNum == ballCount) {
							if (ballObj.getColorVal() == nextObj.getColorVal()) {
								bHitFlag = true;
								hitNum = tmpHitNum;
								hitColor = ballObj.getColorVal();
							}
						}
					}
				}

				if ((ballPoint.x + Math.cos(rate) * speed > ballObj.getXEndPos() && ballObj.getXEndPos() > ballObj.getXStartPos())
						|| (ballPoint.x - Math.cos(rate) * speed < ballObj.getXEndPos() && ballObj.getXStartPos() > ballObj.getXEndPos())
						|| (ballPoint.y + Math.sin(rate) * speed > ballObj.getYEndPos() && ballObj.getYEndPos() > ballObj.getYStartPos())
						|| (ballPoint.y - Math.sin(rate) * speed < ballObj.getYEndPos() && ballObj.getYEndPos() < ballObj.getYStartPos())) {
					int lineIndex = ballObj.getLineIndex();
					if (lineIndex < levelLine.size()) {
						if (lineIndex + 1 == levelLine.size()) {
							ballObjects.get(ballCount).removeObject();
							ballObjects.remove(ballCount);
							BALL_SPEED = 15;
							bBulletFlag = false;
							BALL_TEMP_COUNT = BALL_COUNT;
							makeBulletFlag = false;
							isCompleteFlag = false;
						} else {
							ballPoint.x = ballObj.getXEndPos();
							ballPoint.y = ballObj.getYEndPos();

							lineObj line = levelLine.get(ballObj.getLineIndex() + 1);
							ballObj.setXStartPos(ballObj.getXEndPos());
							ballObj.setXEndPos(line.x);
							ballObj.setYStartPos(ballObj.getYEndPos());
							ballObj.setYEndPos(line.y);
							ballObj.setLineIndex(ballObj.getLineIndex() + 1);
							ballObj.getPosition().setPos(ballPoint);
						}
					}
				} else {
					if (ballObj.getXStartPos() <= ballObj.getXEndPos())
						ballPoint.x = (float) (ballPoint.x + Math.cos(rate) * speed);
					else
						ballPoint.x = (float) (ballPoint.x - Math.cos(rate) * speed);

					if (ballObj.getYStartPos() <= ballObj.getYEndPos())
						ballPoint.y = (float) (ballPoint.y + Math.sin(rate)* speed);
					else 
						ballPoint.y = (float) (ballPoint.y - Math.sin(rate)* speed);
					
					ballObj.getPosition().setPos(ballPoint);
				}

				if (bFlag)
					break;
			}

			if (ballObjects.size() > 0) {
				double rate = Math.atan2(levelLine.get(1).y - levelLine.get(0).y, levelLine.get(1).x - levelLine.get(0).x);
				
				BitmapObject obj = ballObjects.get(ballObjects.size() - 1);
				Point2D objPos = obj.getPosition();
				if ((ballObjects.get(ballObjects.size() - 1).getPosition().x >= 0 && ballObjects.get(ballObjects.size() - 1).getPosition().y >= 0) 
						&& ( 
							(obj.getXEndPos() >= obj.getXStartPos() && objPos.x >= obj.getXStartPos() && (int)Math.abs(objPos.x - levelLine.get(0).x) >= (int)Math.abs(BALL_IMAGE_WIDTH * Math.cos(rate))
									&& obj.getYStartPos() >= obj.getYEndPos() && objPos.y <= obj.getYStartPos() && (int)Math.abs(objPos.y - levelLine.get(0).y) >= (int)(Math.abs(BALL_IMAGE_WIDTH * Math.sin(rate))))
							|| (obj.getXEndPos() <= obj.getXStartPos() && objPos.x <= obj.getXStartPos() && (int)Math.abs(objPos.x - levelLine.get(0).x) >= (int)Math.abs(BALL_IMAGE_WIDTH * Math.cos(rate))
									&& obj.getYStartPos() <= obj.getYEndPos() && objPos.y >= obj.getYStartPos() &&(int)Math.abs(objPos.y - levelLine.get(0).y) <= (int)(Math.abs(BALL_IMAGE_WIDTH * Math.sin(rate))))
						)) {
					makeBallObjs();
				}
			}
		}

		if (ballObjects.size() <= 0)
			makeBallObjs();
	}

	private void moveBackBall() {
		if (pageData.status != GAME_GO || (characterFlag && characterKind == CHARACTER_BACK))
			return;

		double xWidth = 0;
		double yWidth = 0;
		double ballWidth = 0;
		int speed = 1;
		boolean flag = false;
		for (int i = 0; i < ballObjects.size() - 1; i++) {
			BitmapObject ballObj = ballObjects.get(i);
			Point2D ballPos = ballObj.getPosition();

			if (i < ballObjects.size() - 1) {
				BitmapObject beObj = ballObjects.get(i + 1);
				Point2D bePos = beObj.getPosition();

				xWidth = ballPos.x - bePos.x;
				yWidth = ballPos.y - bePos.y;

				ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

				if ((int)ballWidth > (int)(30 * activity.yScreenSize)) {
					if (ballObj.getColorVal() == beObj.getColorVal()) {
						for (int moveCount = 0; moveCount < 6; moveCount++) {
							boolean bFlag = false;
							flag = false;
							
							for (int pos = i; pos >= 0; pos--) {
								BitmapObject backObj = ballObjects.get(pos);
								Point2D backPos = backObj.getPosition();

								if (pos > 0 && bFlag) {
									BitmapObject beBackObj = ballObjects.get(pos - 1);
									Point2D beBackPos = beBackObj.getPosition();

									xWidth = backPos.x - beBackPos.x;
									yWidth = backPos.y - beBackPos.y;

									ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

									if ((int)ballWidth > (int)(32 * activity.xScreenSize))
										flag = true;
								}

								bFlag = true;
								double nTempRate = Math.atan2(Math.abs(backObj.getYEndPos() - backObj.getYStartPos()), Math.abs(backObj.getXEndPos()
												- backObj.getXStartPos()));
								
								if ((backPos.x + Math.cos(nTempRate) * 1 > backObj.getXStartPos() && backObj.getXStartPos() > backObj.getXEndPos())
										|| (backPos.x - Math.cos(nTempRate) * 1 < backObj.getXStartPos() && backObj.getXStartPos() < backObj.getXEndPos())
										|| (backPos.y - Math.sin(nTempRate) * 1 < backObj.getYStartPos() && backObj.getYEndPos() > backObj.getYStartPos())
										|| (backPos.y + Math.sin(nTempRate) * 1 > backObj.getYStartPos() && backObj.getYEndPos() < backObj.getYStartPos())) {

									int lineIndex = backObj.getLineIndex();
									if (lineIndex == 1) {
										backPos.x = (float) (backObj.getXStartPos() - Math.cos(nTempRate) * 1);
										backPos.y = (float) (backObj.getYStartPos() - Math.sin(nTempRate) * 1);
										backObj.setXEndPos(backObj.getXStartPos());
										backObj.setYEndPos(backObj.getYStartPos());
										backObj.setXStartPos((float) (-1000 * Math.abs(Math.cos(nTempRate))));
										backObj.setYStartPos((float) (-1000 * Math.abs(Math.sin(nTempRate))));
									} else if (lineIndex > 1) {
										backPos.x = backObj.getXStartPos();
										backPos.y = backObj.getYStartPos();
										
										lineObj obj = levelLine.get(backObj.getLineIndex() - 2);
										backObj.setXStartPos(obj.x);
										backObj.setXEndPos(backPos.x);
										backObj.setYStartPos(obj.y);
										backObj.setYEndPos(backPos.y);
									} else {
										System.out.println("line index = 0");
									}
									
									backObj.setLineIndex(lineIndex - 1);
								} else { // end if
									if (backObj.getXStartPos() <= backObj.getXEndPos())
										backPos.x = (float) (backPos.x - Math.cos(nTempRate) * speed);
									else
										backPos.x = (float) (backPos.x + Math.cos(nTempRate) * speed);

									if (backObj.getYStartPos() <= backObj.getYEndPos())
										backPos.y = (float) (backPos.y - Math.sin(nTempRate)* speed);
									else 
										backPos.y = (float) (backPos.y + Math.sin(nTempRate)* speed);
								}

								backObj.getPosition().setPos(backPos);

								if (flag)
									break;
							}// end for pos = i
						} // end for moveCount

						ballObj = ballObjects.get(i);
						ballPos = ballObj.getPosition();

						beObj = ballObjects.get(i + 1);
						bePos = beObj.getPosition();

						xWidth = ballPos.x - bePos.x;
						yWidth = ballPos.y - bePos.y;

						ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

						if ((int)ballWidth <= (int)(32 * activity.xScreenSize)) {
							bHitFlag = true;
							hitNum = i;
							hitColor = ballObj.getColorVal();
						}

						break;
					} else {
						flag = true;
					}
				} // end if ballWidth > 33
			} // end if i < ballObjects.size
		}
	}

	private void characterBackBall() {
		
		if (characterFlag && characterKind == CHARACTER_BACK) {
			BitmapObject ballObj = null;
			Point2D ballPos = null;
			BitmapObject backObj = null;
			BitmapObject nextObj = null;
			Point2D nextPos = null;
			Point2D backPos = null;
			int speed = 1;
			double xWidth = 0;
			double yWidth = 0;
			double ballWidth = 0;
			int moveCount = 0;
			boolean bFlag = false;
			boolean bBackFlag = false;

			for (moveCount = 0; moveCount < BALL_SPEED; moveCount++) {
				for (int i = 0; i < ballObjects.size(); i++) {
					bBackFlag = false;
					ballObj = ballObjects.get(i);
					ballPos = ballObj.getPosition();

					double rate = Math.atan2(Math.abs(ballObj.getYEndPos() - ballObj.getYStartPos()), Math.abs(ballObj.getXEndPos() - ballObj.getXStartPos()));
					
					if (i < ballObjects.size() - 1) {
						nextObj = ballObjects.get(i + 1);
						nextPos = nextObj.getPosition();
						xWidth = ballPos.x - nextPos.x;
						yWidth = ballPos.y - nextPos.y;
						ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

						if ((int)ballWidth > (int)(30 * activity.yScreenSize)) {
							bBackFlag = true;
							for (int backCount = 0; backCount < 2; backCount++) {
								bFlag = false;
								for (int count = i; count >= 0; count--) {
									backObj = ballObjects.get(count);
									backPos = backObj.getPosition();
									
									if (count > 0) {
										BitmapObject comObj = ballObjects.get(count - 1);
										Point2D comPos = comObj.getPosition();

										xWidth = comPos.x - backPos.x;
										yWidth = comPos.y - backPos.y;

										ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

										if (ballWidth > (int)(32 * activity.yScreenSize)) {
											bFlag = true;
										}
									} // end if count > 0
									
									double nTempRate = Math.atan2(Math.abs(backObj.getYEndPos() - backObj.getYStartPos()), Math.abs(backObj.getXEndPos()
													- backObj.getXStartPos()));
									
									if ((backPos.x + Math.cos(nTempRate) * 1 > backObj.getXStartPos() && backObj.getXStartPos() > backObj.getXEndPos())
											|| (backPos.x - Math.cos(nTempRate) * 1 < backObj.getXStartPos() && backObj.getXStartPos() < backObj.getXEndPos())
											|| (backPos.y - Math.sin(nTempRate) * 1 < backObj.getYStartPos() && backObj.getYEndPos() > backObj.getYStartPos())
											|| (backPos.y + Math.sin(nTempRate) * 1 > backObj.getYStartPos() && backObj.getYEndPos() < backObj.getYStartPos())) {
										int lineIndex = backObj.getLineIndex();
										
										if (lineIndex == 1) {
											if (backObj.getXStartPos() <= backObj.getXEndPos())
												backPos.x = (float) (backPos.x - Math.cos(nTempRate) * speed);
											else
												backPos.x = (float) (backPos.x + Math.cos(nTempRate) * speed);

											if (backObj.getYStartPos() <= backObj.getYEndPos())
												backPos.y = (float) (backPos.y - Math.sin(nTempRate)* speed);
											else 
												backPos.y = (float) (backPos.y + Math.sin(nTempRate)* speed);
										} else if (lineIndex > 1) {
											backPos.x = backObj.getXStartPos();
											backPos.y = backObj.getYStartPos();
											
											lineObj obj = levelLine.get(backObj.getLineIndex() - 2);
											backObj.setXStartPos(obj.x);
											backObj.setXEndPos(backPos.x);
											backObj.setYStartPos(obj.y);
											backObj.setYEndPos(backPos.y);
											backObj.setLineIndex(lineIndex - 1);
										} else {
											System.out.println("line index = 0");
										}
										
									} else { // end if
										if (backObj.getXStartPos() <= backObj.getXEndPos())
											backPos.x = (float) (backPos.x - Math.cos(nTempRate) * speed);
										else
											backPos.x = (float) (backPos.x + Math.cos(nTempRate) * speed);

										if (backObj.getYStartPos() <= backObj.getYEndPos())
											backPos.y = (float) (backPos.y - Math.sin(nTempRate)* speed);
										else 
											backPos.y = (float) (backPos.y + Math.sin(nTempRate)* speed);
									}
									
									backObj.getPosition().setPos(backPos);
									
									if (bFlag)
										break;
								} // end for count = i
							} // end for backCount = 0

							xWidth = ballPos.x - nextPos.x;
							yWidth = ballPos.y - nextPos.y;
							ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

							if (ballWidth < (int)(38 * activity.xScreenSize)) {
								if (ballObj.getColorVal() == nextObj.getColorVal()) {
									bHitFlag = true;
									hitNum = i;
									hitColor = ballObj.getColorVal();
								}
							}
						} //end if ballwidth > 34
					}
					
					if ((ballPos.x + Math.cos(rate) * 1 > ballObj.getXStartPos() && ballObj.getXStartPos() > ballObj.getXEndPos())
							|| (ballPos.x - Math.cos(rate) * 1 < ballObj.getXStartPos() && ballObj.getXStartPos() < ballObj.getXEndPos())
							|| (ballPos.y - Math.sin(rate) * 1 < ballObj.getYStartPos() && ballObj.getYEndPos() > ballObj.getYStartPos())
							|| (ballPos.y + Math.sin(rate) * 1 > ballObj.getYStartPos() && ballObj.getYEndPos() < ballObj.getYStartPos())) {
						int lineIndex = ballObj.getLineIndex();
						
						if (lineIndex == 1) {
							if (ballObj.getXStartPos() <= ballObj.getXEndPos())
								ballPos.x = (float) (ballPos.x - Math.cos(rate) * speed);
							else
								ballPos.x = (float) (ballPos.x + Math.cos(rate) * speed);

							if (ballObj.getYStartPos() <= ballObj.getYEndPos())
								ballPos.y = (float) (ballPos.y - Math.sin(rate)* speed);
							else 
								ballPos.y = (float) (ballPos.y + Math.sin(rate)* speed);
						} else if (lineIndex > 1) {
							ballPos.x = ballObj.getXStartPos();
							ballPos.y = ballObj.getYStartPos();
							
							lineObj obj = levelLine.get(ballObj.getLineIndex() - 2);
							ballObj.setXStartPos(obj.x);
							ballObj.setXEndPos(ballPos.x);
							ballObj.setYStartPos(obj.y);
							ballObj.setYEndPos(ballPos.y);
							ballObj.setLineIndex(lineIndex - 1);
						} else {
							System.out.println("line index = 0");
						}
						
					} else {
						if (ballObj.getXStartPos() <= ballObj.getXEndPos())
							ballPos.x = (float) (ballPos.x - Math.cos(rate) * speed);
						else
							ballPos.x = (float) (ballPos.x + Math.cos(rate) * speed);

						if (ballObj.getYStartPos() <= ballObj.getYEndPos())
							ballPos.y = (float) (ballPos.y - Math.sin(rate)* speed);
						else 
							ballPos.y = (float) (ballPos.y + Math.sin(rate)* speed);
					}
					
					ballObj.getPosition().setPos(ballPos);
					
					if (bBackFlag)
						break;
				} // end for i < ballObjects.size
			} // end for moveCount = 0
		} // end if character flag && characterkind == CB
	}
	
	private void bulletHitObjects() {
		if (pageData.status == GAME_GO && bBulletFlag) {
			float xBulletPos = bulletObject.getPosition().x;
			float yBulletPos = bulletObject.getPosition().y;

			double xWidth = 0;
			double yWidth = 0;
			double ballWidth = 0;
			
			for (int i = 0; i <= ballObjects.size() - 1; i++) {
				BitmapObject ballObj = ballObjects.get(i);

				float xBallPos = ballObj.getPosition().x;
				float yBallPos = ballObj.getPosition().y;

				xWidth = xBallPos - xBulletPos;
				yWidth = yBallPos - yBulletPos;
				ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);

				if (ballWidth < (int)(30 * activity.yScreenSize)) {
					boolean flag = false;

					bulletBallMoveCount = 0;
					double moveRate = Math.atan2(Math.abs(ballObj.getYEndPos() - ballObj.getYStartPos()), Math.abs(ballObj.getXEndPos()- ballObj.getXStartPos()));
					
					hitColor = bulletObject.getColorVal();

					double xNextPos = 0;
					double yNextPos = 0;
					double xBeforePos = 0;
					double yBeforePos = 0;
					double BeforeBallWidth = 0;
					double NextBallWidth = 0;
					
					if (ballObj.getXStartPos() <= ballObj.getXEndPos()) {
						
						xBeforePos = xBulletPos - (xBallPos - Math.cos(moveRate) * BALL_IMAGE_WIDTH);
						xNextPos = xBulletPos - (xBallPos + Math.cos(moveRate) * BALL_IMAGE_WIDTH);
						
						if (ballObj.getYEndPos() >= ballObj.getYStartPos()) {
							yBeforePos = yBulletPos - (yBallPos - Math.sin(moveRate) * BALL_IMAGE_WIDTH);
							yNextPos = yBulletPos - (yBallPos + Math.sin(moveRate) * BALL_IMAGE_WIDTH);
						} else {
							yBeforePos = yBulletPos - (yBallPos + Math.sin(moveRate) * BALL_IMAGE_WIDTH);
							yNextPos = yBulletPos - (yBallPos - Math.sin(moveRate) * BALL_IMAGE_WIDTH);
						}
						
						BeforeBallWidth = Math.sqrt(xBeforePos * xBeforePos + yBeforePos * yBeforePos);
						NextBallWidth = Math.sqrt(xNextPos * xNextPos + yNextPos * yNextPos);
						
						if (BeforeBallWidth < NextBallWidth) {
							hittmpNum = hitNum = i + 1;
							hittmpNum--;
						} else {
							hittmpNum = hitNum = i;
							flag = true;
						}
					} else {
						xBeforePos = xBulletPos - (xBallPos + Math.cos(moveRate) * BALL_IMAGE_WIDTH);
						xNextPos = xBulletPos - (xBallPos - Math.cos(moveRate) * BALL_IMAGE_WIDTH);
						
						if (ballObj.getYEndPos() >= ballObj.getYStartPos()) {
							yBeforePos = yBulletPos - (yBallPos - Math.sin(moveRate) * BALL_IMAGE_WIDTH);
							yNextPos = yBulletPos - (yBallPos + Math.sin(moveRate) * BALL_IMAGE_WIDTH);
						} else {
							yBeforePos = yBulletPos - (yBallPos + Math.sin(moveRate) * BALL_IMAGE_WIDTH);
							yNextPos = yBulletPos - (yBallPos - Math.sin(moveRate) * BALL_IMAGE_WIDTH);
						}
						
						BeforeBallWidth = Math.sqrt(xBeforePos * xBeforePos + yBeforePos * yBeforePos);
						NextBallWidth = Math.sqrt(xNextPos * xNextPos + yNextPos * yNextPos);
						
						if (BeforeBallWidth < NextBallWidth) {
							hittmpNum = hitNum = i + 1;
							hittmpNum--;
						} else {
							hittmpNum = hitNum = i;
							flag = true;
						}
					}
					
					xInsertBallPos = xBallPos;
					yInsertBallPos = yBallPos;
					insertLine = ballObj.getLineIndex();
					xInsertStartPos = ballObj.getXStartPos();
					yInsertStartPos = ballObj.getYStartPos();
					xInsertEndPos = ballObj.getXEndPos();
					yInsertEndPos = ballObj.getYEndPos();

					if (flag) {
						int speed = 1;
						Point2D tmpPoint = new Point2D(xBallPos, yBallPos);
						SpeedObject init_speed = new SpeedObject(0, 5, 0, 0, 1);

						BitmapObject tmpObj = new BitmapObject(activity, BALL_IMAGE_NAME, tmpPoint, (int) BALL_IMAGE_WIDTH,	(int) BALL_IMAGE_HEIGHT, ballBitmaps
										.get(hitColor));

						tmpObj.setXStartPos(ballObj.getXStartPos());
						tmpObj.setXEndPos(ballObj.getXEndPos());
						tmpObj.setYStartPos(ballObj.getYStartPos());
						tmpObj.setYEndPos(ballObj.getYEndPos());
						tmpObj.setLineIndex(ballObj.getLineIndex());
						tmpObj.getPosition().setPos(ballObj.getPosition());

						for (int j = 0; j < BALL_IMAGE_WIDTH; j++) {
							double rate = Math.atan2(tmpObj.getYEndPos() - tmpObj.getYStartPos(), Math.abs(tmpObj.getXEndPos() - tmpObj.getXStartPos()));
							if ((tmpPoint.x + Math.cos(rate) * speed > tmpObj.getXEndPos() && tmpObj.getXEndPos() > tmpObj.getXStartPos())
									|| (tmpPoint.x - Math.cos(rate) * speed < tmpObj.getXEndPos() && tmpObj.getXStartPos() > tmpObj.getXEndPos())
									|| (tmpPoint.y + Math.sin(rate) * speed > tmpObj.getYEndPos() && tmpObj.getYEndPos() > tmpObj.getYStartPos())
									|| (tmpPoint.y - Math.sin(rate) * speed < tmpObj.getYEndPos() && tmpObj.getYEndPos() < tmpObj.getYStartPos())) {

								tmpPoint.x = tmpObj.getXEndPos();
								tmpPoint.y = tmpObj.getYEndPos();

								if (tmpObj.getLineIndex() + 1 < levelLine.size()) {
									lineObj line = levelLine.get(tmpObj.getLineIndex() + 1);
									tmpObj.setXStartPos(tmpObj.getXEndPos());
									tmpObj.setXEndPos(line.x);
									tmpObj.setYStartPos(tmpObj.getYEndPos());
									tmpObj.setYEndPos(line.y);
									tmpObj.setLineIndex(tmpObj.getLineIndex() + 1);
									tmpObj.getPosition().setPos(tmpPoint);
								}
							} else {
								if (tmpObj.getXStartPos() <= tmpObj.getXEndPos())
									tmpPoint.x = (float) (tmpPoint.x + Math.cos(rate) * 1);
								else
									tmpPoint.x = (float) (tmpPoint.x - Math.cos(rate) * 1);

								tmpPoint.y = (float) (tmpPoint.y + Math.sin(rate) * 1);
								tmpObj.getPosition().setPos(tmpPoint);
							}
						}

						xInsertBallPos = tmpObj.getPosition().x;
						yInsertBallPos = tmpObj.getPosition().y;
						xInsertStartPos = tmpObj.getXStartPos();
						yInsertStartPos = tmpObj.getYStartPos();
						xInsertEndPos = tmpObj.getXEndPos();
						yInsertEndPos = tmpObj.getYEndPos();
						insertLine = tmpObj.getLineIndex();

						tmpObj.removeObject();
						tmpObj = null;
					}

					moveStatus(GAME_BALL_INSERT);

					Point2D init_point = new Point2D(-100, -100);
					SpeedObject init_speed = new SpeedObject(0, 5, 0, 0, 1);

					BitmapObject insertObj = new BitmapObject(activity, BALL_IMAGE_NAME, init_point, (int) BALL_IMAGE_WIDTH, (int) BALL_IMAGE_HEIGHT,
							ballBitmaps.get(hitColor));

					insertObj.setXStartPos(ballObj.getXStartPos());
					insertObj.setXEndPos(ballObj.getXEndPos());
					insertObj.setYStartPos(ballObj.getYStartPos());
					insertObj.setYEndPos(ballObj.getYEndPos());
					insertObj.setLineIndex(insertLine);
					insertObj.setColorVal(hitColor);
					insertObj.getPosition().setPos(init_point);

					ballObjects.add(hitNum, insertObj);

					break;
				}
			}
		}
	}

	private void insertBulletBall() {
		boolean flag = false;
		int moveCount = 0;

		BitmapObject ballObj = null;
		BitmapObject ballObj1 = null;
		double xWidth = 0;
		double yWidth = 0;
		int speed = 1;
		BULLET_SPEED = (int)(6 * activity.xScreenSize);

		if (bulletBallMoveCount < 1)
			moveCount = (int)(30 * activity.xScreenSize) / 2;
		else
			moveCount = (int)(30 * activity.xScreenSize) - (int)(30 * activity.xScreenSize) / 2;

		for (int j = 0; j < moveCount; j++) {
			for (int n = hittmpNum; n >= 0; n--) {
				flag = false;
				ballObj = ballObjects.get(n);
				Point2D ballPoints = ballObj.getPosition();

				if (n > 0) {
					ballObj1 = ballObjects.get(n - 1);
					Point2D nextBallPoint = ballObj1.getPosition();
					xWidth = ballPoints.x - nextBallPoint.x;
					yWidth = ballPoints.y - nextBallPoint.y;

					double ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);
					if (n == hittmpNum) {
						if (hittmpNum == hitNum) {
							xWidth = xInsertBallPos - nextBallPoint.x;
							yWidth = yInsertBallPos - nextBallPoint.y;
							ballWidth = Math.sqrt(xWidth * xWidth + yWidth * yWidth);
							if (ballWidth > (int)(30 * activity.xScreenSize))
								flag = true;
						} else {
							if (ballWidth > (int)(30 * activity.xScreenSize))
								flag = true;
						}
					} else {
						if (ballWidth > (int)(30 * activity.xScreenSize))
							flag = true;	
					}
				}

				double rate = Math.atan2(ballObj.getYEndPos() - ballObj.getYStartPos(), ballObj.getXEndPos() - ballObj.getXStartPos());

				if ((ballPoints.x + Math.cos(rate) * 1 > ballObj.getXEndPos() && ballObj.getXEndPos() > ballObj.getXStartPos())
						|| (ballPoints.x - Math.cos(rate) * speed < ballObj.getXEndPos() && ballObj.getXStartPos() > ballObj.getXEndPos())
						|| (ballPoints.y + Math.sin(rate) * speed > ballObj.getYEndPos() && ballObj.getYEndPos() > ballObj.getYStartPos())
						|| (ballPoints.y - Math.sin(rate) * speed < ballObj.getYEndPos() && ballObj.getYEndPos() < ballObj.getYStartPos())) {

					if (ballPoints.x != -100) {
						ballPoints.x = ballObj.getXEndPos();
						ballPoints.y = ballObj.getYEndPos();

						if (ballObj.getLineIndex() + 1 < levelLine.size()) {
							lineObj obj = levelLine.get(ballObj.getLineIndex() + 1);
							ballObj.setXStartPos(ballObj.getXEndPos());
							ballObj.setXEndPos(obj.x);
							ballObj.setYStartPos(ballObj.getYEndPos());
							ballObj.setYEndPos(obj.y);
							ballObj.setLineIndex(ballObj.getLineIndex() + 1);
						}
					}
				} else {
					ballPoints.x = (float) (ballPoints.x + Math.cos(rate) * (speed));
					ballPoints.y = (float) (ballPoints.y + Math.sin(rate) * speed);
				}

				ballObj.getPosition().setPos(ballPoints);

				if (flag)
					break;
			}
		}

		if (bulletBallMoveCount < 1)
			return;

		ballObj = ballObjects.get(hitNum);
		ballObj.getPosition().x = xInsertBallPos;
		ballObj.getPosition().y = yInsertBallPos;
		ballObj.setXStartPos(xInsertStartPos);
		ballObj.setXEndPos(xInsertEndPos);
		ballObj.setYStartPos(yInsertStartPos);
		ballObj.setYEndPos(yInsertEndPos);
		ballObj.setLineIndex(insertLine);

		moveStatus(GAME_GO);
		bulletObject.removeObject();
		BULLET_SPEED = (int)(50 * activity.xScreenSize);
		bulletBallMoveCount = 0;

		activity.GetState();
		loadSound();
		Utils.startPlay(activity.isCheckSound, putbullet_sound, false);

		bHitFlag = true;
		bBulletFlag = false;
		speedFlag = -1;
		hittmpNum = 0;
	}

	private void changeSpeed() {
		if (ballObjects.size() > 0 && isCompleteFlag) {
			if (!characterFlag) {
				int curBallCount = ballObjects.size();
				// ball speed is first ball speed
				// if current appeared ball count less firstBallCount, setting
				if (firstBallCount > curBallCount && BALL_TEMP_COUNT < BALL_COUNT - 10) {
					BALL_SPEED = 10;
				} else if (curBallCount < 10) {
					BALL_SPEED = 4;
				} else {
					BALL_SPEED = 2;
					
					if (levelFlag)
						BALL_SPEED = 3;
				}
			} else {
				if (characterKind == CHARACTER_SLOW) {
					BALL_SPEED = 1;
				} else if (characterKind == CHARACTER_BACK) {
					BALL_SPEED = 10;
				}
			}
		}
	}

	private void makePrevGunBall(Canvas g) {
		String imageName = "";
		Bitmap bitmap = null;
		Field field;
		int fieldId = 0;

		compareColorBall();
		imageName = GUN_PREV_BALL_IMAGE_NAME;
		imageName += String.valueOf(prevIndex);

		try {
			field = R.drawable.class.getDeclaredField(imageName);
			fieldId = field.getInt((Object) R.drawable.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);
		prevBallBitmap = Bitmap.createScaledBitmap(bitmap, (int)(90 * activity.xScreenSize), (int)(90 * activity.xScreenSize), true);
		bitmap.recycle();

		Bitmap image1 = null;
		image1 = rotateImage(prevBallBitmap, 1, 0, 0, prevBallBitmap.getWidth(), prevBallBitmap.getHeight(), (int) angle - 90);

		pointX = ZUMAnia.gameWidth / 2;
		pointY = ZUMAnia.gameHeight;

		g.drawBitmap(image1, pointX - image1.getWidth() / 2, (int)(430 * activity.yScreenSize) - image1.getHeight() / 2, null);
		prevBallBitmap.recycle();
		image1.recycle();
	}

	private void makeNextGunBall(Canvas g) {
		String imageName = "";
		Bitmap bitmap = null;
		Field field;
		int fieldId = 0;

		imageName = GUN_NEXT_BALL_IMAGE_NAME;
		imageName += String.valueOf(nextIndex);

		try {
			field = R.drawable.class.getDeclaredField(imageName);
			fieldId = field.getInt((Object) R.drawable.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

		bitmap = BitmapFactory.decodeResource(activity.getResources(), fieldId);

		nextBallBitmap = Bitmap.createScaledBitmap(bitmap, (int)(90 * activity.xScreenSize), (int)(90 * activity.xScreenSize), true);

		bitmap.recycle();

		Bitmap image1 = null;

		image1 = rotateImage(nextBallBitmap, 1, 0, 0,nextBallBitmap.getWidth(), nextBallBitmap.getHeight(), (int) angle - 90);

		pointX = ZUMAnia.gameWidth / 2;
		pointY = ZUMAnia.gameHeight;

		g.drawBitmap(image1, pointX - image1.getWidth() / 2, (int)(430 * activity.yScreenSize) - image1.getHeight() / 2, null);

		image1.recycle();
	}

	private void makeBulletBall() {
		float init_x = 385 * activity.xScreenSize;
		float init_y = 415 * activity.yScreenSize;

		float x_move_speed = 0;
		float y_move_speed = 0;

		Point2D init_point = new Point2D(init_x, init_y);
		SpeedObject init_speed = new SpeedObject(BALL_WRIGGLE_SPEED, (int) x_move_speed, (int) y_move_speed, 0, BALL_IMAGE_COUNT);

		BitmapObject object = new BitmapObject(activity, BULLET_IMAGE_NAME, init_point, (int) BULLET_IMAGE_WIDTH,
				(int) BULLET_IMAGE_HEIGHT, bulletBitmaps.get(prevIndex - 1));

		object.setColorVal(prevIndex - 1);
		object.getPosition().setPos(init_point);

		bulletObject = object;
	}

	private void getRemainBallCount() {
		remainColorArray.removeAllElements();
		remainColorArray.clear();

		int objectColor = -1;
		int remainColor = -1;
		boolean flag = false;

		if (ballObjects.size() <= 0)
			return;
		for (int i = 0; i <= ballObjects.size() - 1; i++) {
			flag = false;
			objectColor = ballObjects.get(i).getColorVal();

			for (int j = 0; j <= remainColorArray.size() - 1; j++) {
				remainColor = remainColorArray.get(j);

				if (objectColor == remainColor) {
					flag = true;
					break;
				}
			}

			if (!flag) {
				remainColorArray.add(objectColor);
			}
		}
	}

	private void moveBulletBall() {

		if (bBulletFlag) {
			float xBulletPos = 0;
			float yBulletPos = 0;
			float init_x = ZUMAnia.gameWidth / 2 - BULLET_IMAGE_WIDTH / 2;

			int nBulletSpeed = BULLET_SPEED;

			if (speedFlag == -1) {
				nBulletSpeed = (int)(70 * activity.xScreenSize);
				speedFlag = 1;
			}

			if (prevPoint.x != 0 && prevPoint.y != 0) {

				BitmapObject bulletObj = bulletObject;
				if (bulletObj != null) {
					Point2D bulletPoint = bulletObj.getPosition();
					float x_move = 0;
					float y_move = 0;

					xBulletPos = bulletPoint.x;
					yBulletPos = bulletPoint.y;

					if (bBrokenFlag) {
						nBulletSpeed = 1;
					}

					if (init_x <= prevPoint.x)
						x_move = (float) (xBulletPos + nBulletSpeed * Math.cos(bulletRate));
					else
						x_move = (float) (xBulletPos - nBulletSpeed * Math.cos(bulletRate));

					y_move = (float) (yBulletPos - nBulletSpeed * Math.sin(bulletRate));

					bulletPoint.x = x_move;
					bulletPoint.y = y_move;
					bulletObj.getPosition().setPos(bulletPoint);
					bulletObj.setBitmap(bulletBitmaps.get(bulletObj.getColorVal()));

					if (x_move > ZUMAnia.gameWidth || x_move < 1 || y_move < 1) {
						bulletObject.removeObject();
						bulletObject = null;
						bBulletFlag = false;
						speedFlag = -1;
					}
				}
			}
		} else {
			if (bulletObject != null) {
				bulletObject.removeObject();
				bulletObject = null;
			}	
		}
	}

	private void compareColorBall()
	{	
		getRemainBallCount();

		if (remainColorArray.size() == 0) {
			return;
		}
		else
		{
			int firFlag = -1;
			int colorIndex = 0;
			for (int i = 0; i <= remainColorArray.size() - 1; i++) {
				if (prevIndex == remainColorArray.get(i) + 1)
					firFlag = 1;
			}

			int secFlag = -1;
			for (int i = 0; i <= remainColorArray.size() - 1; i++) {
				if (nextIndex == remainColorArray.get(i) + 1)
					secFlag = 1;
			}

			if (firFlag != 1)
			{
				Random firRand = new Random();
				if(remainColorArray.size() == 1)
					prevIndex = remainColorArray.get(0) + 1;
				else
				{
					colorIndex = firRand.nextInt(remainColorArray.size() - 1);
					prevIndex = remainColorArray.get(colorIndex) + 1;
				}
			}
			
			firFlag = -1;

			if (secFlag != 1)
			{
				Random secRand = new Random();
				if(remainColorArray.size() == 1)
					nextIndex = remainColorArray.get(0) + 1;
				else
				{
					colorIndex = secRand.nextInt(remainColorArray.size() - 1);
					nextIndex = remainColorArray.get(colorIndex) + 1;
				}
			}
			
			secFlag = -1;
		}
	}
	
	private void compareDoubleBall() {
		getRemainBallCount();

		int firRand = (int) (Math.random() * 10);
		int secRand = (int) (Math.random() * 10);

		if (remainColorArray.size() == 0) {
			return;
		}
		if (remainColorArray.size() == 1) {
			if (nextIndex != remainColorArray.get(0) + 1)
				prevIndex = remainColorArray.get(0) + 1;

			nextIndex = remainColorArray.get(0) + 1;
		}

		else if (remainColorArray.size() == 2) {
			int firFlag = -1;
			for (int i = 0; i <= remainColorArray.size() - 1; i++) {
				if (prevIndex == remainColorArray.get(i) + 1)
					firFlag = 1;
			}

			int secFlag = -1;
			for (int i = 0; i <= remainColorArray.size() - 1; i++) {
				if (nextIndex == remainColorArray.get(i) + 1)
					secFlag = 1;
			}

			if (firFlag != 1 && firRand >= 0 && firRand < 5)
				prevIndex = remainColorArray.get(0) + 1;

			if (firFlag != 1 && firRand >= 5 && firRand < 10)
				prevIndex = remainColorArray.get(1) + 1;

			firFlag = -1;

			if (secFlag != 1 && secRand >= 0 && secRand < 5)
				nextIndex = remainColorArray.get(0) + 1;

			if (secFlag != 1 && secRand >= 5 && secRand < 10)
				prevIndex = remainColorArray.get(1) + 1;

			secFlag = -1;
		}
	}

	private void deleteBall() {

		int ballSize = ballObjects.size();

		if (pageData.status == GAME_GO && bHitFlag && ballSize > 2) {

			BitmapObject ballObj;
			int nCount = 1;
			int nStart = 0;
			int nEnd = 0;
			nStart = nEnd = hitNum;

			for (int i = hitNum - 1; i >= 0; i--) {
				ballObj = ballObjects.get(i);
				if (ballObj.getColorVal() != hitColor)
					break;
				nStart = i;
				nCount++;
			}

			for (int i = hitNum + 1; i < ballSize; i++) {
				ballObj = ballObjects.get(i);
				if (ballObj.getColorVal() != hitColor)
					break;
				nEnd = i;
				nCount++;
			}

			if (nCount >= 3) {
				pageData.score += ONE_HIT_SCORE * nCount;
				tempScore = pageData.score;
				for (int nDel = 0; nDel <= nEnd - nStart; nDel++) {
					ballObj = ballObjects.get(nStart);

					Point2D init_point = new Point2D(ballObj.getPosition().x, ballObj.getPosition().y);
					SpeedObject init_speed = new SpeedObject(0, 5, 0, 0, 1);
					BitmapObject object = new BitmapObject(activity, BALL_IMAGE_NAME, init_point, (int) BALL_IMAGE_WIDTH, (int) BALL_IMAGE_HEIGHT,
							breakBallBitmaps.get(ballObj.getColorVal()));

					object.getPosition().setPos(init_point);

					if (ballObj.characterFlag == true) {
						characterKind = ballObj.characterKind;
						characterFlag = true;
						if (characterKind == CHARACTER_BOMB) {
							makeBombBrokenBall();
							BitmapObject bombObj = brokenObjects.get(0);
							bombObj.getPosition().setPos(init_point);
							bombObj.getPosition().x = ballObj.getPosition().x - 20;
							bombObj.getPosition().y = ballObj.getPosition().y - 20;

							xBombPos = (int) (object.getPosition().x = ballObj.getPosition().x);
							yBombPos = (int) (object.getPosition().y = ballObj.getPosition().y);
						}

						characterStartTime = System.currentTimeMillis();
					}

					breakBallObjects.add(object);

					activity.GetState();
					loadSound();
						
					Utils.startPlay(activity.isCheckSound, break_sound, false);

					ballObjects.get(nStart).removeObject();
					ballObjects.removeElementAt(nStart);
					
					if(bulletObject != null)
						bBulletFlag = true;
					else
						bBulletFlag = false;
				}
			}

			hitColor = -1;
			bHitFlag = false;
			if(bulletObject != null)
				bBulletFlag = true;
			else
			{
				bBulletFlag = false;
				speedFlag = -1;
			}
		}
	}

	private void deleteCharacterBombBall() {
		if (characterFlag && characterKind == CHARACTER_BOMB) {
			brokenObjects.get(0).removeObject();
			brokenObjects.remove(0);
		}
	}

	private void processCharacterBall() {
		if (characterFlag && characterKind == CHARACTER_BOMB) {
			for (int i = 0; i < ballObjects.size(); i++) {
				BitmapObject ballObj = ballObjects.get(i);

				if (ballObj.getPosition().x > xBombPos - (int)(120 * activity.xScreenSize)
						&& ballObj.getPosition().x < xBombPos + (120 * activity.xScreenSize)
						&& ballObj.getPosition().y > yBombPos - (120 * activity.yScreenSize)
						&& ballObj.getPosition().y < yBombPos + (120 * activity.yScreenSize)) {
					ballObjects.get(i).removeObject();
					ballObjects.removeElementAt(i);
					i--;
				}
			}
		}
	}

	@Override
	public void loadPage(Object parm, Boolean isCanceled) {
		super.loadPage(parm, isCanceled);

		if (!isCanceled) {

		}

		Window a = activity.getWindow();

		// Set screen deflection
		setScreenDeviation(ZUMAnia.gameWidth / 2, 0);

		lifeFrog_BITMAP_WIDTH = 30;
		lifeFrog_BITMAP_HEIGHT = 30;
		
		loadBitmap();
		onResume();

		isLoaded = true;
		initGameData();
	}

	public void pointerPressed_pause(int x, int y) {
		int pauseX1 = 0;
		int pauseX2 = 0;
		int pauseY1 = 0;

		pauseX1 = ZUMAnia.gameWidth - (int)(120 * activity.xScreenSize);
		pauseX2 = pauseX1 + pauseBtnPressedBitmap.getWidth();
		pauseY1 = pauseBtnPressedBitmap.getHeight() + 5;

		if (x >= pauseX1 && x <= pauseX2 && y >= 5 && y <= pauseY1) {
			bMenuBtnPressed = true;

			PauseMenuDialog pauseMenuDlg = new PauseMenuDialog(this.context,
					this);
			pauseMenuDlg.getWindow().setBackgroundDrawableResource(R.drawable.pause_background);
			gPauseMenuDlg = pauseMenuDlg;
			pauseMenuDlg.show();

			gameData.currPage.page.onPause();

			return;
		}
		
		bMenuBtnPressed = false;
	}

	public void pointerPressed_gun(int x, int y) {
		
		if (bMenuBtnPressed)
			return ;
		
		int gunX1 = 0;
		int gunX2 = 0;
		int gunY1 = 0;
		int gunY2 = 0;

		gunX1 = ZUMAnia.gameWidth / 2 - gunBitmaps.get(0).getWidth() / 2;
		gunX2 = gunX1 + gunBitmaps.get(0).getWidth();
		gunY1 = ZUMAnia.gameHeight - gunBitmaps.get(0).getHeight();
		gunY2 = gunY1 + gunBitmaps.get(0).getHeight();

		if (x >= gunX1 && x <= gunX2 && y > gunY1 && y <= gunY2
				&& bBulletFlag == false) {
			bChangeGunBall = true;
			canvas.drawBitmap(prevImage, pointX - prevImage.getWidth() / 2,
					430 - prevImage.getHeight() / 2, null);
			tempIndex = prevIndex;
			prevIndex = nextIndex;
			nextIndex = tempIndex;
			makePrevGunBall(canvas);
			makeNextGunBall(canvas);
			return;
		}
		bChangeGunBall = false;
	}

	public void getXPoint()
	{
        //Set fullscreen
        Window win = activity.getWindow();
        
        int width;
        int height;
        
      	width = win.getWindowManager().getDefaultDisplay().getWidth();
    	height = win.getWindowManager().getDefaultDisplay().getHeight();

    	if(width == 800 && height == 480)
    		initXPoint = 445;
    	else if(width == 480 && height == 320)
    		initXPoint = 445;
    	else if(width == 320 && height == 240)
    		initXPoint = 445;
    	else
    		initXPoint = 440;
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		xPressed = (int) event.getX();
		yPressed = (int) event.getY();

		int dx = 0;
		int dy = 0;

		if (pageData.status == GAME_START) {

		} else if (pageData.status == GAME_MISSION_FAIL) {

		} else if (pageData.status == GAME_MISSION_COMPLETE) {

		} else if (pageData.status == GAME_OVER) {

		} else if (pageData.status == CONGRATULATIONS) {

		} else if (true) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				pointerPressed_pause(xPressed, yPressed);

				if (pageData.status != GAME_GO)
					break;
				
				pointerPressed_gun(xPressed, yPressed);
				if (bChangeGunBall == true || bMenuBtnPressed == true)
					break;
				if (!bBulletFlag && makeBulletFlag) {
					prevPoint.x = (int) (event.getX() - BALL_IMAGE_WIDTH / 2);
					prevPoint.y = (int) (event.getY() - BALL_IMAGE_HEIGHT / 2);
					bBulletFlag = true;

					bulletRate = Math.atan2(BACKGROUND_BITMAP_HEIGHT - (GUN_IMAGE_VARIATION * activity.yScreenSize) - prevPoint.y, Math.abs(ZUMAnia.gameWidth / 2
									- BULLET_IMAGE_WIDTH / 2 - prevPoint.x));
					dx = pointX - xPressed;
					dy = pointY - yPressed;
					angle = Math.toDegrees((Math.atan2(dy, dx)));
					getXPoint();
					tangle = Math.toDegrees((Math.atan2(((int)(initXPoint * activity.yScreenSize) - yPressed), dx)));
					makeBulletBall();
					getRemainBallCount();
					prevIndex = nextIndex;

					int nRand = (int) (Math.random() * 10);

					if (remainColorArray.size() == 2) {
						if (nRand >= 0 && nRand < 5)
							nextIndex = remainColorArray.get(0) + 1;
						else
							nextIndex = remainColorArray.get(1) + 1;
					} else if (remainColorArray.size() == 1) {
						nextIndex = remainColorArray.get(0) + 1;
					} else
						nextIndex = (int) (Math.random() * 10000)
								% BALL_IMAGE_COUNT + 1;

					activity.GetState();
					Utils.startPlay(activity.isCheckSound, bullet_sound, false);

				}
				break;
			case MotionEvent.ACTION_MOVE:
				break;
			case MotionEvent.ACTION_UP:
				break;
			}
		}
		return false;
	}

	@Override
	public synchronized void onSurfaceRedraw(Canvas c, long count) {
		long startTime = System.currentTimeMillis();
		long remainTime;

		if (!isIntentPause) {
			if (c != null)
				onRedraw(c, count);

			onNextFrame(true, c);

			if (gameData.gameStatus == ZUMAnia.GAME_NONE)
				moveShinerObjs(c);
		}

		remainTime = GAME_REFRESH_RATE - (System.currentTimeMillis() - startTime);
		if (remainTime < 0)
			remainTime = 1;

		setSurfaceViewRedrawListener(this, remainTime);

		if (settting_flag) {
			PauseMenuDialog pauseMenuDlg = new PauseMenuDialog(this.context, this);
			
			pauseMenuDlg.getWindow().setBackgroundDrawableResource(R.drawable.pause_background);
			pauseMenuDlg.show();
			gameData.currPage.page.onPause();
			settting_flag = false;
		}
	}

	private synchronized void onRedraw(Canvas c, long count) {
		canvas = c;
		c.drawBitmap(backgroundBitmap, 0, 0, null);
		
		if (pageData.status == GAME_GO || pageData.status == GAME_BALL_INSERT) {
			Bitmap image;
			pointX = ZUMAnia.gameWidth / 2;
			image = rotateImage(targetLineBitmap, 1, 0, 0, (int)(90 * activity.xScreenSize), (int)(800 * activity.yScreenSize), (int) tangle - 90);
			pointY = ZUMAnia.gameHeight;
			c.drawBitmap(image, pointX - image.getWidth() / 2, (int)(430 * activity.yScreenSize) - image.getHeight() / 2, null);
			image.recycle();	
		}
		
		//
		c.drawBitmap(levelBitmap, 0, 0, null);
		c.drawBitmap(endPipeBitmap, xEndPipe, yEndPipe, null);

		super.onSurfaceRedraw(c, count);
		c.drawBitmap(gamebg, 0, 0, null);
		c.drawBitmap(pauseBtnNormalBitmap, ZUMAnia.gameWidth - (int)(120 * activity.xScreenSize), 5,	null);
		makeGameData(c);
	}

	private void characterTime() {
		long curTime = System.currentTimeMillis();

		if (pageData.status == GAME_MISSION_FAIL) {
			if (curTime - characterStartTime > CHARACTER_TIME) {
				unLoadBitmap();
				loadBitmap();
				initGameData();
			}
		}

		if (pageData.status == GAME_OVER) {
			if (curTime - characterStartTime > CHARACTER_TIME) {
				pageData.score = 0;
				activity.selectPage(R.layout.selectlevel, true, null);
			}
		}
		
		if (pageData.status == GAME_MISSION_COMPLETE) {
			if (curTime - characterStartTime > CHARACTER_TIME) {
				unLoadBitmap();
				if (activity.level_number == 12) {
					activity.selectPage(R.layout.selectlevel, true, null);
					return ;
				}
				loadBitmap();
				initGameData();
			}
		}

		if (characterFlag && characterKind == CHARACTER_BACK || characterFlag
				&& characterKind == CHARACTER_SLOW) {
			if (curTime - characterStartTime > 5000) {
				characterFlag = false;
				characterKind = CHARACTER_DEFAULT;
				characterStartTime = 0;
			}
		}
	}

	private void completeLevel() {

		float init_x = BACKGROUND_BITMAP_WIDTH / 2 - missionCompleteBitmap.getWidth() / 2;
		float init_y = BACKGROUND_BITMAP_HEIGHT / 2 - missionCompleteBitmap.getHeight() / 2;

		Point2D init_point = new Point2D(init_x, init_y);

		BitmapObject object = new BitmapObject(activity, BULLET_IMAGE_NAME,
				init_point, (int) missionCompleteBitmap.getWidth(),
				(int) missionCompleteBitmap.getHeight(), missionCompleteBitmap);

		object.getPosition().setPos(init_point);

		missionObject = object;
		addRefObject(missionObject);
	}

	private void missionFail() {
		float init_x = BACKGROUND_BITMAP_WIDTH / 2 - missionFailBitmap.getWidth() / 2;
		float init_y = BACKGROUND_BITMAP_HEIGHT / 2 - missionFailBitmap.getHeight() / 2;

		Point2D init_point = new Point2D(init_x, init_y);

		BitmapObject object = new BitmapObject(activity, BULLET_IMAGE_NAME,
				init_point, (int) missionFailBitmap.getWidth(),
				(int) missionFailBitmap.getHeight(), missionFailBitmap);

		object.getPosition().setPos(init_point);

		gameOverObject = object;
		addRefObject(gameOverObject);
	}
	
	/**
	 * gameOver
	 * 
	 * @param 
	 */
	private void gameOver() {
		float init_x = BACKGROUND_BITMAP_WIDTH / 2 - gameOverBitmap.getWidth() / 2;
		float init_y = BACKGROUND_BITMAP_HEIGHT / 2 - gameOverBitmap.getHeight() / 2;
		
		Point2D init_point = new Point2D(init_x, init_y); //setting image appear position
		
		BitmapObject object = new BitmapObject(activity, BULLET_IMAGE_NAME,
				init_point, (int) gameOverBitmap.getWidth(),
				(int) gameOverBitmap.getHeight(), gameOverBitmap);
		
		object.getPosition().setPos(init_point);
		
		missionObject = object;
		addRefObject(missionObject);
		
	}

	/**
	 * Go next frame
	 * 
	 * @param bMoveable
	 */
	public synchronized void onNextFrame(boolean bMoveable, Canvas c) {

		removeAllRefObjects();
		characterTime();

		switch (pageData.status) {
		case GAME_NONE:
			break;
		case GAME_START:
			int nRand = (int) (Math.random() * 10);
			if (remainColorArray.size() == 2) {
				if (nRand >= 0 && nRand < 5) {
					prevIndex = remainColorArray.get(0) + 1;
					nextIndex = remainColorArray.get(0) + 1;
				} else {
					prevIndex = remainColorArray.get(1) + 1;
					nextIndex = remainColorArray.get(1) + 1;
				}
			} else if (remainColorArray.size() == 1) {
				prevIndex = remainColorArray.get(0) + 1;
				nextIndex = remainColorArray.get(0) + 1;
			} else {
				prevIndex = (int) (Math.random() * 10000) % BALL_IMAGE_COUNT
						+ 1;
				nextIndex = (int) (Math.random() * 10000) % BALL_IMAGE_COUNT
						+ 1;
			}

			makeBallObjs();
			makeBulletBall();
			makeGameObjects();
			break;
		case GAME_BALL_INSERT:
			insertBulletBall();
			bulletBallMoveCount++;
			makeGameObjects();

			draw_arrow(c);
			if (bChangeGunBall == false || bMenuBtnPressed == false)
				moveBulletBall();
			makePrevGunBall(c);

			makeNextGunBall(c);
			break;
		case GAME_GO:
			isMissionComplete();
			deleteCharacterBombBall();
			characterTime();
			changeSpeed();
			draw_arrow(c);
			if (bChangeGunBall == false || bMenuBtnPressed == false)
				moveBulletBall();

			makePrevGunBall(c);
			makeNextGunBall(c);
			moveBall();
			deleteBreakBallObjs();
			moveBackBall();
			bulletHitObjects();
			deleteBall();
			processCharacterBall();
			characterBackBall();
			makeGameObjects();
			break;
		case GAME_MISSION_COMPLETE:
			makeGameObjects();
			completeLevel();
			break;
		case GAME_MISSION_FAIL:
			makeGameObjects();
			missionFail();
			break;
		case GAME_OVER:
			makeGameObjects();
			gameOver();
			break;
		case CHARACTER_BACK:
			characterBackBall();
			break;
		}

	}

	private void makeGameObjects() {
		removeAllRefObjects();

		if (pageData.status == GAME_GO || pageData.status == GAME_BALL_INSERT) {
			for (int i = 0; i < ballObjects.size(); i++)
				addRefObject(ballObjects.get(i));

			if (!characterFlag	|| (characterFlag == true && characterKind != CHARACTER_BOMB)) {
				for (int i = 0; i < breakBallObjects.size(); i++)
					addRefObject(breakBallObjects.get(i));
			}

			if (characterFlag && characterKind == CHARACTER_BOMB) {
				addRefObject(brokenObjects.get(0));
				characterFlag = false;
				characterKind = CHARACTER_DEFAULT;
			}

			addRefObject(bulletObject);
		}

		addRefObject(gunObject);
	}

	private void draw_score(Canvas c) {
		Rect src, dest;
		// score
		int scoreStringCount = String.valueOf(pageData.score).length();

		boolean isContentScoreNumberOne = false;

		for (int i = scoreStringCount - 1; i >= 0; i--) {

			int scoreNumber = Integer.parseInt(String.valueOf(pageData.score).substring(i, i + 1));

			src = new Rect(0, 0, (int)(scoreNumberBitmaps.get(scoreNumber).getWidth() * activity.xScreenSize), (int)(30 * activity.yScreenSize));
			int numWidth = 0;
			if (scoreNumber == 1) {
				isContentScoreNumberOne = true;
				numWidth = (int)(SCORE_NUMBER_ONE_BITMAP_WIDTH * activity.xScreenSize);
			} else {
				numWidth = (int)(SCORE_NUMBER_BITMAP_WIDTH * activity.xScreenSize);
			}

			int numMinusWidth = 0;
			if (isContentScoreNumberOne) {
				numMinusWidth = (int)(SCORE_NUMBER_ONE_BITMAP_WIDTH * activity.xScreenSize);
			} else {
				numMinusWidth = (int)(SCORE_NUMBER_BITMAP_WIDTH * activity.xScreenSize);
			}

			int x = (int)(activity.xScreenSize * SCORE_NUMBER_BITMAP_X_END) - (int)(activity.xScreenSize * SCORE_NUMBER_BITMAP_WIDTH * (scoreStringCount - i - 1)) - numMinusWidth
					- SCORE_NUMBER_BITMAP_INTERVAL_WIDTH * (scoreStringCount - i - 1);
			
			dest = new Rect(x, (int)(SCORE_NUMBER_BITMAP_FIRST_Y * activity.yScreenSize), numWidth + x,
					(int)(activity.yScreenSize * (SCORE_NUMBER_BITMAP_HEIGHT + SCORE_NUMBER_BITMAP_FIRST_Y)));

			c.drawBitmap(scoreNumberBitmaps.get(scoreNumber), null, dest, null);
		}
	}

	private void makeGameData(Canvas c) {

		// progress
		Rect src, dest;
		src = new Rect(0, 0, progressBitmap.getWidth(), progressBitmap.getHeight());
		int rate = (int)(150 * activity.xScreenSize) * (BALL_TEMP_COUNT - 1) / BALL_COUNT;
		dest = new Rect((int)(185 * activity.xScreenSize), (int)(455 * activity.yScreenSize), (int)(185 * activity.xScreenSize) + rate, (int)(455 * activity.yScreenSize) + (int)(20 * activity.yScreenSize));
		c.drawBitmap(progressBitmap, src, dest, null);
		draw_score(c);
		draw_lifeFrog(c);
	}

	private void moveStatus(int status) {
		pageData.status = status;
	}

	private void init_status() {
		switch (pageData.status) {
		case GAME_NONE:
			break;
		case GAME_SHINER:
			int nRand = (int) (Math.random() * 10);

			if (remainColorArray.size() == 2) {
				if (nRand >= 0 && nRand < 5) {
					prevIndex = remainColorArray.get(0) + 1;
					nextIndex = remainColorArray.get(0) + 1;
				} else {
					prevIndex = remainColorArray.get(1) + 1;
					nextIndex = remainColorArray.get(1) + 1;
				}
			} else if (remainColorArray.size() == 1) {
				prevIndex = remainColorArray.get(0) + 1;
				nextIndex = remainColorArray.get(0) + 1;
			} else {
				prevIndex = (int) (Math.random() * 10000) % BALL_IMAGE_COUNT + 1;
				nextIndex = (int) (Math.random() * 10000) % BALL_IMAGE_COUNT + 1;
			}

			makeShinerObjs();
			makeBallObjs();
			makeBulletBall();
			break;
		case GAME_START:
			makeGameObjects();
			moveStatus(GAME_GO);
			break;
		case GAME_GO:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		pageData.score = 0;
		pageData.life = 3;
		return super.onKeyDown(keyCode, event);
	}
}
