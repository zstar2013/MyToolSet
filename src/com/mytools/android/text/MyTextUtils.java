package com.mytools.android.text;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

public class MyTextUtils {
	
	/**
	 * ��ȡ��Ҫ���ƾ������ֵ�y�����꣬mPaint��Ҫ�����ú������С
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
		// �������ָ߶� 
		float fontHeight = fontMetrics.bottom - fontMetrics.top; 
		// ��������baseline 
		return height - (height - fontHeight) / 2 - fontMetrics.bottom; 
		
	}
}
