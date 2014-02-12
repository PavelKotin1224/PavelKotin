package com.zumania;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.media.MediaPlayer;
import android.os.Vibrator;

public class Utils {

	public static String readFile(String path, String fileName) {
		
		String readingData = null;
		byte iobuffer[] = null;
		int idx = 0;
		int len;
		File file = new File(path, fileName);
		if (!file.exists()){
			return null;

		}
		
		iobuffer = new byte[(int) file.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			while ((len = fis.read(iobuffer, idx, iobuffer.length - idx)) > 0) {
				idx += len;
			}
		} catch (IOException ex) {
			return null;
		}

		if (fis != null) {
			try {
				fis.close();
			} catch (IOException ex) {
				return null;
			}
		}
		else{
			return null;
		}
		
		readingData = new String(iobuffer);
		
		return readingData;
	}
	

	public static boolean writeFile(String path, String fileName, String writingData) {
		File file = new File(path, fileName);
		try {
			if (!file.exists()){
				file.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		FileOutputStream fout = null;
		try {
			fout = new FileOutputStream(file);
			fout.write(writingData.getBytes());

		} catch (Exception e1) {
			e1.printStackTrace();
			return false;
		}
		try {
			if (fout != null)
				fout.close();
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public static boolean deleteFile(String path, String fileName) {
		File file = new File(path, fileName);
		if (file.exists()){
			return file.delete();
		}
		return true;
		
	}
	
	public static String[] split(String src, String key) {
		Vector<String> data = new Vector<String>();
		int index = 0;
		while ((index = src.indexOf(key))>= 0) {
			data.addElement(src.substring(0, index));
			src = src.substring(index + key.length());
		}
		if (src.length() >= 0)
			data.addElement(src);
		String[] tmplist = new String[data.size()];
		for (int i = 0; i < data.size(); i ++) {
			tmplist[i] = data.get(i);
		}
		return tmplist;
	}
	
	public static void startPlay(int isSound, MediaPlayer mediaPlayer, boolean isLoop){
    	if(mediaPlayer != null){
    		if(isSound == 1) {
    			mediaPlayer.setLooping(isLoop);
            	mediaPlayer.start();
    		}
    	}
    }
    /**
     * Stop Sound
     * @param mediaPlayer
     */   
    public static void stopPlay(MediaPlayer mediaPlayer){
		if(mediaPlayer != null){
			try {
				if(mediaPlayer.isPlaying()){
					mediaPlayer.stop();
				}
			} catch (Exception ex) {
			}
		}
    }
    
    /**
     * Release Sound
     * @param mediaPlayer
     */   
    public static void releasePlay(MediaPlayer mediaPlayer){
		if(mediaPlayer != null){
			try {
				if(mediaPlayer.isPlaying()){
					mediaPlayer.stop();
				}
			} catch (Exception ex) {
			}
			mediaPlayer.release();
			mediaPlayer = null;
		}
    }

    /**
     * Pause Sound
     * @param mediaPlayer
     */
    public static void pausePlay(MediaPlayer mediaPlayer){
    	if(mediaPlayer != null){
			if(mediaPlayer.isPlaying()){
	    		mediaPlayer.pause();
			}
    	}
    }
    
    /**
     * Vibration
     * @param mediaPlayer
     */
    public static void startVibration(int isVibration, Vibrator vibrator, int time){
    	if(vibrator != null){
			if(isVibration == 1){
				vibrator.vibrate(time);
			}
    	}
    }
    
    public static Vector<String> getXMLFromServer(String url) {
    	try {
			URL serverUrl = new URL(url);
			InputStream isText = serverUrl.openStream();
			String date = null;
			String message = null;
			Vector<String> versionInfo = new Vector<String>();
			
			if(isText != null) {
				XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
				XmlPullParser parser = parserCreator.newPullParser();
				parser.setInput(isText, null);
				
				int parserEvent = parser.getEventType();
				while (parserEvent != XmlPullParser.END_DOCUMENT) {
					
					if(parserEvent == XmlPullParser.START_TAG) {
						String tag = parser.getName();
						if(tag.equals("timestamp")) {
							parser.next();
							date = parser.getText();
						} else if(tag.equals("message")) {
							parser.next();
							message = parser.getText();
						}
					}
					parserEvent = parser.next();
				}
				
				isText.close();
				
				versionInfo.add(date);
				versionInfo.add(message);
				
				return versionInfo;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
    }
    
    public static Vector<String> getVersionFromServer(String url) {
    	
    	String clientTimeStampText = Utils.readFile(ZUMAnia.path, ZUMAnia.versionFilename); 
    	
    	if(clientTimeStampText == null){
			return null;
		} else {
			int serverTimeStamp = 0;
			String versionMessage = null;
			Vector<String> serverVersionInfo = getXMLFromServer(url);
			
			if(serverVersionInfo != null) {
				serverTimeStamp = Integer.parseInt(serverVersionInfo.get(0));		
				versionMessage = serverVersionInfo.get(1);
			} else {
				return null;
			}
			
			int clientTimeStamp = Integer.parseInt(clientTimeStampText);
			
			if(versionMessage != null && serverTimeStamp != 0 && !versionMessage.equals("0") && serverTimeStamp != clientTimeStamp) {

				Vector<String> versionInfo = new Vector<String>();

				versionInfo.add(serverTimeStamp + "");
		    	versionInfo.add(versionMessage);
		    	
		    	return versionInfo; 
			} else {
				return null;
			}
		}
    }
}
