package netcom.android.downloader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class MyProgressBar extends ProgressBar{

	/**
	 * 显示进度的文本
	 */
	private String text;
	/**
	 * 画笔类
	 */
	private Paint mPaint;
	/**
	 * 计算速度线程
	 */
	private Thread speedCountor;
	/**
	 * 发送给主页面信息
	 */
	private Handler mHandler;
	
	/**
	 * 是否运行
	 */
	private boolean isrun;
	
	
	public MyProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initText();
		//startSpeedCounter();
	
	}
	
	public MyProgressBar(Context context,Handler handler){
		super(context);
		initText();
		//startSpeedCounter();
	}
	
	public MyProgressBar(Context context, AttributeSet attrs, int defStyle){
		super(context,attrs,defStyle);
		initText();
		//startSpeedCounter();
	}
	
	@Override
	public synchronized void setProgress(int progress) {
		// TODO Auto-generated method stub
		//绘制进度
		setText(progress);
		
		super.setProgress(progress);
	}
	
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Rect rect=new Rect();
		this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
		int x=(getWidth()/2)-rect.centerX();
		int y=(getHeight()/2)-rect.centerY();
		canvas.drawText(this.text, x, y, mPaint);
	}
	
	
	
	private void setText(int progress) {
		int i=(int)((double)progress/this.getMax()*100);
		this.text=String.valueOf(i)+"%";
		
	}
	
	private void setText(){
		setText(this.getProgress());
	}

	/**
	 * 初始化，画笔
	 */
	private void initText() {
		this.mPaint=new Paint();
		this.mPaint.setColor(Color.WHITE);
		
	}
	
	
	
	

}
