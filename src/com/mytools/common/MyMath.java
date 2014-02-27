package com.mytools.common;


import java.math.BigDecimal;

import android.graphics.Point;



public class MyMath {
	/**
	 * �����ָ���Ƕȹ̶�Բ�ĺͰ뾶ʱ�������
	 * @param args
	 */
	public static PointDouble getXY(double inix,double iniy,double angle,int r) {
		int dd=1;
		/*if(angle>90)
		{
			dd=-1;
			angle=180-angle;
		}else
		{
			dd=1;
		}*/
			PointDouble point=new PointDouble();
			double x=dd(dd*Math.cos(Math.toRadians(angle))*r)+inix;
			double y=dd(Math.sin(Math.toRadians(angle))*r)+iniy;
			point.setX(dd(x));
			point.setY(dd(y));
			return point;
		
	}
	//�������벢����3λ
	public static double dd(double dout)
	{
		
        BigDecimal bd=new BigDecimal(dout); 
        bd=bd.setScale(3, BigDecimal.ROUND_HALF_UP); 
        return bd.doubleValue();
	}
	
	
	//�ж���Բ�Ƿ��ཻ
	public static boolean  IsOverlapped(int x1,int y1,int r1,int x2,int y2,int r2){
        //����Բ��֮��ľ����ƽ��
        int l = (x1 - x2)*(x1-x2) + (y1 - y2) * (y1 - y2);
        if(l < (r1 + r2) * (r1 + r2)){
               return true;
        }else{
               return false;
        }
 }
	//�жϵ��Ƿ���Բ��
	public static boolean isInCircul(int x,int y,int cx,int cy,int r){
		 
        int l = (x - cx)*(x-cx) + (y - cy) * (y - cy);
        if(l < r * r){
               return true;
        }else{
               return false;
        }
	}
	//�ж��Ƿ��ڷ�Χ��
	public static boolean isInArea(int x,int y,int dx,int dy,int cx,int cy,int r){
		if(cx+r<dx&&cy+r<dy&&cx-r>x&&cy-r>y){
			return true;
		}else{
			return false;
		}
	}
	
	
}
