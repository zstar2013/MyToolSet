package com.mytools.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Window;

public class ScreenDetail {


	public static boolean isScreenPortrait(Context context) {
		Configuration cf = context.getResources().getConfiguration();
		int ori = cf.orientation;
		if (ori == Configuration.ORIENTATION_PORTRAIT) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 获取屏幕的大小的字符串 函数名称 : getScreenMetrics 功能描述 : 参数说明：
	 * 
	 * @param context
	 * @return 返回值： String 修改记录： 日期：2012-7-2 上午9:55:07 修改人：张鑫 描述 ：
	 */
	public static String getScreenMetrics(Context context) {
		// 获取屏幕大小
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
		.getMetrics(dm);
		return dm.widthPixels + "*" + dm.heightPixels;
	}


	public static double getScreenWidth(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		double dtp = context.getResources().getDisplayMetrics().density;
		return dm.widthPixels / dtp;
	}

	public static int getScreenWidthPx(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.widthPixels;
	}

	public static int getScreenHeightPx(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		return dm.heightPixels;
	}

	public static int dp2Px(Context context, double dp) {
		double dtp = context.getResources().getDisplayMetrics().density;
		int value = (int) (dp * dtp);
		return value;

	}

	public static float px2Dp(Context context, int px) {
		double dtp = context.getResources().getDisplayMetrics().density;
		float value = (float) (px * 1F / dtp);
		return value;
	}

	/**
	 * 获取状态栏的高度 函数名称 : getStatusHeight 功能描述 : 参数说明：
	 * 
	 * @param context
	 * @return 返回值： int 修改记录： 日期：2012-7-2 上午9:54:36 修改人：张鑫 描述 ：
	 */
	public static int getStatusHeight(Context context) {
		Rect frame = new Rect();

		((Activity) context).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);

		int statusBarHeight = frame.top;

		return statusBarHeight;

	}

	/**
	 * 获取标题栏的高度 函数名称 : getTitleHeight 功能描述 : 参数说明：
	 * 
	 * @param context
	 * @return 返回值： int 修改记录： 日期：2012-7-2 上午9:54:05 修改人：张鑫 描述 ：
	 */
	public static int getTitleHeight(Context context) {
		int contentTop = ((Activity) context).getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();

		// statusBarHeight是上面所求的状态栏的高度
		int titleBarHeight = contentTop - getStatusHeight(context);
		return titleBarHeight;

	}

	private static Bitmap getpage;

	/**
	 * 获取当前页面的截图
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap getScreenBitmap(Context context) {
		getpage = Bitmap.createBitmap(getScreenWidthPx(context),
				getScreenHeightPx(context), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(getpage);
		canvas.drawColor(Color.LTGRAY);// 这里可以进行任何绘图步骤
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return getpage;

	}

}
