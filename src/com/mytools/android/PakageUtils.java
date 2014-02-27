package com.mytools.android;

import java.util.List;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.provider.Settings;

public class PakageUtils {
	private static List<ResolveInfo> mApps;

	public static boolean checkApkExist(Context context, String packageName){

		if (packageName == null || "".equals(packageName))
		return false;
		try {
		ApplicationInfo info = context.getPackageManager()
		.getApplicationInfo(packageName,
		PackageManager.GET_UNINSTALLED_PACKAGES);
		return true;
		} catch (NameNotFoundException e) {
		return false;
		}
		
	}
	
	public static void loadApps(Context mContext) {
	      Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	      mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);


	      mApps = mContext.getPackageManager().queryIntentActivities(mainIntent, 0);
	  }
	
	public static void startPackage(String packageName, String fullClassName,Context context){
		Intent intent=new Intent();
		intent.setComponent(new ComponentName(packageName,
				fullClassName));
		//intent = new Intent(Settings.ACTION_SETTINGS);//打开默认的Setting
		context.startActivity(intent);
	}
	
	
}
