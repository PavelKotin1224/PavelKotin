package com.zumania;

import java.util.Vector;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RankingDatabase {

	ZUMAnia activity;

	private static SQLiteDatabase mDatabase;

	private final static String db_name = "spidercraze.db";
	private final static String db_integer_type = "INTEGER";
	private final static String db_text_type = "TEXT";
	public final static int MAX_PLAYER_COUNT = 5;

	// score_history table
	private final static String db_tmh = "tbl_score_history";
	private final static String db_tmh_create_sql = "CREATE TABLE %s ("
			+ "%s %s PRIMARY KEY AUTOINCREMENT," + "%s %s, %s %s);";

	private final static String db_tmh_fid_name = "id";
	private final static String db_tmh_fplayer = "player";
	private final static String db_tmh_fscore = "score";


	public RankingDatabase(Activity act) {
		activity = (ZUMAnia) act;

		try {
			// Open or create database
			mDatabase = activity.openOrCreateDatabase(db_name,
					SQLiteDatabase.CREATE_IF_NECESSARY
							| SQLiteDatabase.OPEN_READWRITE, null);
		} catch (Exception e) {
		}

		safeBuildDatabase();
	}

	/**
	 * Build default score database
	 */
	private void safeBuildDatabase() {
		String scoreHistory;

		// Create score_history table
		scoreHistory = String.format(db_tmh_create_sql, db_tmh,
				db_tmh_fid_name, db_integer_type, db_tmh_fplayer, db_text_type,
				db_tmh_fscore, db_integer_type);
		try {
			mDatabase.execSQL(scoreHistory);
		} catch (SQLException se) {

		}
	}

	/**
	 * Add score history item
	 */
	public boolean addScoreHistory(String player, int score) {

		if (!compareScore(score)) {
			return false;
		}

		ContentValues values = new ContentValues();
		long result;
		
		values.put(db_tmh_fplayer, player);
		values.put(db_tmh_fscore, score);

		result = mDatabase.insert(db_tmh, null, values);
		values.clear();
		values = null;

		if (result == -1)
			return false;

		return true;
	}

	/**
	 * Delete all items
	 */
	public boolean deleteAllHistory() {
		int result;

		result = mDatabase.delete(db_tmh, null, null);

		if (result == -1)
			return false;

		return true;
	}

	/**
	 * Get score history list Attention: You must free c object after working
	 */
	public boolean compareScore(int score) {

		Cursor c;
		c = mDatabase.query(true, db_tmh, null, null, null, null, null,
				db_tmh_fscore, null);

		c.moveToFirst();

		if (c.getCount() == 0) {
			c.close();
			return true;
		}
		if (c.getCount() >= MAX_PLAYER_COUNT) {
			if (score <= c.getInt(2)) {
				c.close();
				return false;
			} else {
				mDatabase.delete(db_tmh,db_tmh_fscore+ " = ? and " + db_tmh_fplayer + " = ?",
						new String[] { c.getString(2), c.getString(1) });
			}
		}
		c.close();
		return true;
	}

	public Vector<String> getScoreList() {

		Vector<String> receivers = new Vector<String>();
		Cursor c;
		
		c = mDatabase.query(true, db_tmh, null, null, null, null, null,
				db_tmh_fscore, null);

		try {
			c.moveToLast();
			for (int i = 0; i < MAX_PLAYER_COUNT; i++) {
				receivers.add(c.getString(1));
				receivers.add(c.getString(2));
				c.moveToPrevious();
			}
		} catch (Exception e) {

		}
		
		c.close();
		c = null;

		return receivers;
	}

	protected void finalize() {
		mDatabase.close();
	}
}
