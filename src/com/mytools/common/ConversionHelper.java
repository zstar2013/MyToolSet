package com.mytools.common;

import java.util.List;

/**
 * ��������ת������Ĺ�����
 * @author Administrator
 *
 */

public class ConversionHelper {

	/*public static<T>  T[] ListToArray(){

		return null;
		
	}*/
	
	public static  String[] ListToArray(List<String> list){
		int length =list.size();
		String[] strings=new String[length];
		for(int i=0 ;i<length;i++){
			strings[i]=list.get(i);
		}
		return strings;
		
	}
	
	public static int getPosition (String str ,String[] strs){
		for(int i=0;i<strs.length;i++){
			if(str.endsWith(strs[i])){
				return i;
			}
		}
		return -1;
		
		
		
	}
}
