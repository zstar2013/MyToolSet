package com.mytools.android.text;

import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;

public class FontUtils {

	public static int getFontHeight(float fontSize)  
	{  
	    Paint paint = new Paint();  
	    paint.setTextSize(fontSize);  
	    FontMetrics fm = paint.getFontMetrics();  
	    return (int) Math.ceil(fm.descent - fm.top) + 2;  
	}  
}
