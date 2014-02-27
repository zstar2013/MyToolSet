package com.mytools.android;


import java.lang.reflect.Field;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.KeyEvent;
import android.view.WindowManager;

public class MyDialogUtils {
	
	//用于跳转对话框
	private static String pageNumber = null;
	private static long startTimeOfJumpPage;// 用户按下数字键准备跳转页面的时间
	private static int waitingTimeOfJumpPage=1500;
	
	/**
	 * 显示dialog
	 * 
	 * @param context
	 *            环境
	 * @param strTitle
	 *            标题
	 * @param strText
	 *            内容
	 * @param icon
	 *            图标
	 */
	public static void showDialog(Activity context, String strTitle,
			String strText, int icon) {
		AlertDialog.Builder tDialog = new AlertDialog.Builder(context);
		tDialog.setIcon(icon);
		tDialog.setTitle(strTitle);
		tDialog.setMessage(strText);
		tDialog.setPositiveButton(android.R.string.ok, null);
		tDialog.show();
	}
	
	/**
	 * 显示进度条
	 * 
	 * @param context
	 *            环境
	 * @param title
	 *            标题
	 * @param message
	 *            信息
	 * @param indeterminate
	 *            确定性
	 * @param cancelable
	 *            可撤销
	 * @return
	 */
	public static ProgressDialog showProgress(Context context,
			CharSequence title, CharSequence message, boolean indeterminate,
			boolean cancelable) {
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setTitle(title);
		dialog.setMessage(message);
		dialog.setIndeterminate(indeterminate);
		dialog.setCancelable(false);
		// dialog.setDefaultButton(false);
		dialog.setOnCancelListener(new OnCancelListener() {
			
			public void onCancel(DialogInterface dialog) {
				dialog.dismiss();
				
			}
		});

		dialog.show();
		return dialog;
	}
	
	public static  void showPageJumpDialog(Context context,int firstCharOfPageNumber,final DialogCallBack callback){

		pageNumber = "" + firstCharOfPageNumber;
		startTimeOfJumpPage = System.currentTimeMillis();
		final String dlgTitle = "跳转到页面: ";
		final AlertDialog dialog = new AlertDialog(context) {
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() != KeyEvent.ACTION_DOWN)
					return false;
				startTimeOfJumpPage = System.currentTimeMillis();// 每次按下键时重设该时间
				if (keyCode < 7 || keyCode > 16)
					return false; // 7到16之间分别是按键0到9
				pageNumber = pageNumber + (keyCode - 7);
				setTitle(dlgTitle + pageNumber);

				return super.onKeyDown(keyCode, event);
			}
		};

		dialog.setTitle(dlgTitle + pageNumber);
		dialog.show();
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = 400;
		// params.height = 200;
		dialog.getWindow().setAttributes(params);
		dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				int page_num = 1;
				try {
					page_num = Integer.parseInt(pageNumber);
				} catch (Exception e) {
					return;
				}
				callback.jump2Page(page_num);
			}
		});

		new Thread() {
			@Override
			public void run() {
				while (System.currentTimeMillis() - startTimeOfJumpPage < waitingTimeOfJumpPage) {
					try {
						Thread.sleep(500);
					} catch (Exception e) {

					}
				}

				dialog.dismiss(); // 线程结束了，dialog也要关闭了
			}
		}.start();
	
	}
	public  interface DialogCallBack{
		public  void  jump2Page(int pageNum);
	}
	
	public static void setWaitingTime(int time){
		waitingTimeOfJumpPage=time;
	}
	
	//------------------------------自定义对话框----------------------------
	
	public static void showCustomDialog(Context context,CharSequence message,final MyDialogCallback callback){
		showCustomDialog(context,"" , message, callback);
	}
	
	
	public static void showCustomDialog(Context context,CharSequence title,CharSequence message,final MyDialogCallback callback){

		Builder builder=new Builder(context);
		builder.setTitle(title).setMessage(message).setNegativeButton(context.getResources().getString(android.R.string.cancel),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						callback.onCancelButtonClick(dialog);
					}
				}).setPositiveButton(context.getResources().getString(android.R.string.ok),
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						// deleteCache();
						callback.onConfirmButtonClick(dialog);
					}
				}).show();
	
	}
	
	
	
	
	public static interface MyDialogCallback{
		public void onCancelButtonClick(DialogInterface dialog);
		public void onConfirmButtonClick(DialogInterface dialog);
	}
	
	
	//--------------------------设置对话框点击后是否消失-----------------------------------
	
	private static void setDialogClosable(boolean b,DialogInterface dialog){
		try { 
	        //不关闭
	     Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing"); 
	        field.setAccessible(true); 
	        field.set(dialog, b); 
	        } catch (Exception e) {
	         e.printStackTrace();
	         }
	}
	

}
