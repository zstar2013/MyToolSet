package com.mytools.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * 版权所有 (c)2011, 卓跃计算机职业培训学校。
 * <p>
 * 文件名称 ：MyDateFormator.java
 * <p>
 * 内容摘要 ：用于转换系统时间的类
 * <p>
 * 作者 ：zx
 * <p>
 * 创建时间 ：2011-12-3 上午12:57:01
 * <p>
 * 当前版本号：v1.0
 * <p>
 * 历史记录 :
 * <p>
 * 日期 : 2011-12-3 上午12:57:01 修改人：
 * <p>
 * 描述 :
 * <p>
 */
public class MyDateFormator {

	/**
	 * 
	 *  函数名称 : getSystemDate<p>
	 *  功能描述 :  <p>
	 *  参数说明：<p>获取当前系统日期的字符串
	 *  	@return<p>
	 *  返回值：<p>
	 *  	String<p>
	 *  修改记录：<p>
	 *  日期：2011-12-3 上午1:09:08	修改人：zx<p>
	 *  描述	：<p>
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
	 *  函数名称 : getSystemTime<p>
	 *  功能描述 :  <p>
	 *  参数说明：<p>获取当前时间的字符串
	 *  	@return<p>
	 *  返回值：<p>
	 *  	String<p>
	 *  修改记录：<p>
	 *  日期：2011-12-3 上午1:09:34	修改人：zx<p>
	 *  描述	：<p>
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
