package com.mytools.android.text;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

public class MyTextUtils {
	
	/**
	 * 获取需要绘制居中文字的y轴坐标，mPaint需要先设置好字体大小
	 * getFontBaseY
	 * @param height
	 * @param mPaint
	 * @return  
	 *float 
	 * @exception  
	 * @since  1.0.0
	 */
	public static float getFontBaseY(int height ,Paint mPaint){
		FontMetrics fontMetrics = mPaint.getFontMetrics(); 
		// 计算文字高度 
		float fontHeight = fontMetrics.bottom - fontMetrics.top; 
		// 计算文字baseline 
		return height - (height - fontHeight) / 2 - fontMetrics.bottom; 
		
	}
}
