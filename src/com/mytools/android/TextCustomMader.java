package com.mytools.android;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;

public class TextCustomMader {
	/**
	 * Ĭ�ϵ������С
	 */
	private final static int DEFAULT_TEXTSIZE=10;
	/**
	 * Ĭ�ϵ���Ⱦ����
	 */
	private final static int DEFAULT_SPANEXTENT=Spannable.SPAN_EXCLUSIVE_EXCLUSIVE;
	/**
	 * Ĭ�ϵ�������
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
	 * �Դ�����ַ������зָ�����ı��ͱ��ⰴ���ֲ�ͬ�ķָ�չ��
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
