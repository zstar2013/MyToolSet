/**
 * AirMode.java
 * com.mytools.android
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2013-7-5 		Administrator
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
*/

package com.mytools.android;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

/**
 * 飞行模式的操作类
 * @since    Ver 1.1
 * @Date	 2013-7-5		下午6:48:21
 *
 * @see 	 
 */
public class AirModeHelper {
	
	private static final long atTimeInMillis=24*60*1000; 
	public static boolean IsAirModeOn(Context context) {
		return (Settings.System.getInt(context.getContentResolver(),
				Settings.Global.AIRPLANE_MODE_ON, 0) == 1 ? true : false);
	}
	
	public static void setAirplaneMode(Context context, boolean enabling) {
		Settings.System.putInt(context.getContentResolver(),
				Settings.Global.AIRPLANE_MODE_ON, enabling ? 1 : 0);
		Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
		intent.putExtra("state", enabling);
		context.sendBroadcast(intent);
	}
	
	
}

