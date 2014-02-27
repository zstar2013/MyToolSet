package com.mytools.android.actions;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

public class MySDcardReceiver extends BroadcastReceiver {
	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			
		};
	};

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		

		if (action.equals(Intent.ACTION_MEDIA_EJECT)) {
			// TODO:
			System.out.println("-------------------> mount ACTION_MEDIA_EJECT");

		} else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
			// TODO:
			System.out
					.println("-------------------> mount ACTION_MEDIA_MOUNTED");

		}
	}
	
	class ScanTask implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
