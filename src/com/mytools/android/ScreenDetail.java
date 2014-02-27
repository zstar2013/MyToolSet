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
	 * ��ȡ��Ļ�Ĵ�С���ַ��� �������� : getScreenMetrics �������� : ����˵����
	 * 
	 * @param context
	 * @return ����ֵ�� String �޸ļ�¼�� ���ڣ�2012-7-2 ����9:55:07 �޸��ˣ����� ���� ��
	 */
	public static String getScreenMetrics(Context context) {
		// ��ȡ��Ļ��С
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
	 * ��ȡ״̬���ĸ߶� �������� : getStatusHeight �������� : ����˵����
	 * 
	 * @param context
	 * @return ����ֵ�� int �޸ļ�¼�� ���ڣ�2012-7-2 ����9:54:36 �޸��ˣ����� ���� ��
	 */
	public static int getStatusHeight(Context context) {
		Rect frame = new Rect();

		((Activity) context).getWindow().getDecorView()
				.getWindowVisibleDisplayFrame(frame);

		int statusBarHeight = frame.top;

		return statusBarHeight;

	}

	/**
	 * ��ȡ�������ĸ߶� �������� : getTitleHeight �������� : ����˵����
	 * 
	 * @param context
	 * @return ����ֵ�� int �޸ļ�¼�� ���ڣ�2012-7-2 ����9:54:05 �޸��ˣ����� ���� ��
	 */
	public static int getTitleHeight(Context context) {
		int contentTop = ((Activity) context).getWindow()
				.findViewById(Window.ID_ANDROID_CONTENT).getTop();

		// statusBarHeight�����������״̬���ĸ߶�
		int titleBarHeight = contentTop - getStatusHeight(context);
		return titleBarHeight;

	}

	private static Bitmap getpage;

	/**
	 * ��ȡ��ǰҳ��Ľ�ͼ
	 * 
	 * @param activity
	 * @return
	 */
	public static Bitmap getScreenBitmap(Context context) {
		getpage = Bitmap.createBitmap(getScreenWidthPx(context),
				getScreenHeightPx(context), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(getpage);
		canvas.drawColor(Color.LTGRAY);// ������Խ����κλ�ͼ����
		canvas.save(Canvas.ALL_SAVE_FLAG);
		canvas.restore();
		return getpage;

	}

}
