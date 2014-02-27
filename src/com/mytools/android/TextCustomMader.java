package com.mytools.android;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

public class TextCustomMader {
	/**
	 * 默认的字体大小
	 */
	private final static int DEFAULT_TEXTSIZE=10;
	/**
	 * 默认的渲染区间
	 */
	private final static int DEFAULT_SPANEXTENT=Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
	/**
	 * 默认的字体风格
	 */
	private final static int DEFAULT_SPANSTYLE=Typeface.NORMAL;
	
	
	public static SpannableStringBuilder makeSpan(String text,int start_index,int end_index){
		return makeSpan(text, DEFAULT_TEXTSIZE,start_index, end_index,DEFAULT_SPANEXTENT);
	}
	
	public static SpannableStringBuilder makeSpan(String text,int textSize,int start_index,int end_index,int SPANEXTENT){
		SpannableStringBuilder content=new SpannableStringBuilder();
		content.append(text);
		content.setSpan(new AbsoluteSizeSpan(textSize), start_index, end_index,
				SPANEXTENT);
		
		return content;
	}
	
	/**
	 * 对传入的字符串进行分割，并将文本和标题按两种不同的分割展现
	 * @param text
	 * @param delimiter
	 * @param titleSize
	 * @param cotentSize
	 * @return
	 */
	public static SpannableStringBuilder makeSpan(String text,String delimiter,int titleSize,int cotentSize){
		
		
		return new SpannableStringBuilder();
	}

}
