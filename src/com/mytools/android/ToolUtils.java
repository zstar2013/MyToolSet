package com.mytools.android;

import android.content.Context;
import android.widget.Toast;

public class ToolUtils {

	public static void showToast(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	
	
}
