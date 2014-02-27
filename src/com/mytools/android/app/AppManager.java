package com.mytools.android.app;

import java.util.List;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;

public class AppManager {

	static ActivityManager mActivityManager;
	
	/**
	 * check app is running?
	 *
	 * @author HeJQ
	 * @param appname
	 * @return boolean
	 * @since 20120323
	 */
	private static boolean getRunningAppProcessInfo(Context context,String appname) {
		mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningAppProcessInfo> appProcessList = mActivityManager
				.getRunningAppProcesses();

		for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessList) {

			// int pid = appProcessInfo.pid;
			// int uid = appProcessInfo.uid;
			String processName = appProcessInfo.processName;
			if (processName.equals(appname)) {
				return true;
			}
			// int[] myMempid = new int[] { pid };
			// String[] packageList = appProcessInfo.pkgList;
		}
		return false;
	}
	
	
	/**
	 * 使用包名启动app
	 * RunApp
	 * @param context
	 * @param packageName  
	 *void 
	 * @exception  
	 * @since  1.0.0
	 */
	private void RunApp(Context context,String packageName) {  
        PackageInfo pi;  
        try {  
            pi = context.getPackageManager().getPackageInfo(packageName, 0);  
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);  
            // resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);   
            resolveIntent.setPackage(pi.packageName);  
            PackageManager pManager = context.getPackageManager();  
            List<ResolveInfo> apps = pManager.queryIntentActivities(  
                    resolveIntent, 0);  
  
            ResolveInfo ri = apps.iterator().next();  
            if (ri != null) {  
                packageName = ri.activityInfo.packageName;  
                String className = ri.activityInfo.name;  
  
                Intent intent = new Intent(Intent.ACTION_MAIN);  
                // intent.addCategory(Intent.CATEGORY_LAUNCHER);   
  
                ComponentName cn = new ComponentName(packageName, className);  
  
                intent.setComponent(cn);  
                context.startActivity(intent);  
            }  
        } catch (NameNotFoundException e) {  
            // TODO Auto-generated catch block   
            e.printStackTrace();  
        }  
  
    }  
}
