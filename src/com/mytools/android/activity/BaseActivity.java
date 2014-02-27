package com.mytools.android.activity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 *  
 * BaseActivity 
 *  
 * zx 
 * zx 
 * 2012-8-10 下午3:26:27 
 *  
 * @version 1.0.0 
 *
 */
public abstract class BaseActivity extends Activity {

	/**
	 * 判断是否全屏
	 */
	protected boolean isFullScreen = false;
	protected boolean isLandscape = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initConf();
		setContentView(getContentViewID());
		findViews();
		setLayoutPara();
		setListeners();
	}

	protected boolean isFullScreen() {
		return isFullScreen;
	}

	protected boolean isLandscape() {
		return isLandscape;
	}

	private void initConf() {
		isFullScreen = isFullScreen();
		isLandscape = isLandscape();
		if (isLandscape) {
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		if (isFullScreen) {
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

	/**
	 * 
	 * getContentViewID(获取contentviewID) 
	 * @return  
	 *int 
	 * @exception  
	 * @since  1.0.0
	 */
	protected abstract int getContentViewID();

	public abstract void findViews();

	public abstract void setLayoutPara();

	public abstract void setListeners();

}
