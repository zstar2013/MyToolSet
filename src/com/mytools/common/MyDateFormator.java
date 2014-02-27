package com.mytools.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * ��Ȩ���� (c)2011, ׿Ծ�����ְҵ��ѵѧУ��
 * <p>
 * �ļ����� ��MyDateFormator.java
 * <p>
 * ����ժҪ ������ת��ϵͳʱ�����
 * <p>
 * ���� ��zx
 * <p>
 * ����ʱ�� ��2011-12-3 ����12:57:01
 * <p>
 * ��ǰ�汾�ţ�v1.0
 * <p>
 * ��ʷ��¼ :
 * <p>
 * ���� : 2011-12-3 ����12:57:01 �޸��ˣ�
 * <p>
 * ���� :
 * <p>
 */
public class MyDateFormator {

	/**
	 * 
	 *  �������� : getSystemDate<p>
	 *  �������� :  <p>
	 *  ����˵����<p>��ȡ��ǰϵͳ���ڵ��ַ���
	 *  	@return<p>
	 *  ����ֵ��<p>
	 *  	String<p>
	 *  �޸ļ�¼��<p>
	 *  ���ڣ�2011-12-3 ����1:09:08	�޸��ˣ�zx<p>
	 *  ����	��<p>
	 *
	 */
	public static String getSystemDate() {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd");
		Date date = new Date();
		return bartDateFormat.format(date);
		 //System.out.println(bartDateFormat.format(date));

	}
	
	/**
	 * 
	 *  �������� : getSystemTime<p>
	 *  �������� :  <p>
	 *  ����˵����<p>��ȡ��ǰʱ����ַ���
	 *  	@return<p>
	 *  ����ֵ��<p>
	 *  	String<p>
	 *  �޸ļ�¼��<p>
	 *  ���ڣ�2011-12-3 ����1:09:34	�޸��ˣ�zx<p>
	 *  ����	��<p>
	 *
	 */
	public static String getSystemTime(){
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"HH:mm");
		Date date = new Date();
		return bartDateFormat.format(date);
	}
	
	public static String getTimeID(){
		SimpleDateFormat bartDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmm");
		Date date = new Date();
		return "pic"+bartDateFormat.format(date);

	}
}
