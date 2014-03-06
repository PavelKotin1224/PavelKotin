package com.zumania;

import com.zumania.R;
import com.zumania.CommonPage;
import com.zumania.GameCanvas;
import com.zumania.MainPage;
import com.zumania.RankingDatabase;
import com.zumania.SurfaceViewRepaintListener;
import com.zumania.TimeEventListener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

public class ZUMAnia extends Activity {

	//member variable
	public static int gameWidth;
	public static int gameHeight;

	public static ZUMAnia activity = null;
	GameHandler mainHandler;

	Resources gameResources;
	GameData gameData = null;
	GameCanvas gameCanvas = null;

	public Vibrator vibrator = null; 
	public RankingDatabase gameDatabase;

	public static String path;
	public static int timeStamp;

	public static String versionFilename = "version.dat";
	public static String dataFilename = "data.dat";
	public static String settingFilename = "setting.dat";

	//game status
	public static int GAME_NONE = 0;
	public static int GAME_START = 1;
	public static int GAME_PAUSED = 2;
	public static int GAME_QUIT = 3;

	public int max_level = 1;
	public int level_number ;
	public int isCheckSound = 1;
	public int isCheckMusic = 1;
	public int isCheckVibration = 1;

	public static boolean bExitDlg = false;

	public float xScreenSize = 0;
	public float yScreenSize = 0;
	public int xNormalSize = 800;
	public int yNormalSize = 480;

	/** Inner class definition area */
	public class CommPageData {
		int pageId;
		CommonPage page;
		int prevPageId;
	}

	public class SplashPageData extends CommPageData {

	}

	public class MainPageData extends CommPageData {

	}

	public class ColorfulZumaPageData extends CommPageData {
		int status = 0;

		int level = 1;
		int step = 1;

		int life = 3;

		int bulletForLeafCount = 0;
		int score = 0;
	}

	public class SettingPageData extends CommPageData {
		boolean isSound = true;
		boolean isVibration = true;
		boolean isMusic = true;
	}

	public class RankingPageData extends CommPageData {

	}

	public class HelpPageData extends CommPageData {

	}

	public class AboutPageData extends CommPageData {

	}

	public class SelectLevelData extends CommPageData {

	}
	/**
	 * Keep game information and status for all page
	 * @author 
	 *
	 */
	public final class GameData {

		String dirPath;

		CommPageData currPage = null;
		ViewGroup currView = null; 

		public int gameStatus = GAME_NONE;

		private long teUpdateCount = 0;
		private long svUpdateCount = 0;

		SplashPageData splashPageData = new SplashPageData();
		MainPageData mainPageData = new MainPageData();
		ColorfulZumaPageData colorfulzumaPageData = new ColorfulZumaPageData();
		SettingPageData settingPageData = new SettingPageData();
		RankingPageData rankingPageData = new RankingPageData();
		HelpPageData helpPageData = new HelpPageData();
		AboutPageData aboutPageData = new AboutPageData();
		SelectLevelData selectLevelPageData = new SelectLevelData();

	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		//Set fullscreen
		Window win = getWindow();

		gameWidth = win.getWindowManager().getDefaultDisplay().getWidth();
		gameHeight = win.getWindowManager().getDefaultDisplay().getHeight();
		xScreenSize = (float)gameWidth / (float)xNormalSize;
		yScreenSize = (float)gameHeight / (float)yNormalSize;

		activity = this;

		// Init global field
		gameResources = getResources();
		gameDatabase = new RankingDatabase(this);

		path = activity.getFilesDir().getAbsolutePath();
		timeStamp = 0;

		// Start game
		mainHandler = new GameHandler();
		mainHandler.start();

		//vibration setting
		vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

		gameData = new GameData();
		gameData.dirPath = getFilesDir().getAbsolutePath() + "/";
		gameData.gameStatus = GAME_START;

		selectPage(R.layout.splash, true, null);
		GetState();

		// Start Service
		Intent intent = new Intent(this, FetchUploadService.class);
		startService(intent);
		// end
	}

	public void SaveState() {
		if(ZUMAniaGamePage.tempScore > ZUMAniaGamePage.maxScore) {
			ZUMAniaGamePage.maxScore = ZUMAniaGamePage.tempScore;
		}

		if (level_number > ZUMAniaGamePage.maxlevel) {
			ZUMAniaGamePage.maxlevel = level_number;
		}

		SharedPreferences  mPreferences = getSharedPreferences("shareInfo", 0);
		SharedPreferences.Editor editor = mPreferences.edit();
		editor.putInt("music_option", isCheckMusic);
		editor.putInt("sound_option", isCheckSound);
		editor.putInt("vibration_option", isCheckVibration);
		editor.putInt("maxScore", ZUMAniaGamePage.maxScore);
		editor.putInt("maxLevel", ZUMAniaGamePage.maxlevel);
		editor.commit();
	}

	public void GetState() {
		SharedPreferences mPreferences = getSharedPreferences("shareInfo", 0);
		isCheckMusic = mPreferences.getInt("music_option", 0);   	
		isCheckSound = mPreferences.getInt("sound_option", 0);
		isCheckVibration = mPreferences.getInt("vibration_option", 0);
		activity.max_level = ZUMAniaGamePage.maxlevel = mPreferences.getInt("maxLevel", 0);
		ZUMAniaGamePage.maxScore = mPreferences.getInt("maxScore", 0);
	}
	/**
	 * Game Event Dispatcher
	 * @author 
	 * 	 *
	 */
	public class GameHandler extends Handler {
	
	}

	protected synchronized void onPause() {
		if(gameData.currPage.pageId != R.layout.colorfulzuma){
			gameData.gameStatus = GAME_PAUSED;    		
		}

		if (gameData.currPage != null) {
			gameData.currPage.page.onPause();
		}

		super.onPause();
	}
	protected synchronized void onRestart() {
		if (gameData.currPage != null) {

		}

		super.onRestart();
	}

	@Override
	protected void onResume() {
		if(gameData.currPage.pageId != R.layout.colorfulzuma){
			gameData.gameStatus = GAME_START;    		
		}

		if (gameData.currPage != null) {
			gameData.currPage.page.onResume();
		}

		super.onResume();
	}
	/**
	 * Goto specified page
	 * @param pageId
	 */
	public synchronized void selectPage(int pageId, Boolean is_canceled, Object parm) {

		CommPageData gotoPageData = null;
		LayoutInflater factory = LayoutInflater.from(activity);

		if (gameData.currPage != null) {
			if (pageId != gameData.currPage.pageId) {

				// Unload page
				gameData.currPage.page.onPause();
				gameData.currPage.page.unloadPage();

				// Remove surface view
				gameData.currView.removeAllViews();

				// Drop user events
				mainHandler.removeAllMessages();
				mainHandler.addInitMessage(0);
			}

			if (gameData.currPage.prevPageId == pageId)
				is_canceled = true;
		}

		// Load view
		gameData.currView = (ViewGroup)factory.inflate(pageId, null);
		factory = null;

		// Load new page
		switch (pageId) {
		case R.layout.mainmenu:
			gotoPageData = gameData.mainPageData;
			gotoPageData.page = (MainPage)gameData.currView.findViewById(R.id.main_menu_page);
			break;
		case R.layout.colorfulzuma:
			gotoPageData = gameData.colorfulzumaPageData;
			gotoPageData.page = (ZUMAniaGamePage)gameData.currView.findViewById(R.id.zuma_page);
			break;
		case R.layout.setting:
			gotoPageData = gameData.settingPageData;
			gotoPageData.page = (SettingPage)gameData.currView.findViewById(R.id.setting_page);
			break;
		case R.layout.ranking:
			gotoPageData = gameData.rankingPageData;
			gotoPageData.page = (RankingPage) gameData.currView.findViewById(R.id.ranking_page); 
			break;
		case R.layout.help:
			gotoPageData = gameData.helpPageData;
			gotoPageData.page = (HelpPage) gameData.currView.findViewById(R.id.help_page);
			break;
		case R.layout.about:
			gotoPageData = gameData.aboutPageData;
			gotoPageData.page = (AboutPage) gameData.currView.findViewById(R.id.about_page);
			break;
		case R.layout.splash:
			gotoPageData = gameData.splashPageData;
			gotoPageData.page = (SplashPage)gameData.currView.findViewById(R.id.splash_page);
			break;
		case R.layout.selectlevel:
			gotoPageData = gameData.selectLevelPageData;
			gotoPageData.page = (SelectLevelPage) gameData.currView.findViewById(R.id.select_level_page);
			break;
		default:
			return;
		}

		// Set page layout id
		gotoPageData.pageId = pageId;

		// Set content view
		if (gotoPageData.page.getSurfaceViewRedrawListener() != null) {
			gameCanvas = new GameCanvas(this, null);

			gameData.currView.addView(gameCanvas);
		}
		setContentView(gameData.currView);

		gotoPageData.page.loadPage(parm, is_canceled);

		if (!is_canceled && gameData.currPage != null) {
			gotoPageData.prevPageId = gameData.currPage.pageId;
		}

		gotoPageData.page.onShow();
		gotoPageData.page.setVisibility(View.VISIBLE);

		// Reset page data
		gameData.currPage = gotoPageData;
		gameData.teUpdateCount = 0;
		gameData.svUpdateCount = 0;
	}

	protected void onDestroy() {
		if(isFinishing()){
			gameData.currPage.page.unloadPage();

			// Stop game
			gameData.gameStatus = GAME_QUIT;
			gameDatabase.finalize();
			destroyApp();
		}

		super.onDestroy();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	public void destroyApp() {
		SaveState();
		try{
			System.gc();
		}catch(Exception e){}
		finish();
	}
	/**
	 * Goto previous page
	 * @param pageId
	 */
	public void gotoPrevPage(Object parm) {
		int prevPageId = gameData.currPage.prevPageId;

		if (prevPageId != 0)
			selectPage(prevPageId, true, parm);
	}

	/**
	 * Global Event Listener
	 * Handle "Back" event 
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (gameData.currPage != null) {
			gameData.currPage.page.onKeyDown(keyCode, event);
		}

		if(keyCode == KeyEvent.KEYCODE_BACK){
			if (gameData.currPage.prevPageId == 0) {
				finish();
			} else {
				System.out.println("goto prev page");
				gotoPrevPage(null);    		
			}
		}
		return false;
	}
}
