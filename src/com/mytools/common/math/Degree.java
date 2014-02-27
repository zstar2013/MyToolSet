package com.mytools.common.math;

import android.graphics.Point;

public class Degree {
	private static double getAngle(Point pO,Point pA,Point pB){
		double ma_x=pA.x-pO.x;
		double ma_y=pA.y-pO.y;
		double mb_x=pB.x-pO.x;
		double mb_y=pB.y-pO.y;
		double v1=(ma_x*mb_x)+(ma_y*mb_y);
		double ma_val=Math.sqrt(ma_x*ma_x+ma_y*ma_y);
		double mb_val=Math.sqrt(mb_x*mb_x+mb_y*mb_y);
		double cosM=v1/(ma_val*mb_val);
		double angleAMB=Math.acos(cosM)*180/Math.PI;
		return angleAMB;
	}
}
