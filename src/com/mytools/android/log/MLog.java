/**
 * Name:MLog.java
 * Created:2013-1-15
 * Author:peitao
 * 
 */


package com.mytools.android.log;

import android.util.Log;

public class MLog {
	private static final Boolean LOG_OUT = true;
	private static final int LENGTH = 5;

	public static int d(String TAG, String msg) {
		if (!LOG_OUT) {
			return -1;
		}
		return Log.d(TAG, msg);
	}

	public static int i(String TAG, String msg) {
		if (!LOG_OUT) {
			return -1;
		}
		return Log.i(TAG, msg);
	}

	public static int v(String TAG, String msg) {
		if (!LOG_OUT) {
			return -1;
		}
		return Log.v(TAG, msg);
	}

	public static int w(String TAG, String msg) {
		if (!LOG_OUT) {
			return -1;
		}
		return Log.w(TAG, msg);
	}

	public static int e(String TAG, String msg) {
		if (!LOG_OUT) {
			return -1;
		}
		return Log.e(TAG, msg);
	}

	public static int printMethodName(String TAG) {
		if (!LOG_OUT) {
			return -1;
		}
		String msg = "";
		StackTraceElement info = LogInfo.getInfoInternal(LENGTH);
		if (info != null) {
			msg = info.getMethodName() + " # Line " + info.getLineNumber();
		}
		
		return Log.i(TAG, msg);

	}

	public static int printStackTrace(String TAG) {
		if (!LOG_OUT) {
			return -1;
		}

		StackTraceElement[] stackTraceElements = new Exception().getStackTrace();
		
		if (stackTraceElements != null) {
			Log.d(TAG, "printStackTrace:");
			for (int i = 1; i < stackTraceElements.length; i++) {
				Log.d(TAG, stackTraceElements[i].toString());
			}
		}

		return 0;
	}

}
