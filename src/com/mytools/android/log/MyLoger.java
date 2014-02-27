package com.mytools.android.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.mytools.common.MyDateFormator;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class MyLoger {
	private static StringBuilder logContent = new StringBuilder();
	private static final String dividechar = "\t";
	private static final String line_feed = "\r\n";
	private static final String logPath = "/mnt/sdcard/";

	private static boolean isDebug = true;
	

	public static void i(String tag, String msg) {
		if (isDebug) {
			Log.i(tag, msg);
		}

		appendLog("i", tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isDebug) {
			Log.v(tag, msg);
		}
		appendLog("v", tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isDebug) {
			Log.e(tag, msg);
		}
		appendLog("e", tag, msg);
	}

	private static void appendLog(String type, String tag, String msg) {
		logContent.append(type);
		logContent.append(dividechar);
		logContent.append(MyDateFormator.getSystemDate());
		logContent.append(dividechar);
		logContent.append(MyDateFormator.getSystemTime());
		logContent.append(dividechar);
		logContent.append(tag);
		logContent.append(dividechar);
		logContent.append(msg);
		logContent.append(line_feed);
	}

	public static void close() {
		// writeToLocal();
		logContent = new StringBuilder();
	}

	private static void writeToLocal() {
		File file = new File(logPath + "appdownload-"
				+ System.currentTimeMillis() + ".txt");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			try {
				fos.write(logContent.toString().getBytes());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				fos = null;
			}
		}

	}
	
	public static void showToast(Context context,String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}
}
