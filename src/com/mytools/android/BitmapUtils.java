package com.mytools.android;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.TypedValue;

public class BitmapUtils {
	
	/**
	 * 对图片进行递归缩放
	 * @param inTempStorage
	 */
	public void try_catch_OutOfMemoryError(Context context,int inTempStorage,Bitmap bmp,int resID)
	{
		System.out.println("inTempStorage:"+inTempStorage);
		//释放图片占用的内存
		if(bmp!=null&&!bmp.isRecycled())
		{
			bmp.recycle();
		}
		//对图片缩放设置
		BitmapFactory.Options options = new BitmapFactory.Options();
		//捕捉OutOfMemoryError，进行递归调用缩放
		try
		{
			//inTempStorage缩放倍数，越大越不清晰，1为原图，2为原图的1/2
			options.inSampleSize = inTempStorage;
			bmp=BitmapFactory.decodeResource(context.getResources(), resID,options);
		}catch (OutOfMemoryError ee) {
			//出现内存溢出，调整缩放倍数，递归调用
				inTempStorage++;
			try_catch_OutOfMemoryError(context,inTempStorage,bmp,resID);
		}
	}
	/**
	 * 1.放大缩小图片
	 * 
	 * @param bitmap
	 * @param w
	 * @param h
	 * @return
	 */ 
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) { 
	    int width = bitmap.getWidth(); 
	    int height = bitmap.getHeight(); 
	    Matrix matrix = new Matrix(); 
	    float scaleWidht = ((float) w / width); 
	    float scaleHeight = ((float) h / height); 
	    matrix.postScale(scaleWidht, scaleHeight); 
	    Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, 
	            matrix, true); 
	    return newbmp; 
	} 
	

	 
	/**
	 * 2.获得圆角图片的方法
	 * 
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */ 
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) { 
	    Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap 
	            .getHeight(), Config.ARGB_8888); 
	    Canvas canvas = new Canvas(output); 
	    final int color = 0xff424242; 
	    final Paint paint = new Paint(); 
	    final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()); 
	    final RectF rectF = new RectF(rect); 
	    paint.setAntiAlias(true); 
	    canvas.drawARGB(0, 0, 0, 0); 
	    paint.setColor(color); 
	    canvas.drawRoundRect(rectF, roundPx, roundPx, paint); 
	    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN)); 
	    canvas.drawBitmap(bitmap, rect, rect, paint); 
	    return output; 
	} 
	 
	/**
	 * 3.获得带倒影的图片方法
	 * 
	 * @param bitmap
	 * @return
	 */ 
	public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) { 
	    final int reflectionGap = 4; 
	    int width = bitmap.getWidth(); 
	    int height = bitmap.getHeight(); 
	    Matrix matrix = new Matrix(); 
	    matrix.preScale(1, -1); 
	    //生成反射图片，截取原图从二分之一高度开始二分之一高的的图片，并翻转。
	    Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, 
	            width, height / 2, matrix, false); 
	    Bitmap bitmapWithReflection = Bitmap.createBitmap(width, 
	            (height + height / 2), Config.ARGB_8888); 
	    Canvas canvas = new Canvas(bitmapWithReflection); 
	    canvas.drawBitmap(bitmap, 0, 0, null); 
	    Paint deafalutPaint = new Paint(); 
	    canvas 
	            .drawRect(0, height, width, height + reflectionGap, 
	                    deafalutPaint); 
	    canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null); 
	    Paint paint = new Paint(); 
	    LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0, 
	            bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 
	            0x00ffffff, TileMode.CLAMP); 
	    paint.setShader(shader); 
	    // Set the Transfer mode to be porter duff and destination in 
	    paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
	    // Draw a rectangle using the paint with our linear gradient 
	    canvas.drawRect(0, height, width, bitmapWithReflection.getHeight() 
	            + reflectionGap, paint); 
	    return bitmapWithReflection; 
	} 
	 
	/**
	 * 4.将Drawable转化为Bitmap
	 * 
	 * @param drawable
	 * @return
	 */ 
	public static Bitmap drawableToBitmap(Drawable drawable) { 
	    int width = drawable.getIntrinsicWidth(); 
	    int height = drawable.getIntrinsicHeight(); 
	    Bitmap bitmap = Bitmap.createBitmap(width, height, drawable 
	            .getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 
	            : Bitmap.Config.RGB_565); 
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, width, height); 
	    drawable.draw(canvas); 
	    return bitmap; 
	} 
	
	/**
	 * 读取图片资源
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public Bitmap ReadBitMap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.ARGB_8888;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
		//可以用此句代替？
		/*BitmapFactory.decodeResource(context.getResources(),
		resId);*/
	}
	
	/**
	 * 获取指定地址的图片资源
	 * @param url
	 * @return
	 */
	public static Bitmap loadBitmapFromUrl(String url){
		Log.v("sss", url);
		Bitmap bitmap=null;
		try {
			URL myFileUrl = new URL(url);
			HttpURLConnection conn= (HttpURLConnection) myFileUrl
					.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(60000);
			conn.setReadTimeout(60000);
			conn.connect();
			bitmap=loadBitmapFromUrl(conn);
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return bitmap;
	}
	
	/**
	 * 获取指定url的图片资源
	 * @param httpConn
	 * @return
	 */
	public static Bitmap loadBitmapFromUrl(HttpURLConnection httpConn){
		Bitmap bitmap = null;
		InputStream is = null;
		try {
			is = httpConn.getInputStream();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
			
		}  
		if (is == null){  
			//return null;
		    throw new RuntimeException("stream is null");  
		}else{  
		    try {  
		        byte[] data=readStream(is);  
		        if(data!=null){  
		            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);  
		        }  
		    } catch (Exception e) {  
		        e.printStackTrace();  
		    }  
		      
		    try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}  
		return bitmap;
	}
	
	/**
	 * 得到图片字节流 数组   
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
    public static byte[] readStream(InputStream inStream) throws Exception{        
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();        
        byte[] buffer = new byte[1024];        
        int len = 0;        
        while( (len=inStream.read(buffer)) != -1){        
            outStream.write(buffer, 0, len);        
        }        
        outStream.close();        
        inStream.close();        
        return outStream.toByteArray();        
    }  
	
    /**
     * 获取指定饱和度的图片
     * @param bitmap
     * @param progress
     * @return
     */
    private static Bitmap getBitmapAtSaturation(Bitmap bitmap,int progress){
		 Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),  
                Config.ARGB_8888);  
        ColorMatrix cMatrix = new ColorMatrix();  
        // 设置饱和度   
        cMatrix.setSaturation((float) (progress / 100.0));  

        Paint paint = new Paint();  
        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));  

        Canvas canvas = new Canvas(bmp);  
        // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了   
        canvas.drawBitmap(bitmap, 0, 0, paint);  

        return bmp;
	}
	
    /**
     * 获得指定亮度的图片
     * @param bitmap
     * @param progress
     * @return
     */
	private Bitmap getBitmapAtBrightness(Bitmap bitmap,int progress){
		 Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),  
               Config.ARGB_8888);  
       int brightness = progress - 127;  
       ColorMatrix cMatrix = new ColorMatrix();  
       cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1,  
               0, 0, brightness,// 改变亮度   
               0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });  

       Paint paint = new Paint();  
       paint.setColorFilter(new ColorMatrixColorFilter(cMatrix)); 

       Canvas canvas = new Canvas(bmp);  
       // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了   
       canvas.drawBitmap(bitmap, 0, 0, paint);  

       return bmp;
	}
	
	/**
	 * 获取指定对比度的图片
	 * @param bitmap
	 * @param progress
	 * @return
	 */
	private Bitmap getBitmapAtContrast(Bitmap bitmap,int progress){
		 Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),  
               Config.ARGB_8888);  
		 // int brightness = progress - 127;   
         float contrast = (float) ((progress + 64) / 128.0);  
         ColorMatrix cMatrix = new ColorMatrix();  
         cMatrix.set(new float[] { contrast, 0, 0, 0, 0, 0,  
                 contrast, 0, 0, 0,// 改变对比度   
                 0, 0, contrast, 0, 0, 0, 0, 0, 1, 0 });  

         Paint paint = new Paint();  
         paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));  

       Canvas canvas = new Canvas(bmp);  
       // 在Canvas上绘制一个已经存在的Bitmap。这样，dstBitmap就和srcBitmap一摸一样了   
       canvas.drawBitmap(bitmap, 0, 0, paint);  

       return bmp;
	}
	
	/**
	 * 调整图片的旋转角度
	 * @param bm
	 * @param orientationDegree
	 * @return
	 */
	public static Bitmap adjustPhotoRotation(Bitmap bm,
			final int orientationDegree) {

		Matrix m = new Matrix();
		m.setTranslate((float) bm.getWidth() / 2, (float) bm.getHeight() / 2); // 设置图片的旋转中心，即绕（X,Y）这点进行中心旋转
		m.preRotate(orientationDegree, (float) bm.getWidth() / 2,
				(float) bm.getHeight() / 2); // 要旋转的角度
		m.postTranslate(-(float) bm.getWidth() / 2, -(float) bm.getHeight() / 2);
		Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(),
				Bitmap.Config.ARGB_8888);

		Paint paint = new Paint();
		Canvas canvas = new Canvas(bm1);
		canvas.drawBitmap(bm, m, paint);

		return bm1;
	}

	
	/**
	 * 根据不同的dpi获得相应分辨率的图片
	 * @param resources
	 * @param id
	 * @return
	 */
	public static Bitmap decodeResource(Resources resources, int id) {
		TypedValue value = new TypedValue();
		resources.openRawResource(id, value);
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inTargetDensity = value.density;
		return BitmapFactory.decodeResource(resources, id, opts);
		}
	
	
}
