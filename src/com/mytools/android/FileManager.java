package com.mytools.android;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.mytoolset.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class FileManager 
{
	private File				currentDirectory = new File("/");
	private File 				myTmpFile 		 = null;
	private int 				myTmpOpt		 = -1;
	private Context mContext;

	
	public FileManager(Context context) {
		mContext=context;
		browseToRoot();
		
	}

	//浏览文件系统的根目录
	private void browseToRoot() 
	{
		browseTo(new File("/"));
    }
	//返回上一级目录
	private void upOneLevel()
	{
		if(this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
	}
	//浏览指定的目录,如果是文件则进行打开操作
	private void browseTo(final File file)
	{
		if (file.isDirectory())
		{
			this.currentDirectory = file;
		}
		else
		{
			fileOptMenu(file);
		}
	}
	//打开指定文件
	protected void openFile(File aFile)
	{
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(aFile.getAbsolutePath());
		// 取得文件名
		String fileName = file.getName();
		// 根据不同的文件类型来打开文件
		if (checkEndsWithInStringArray(fileName, mContext.getResources().getStringArray(R.array.fileEndingImage)))
		{
			intent.setDataAndType(Uri.fromFile(file), "image/*");
		}
		else if (checkEndsWithInStringArray(fileName, mContext.getResources().getStringArray(R.array.fileEndingAudio)))
		{
			intent.setDataAndType(Uri.fromFile(file), "audio/*");
		}
		else if (checkEndsWithInStringArray(fileName, mContext.getResources().getStringArray(R.array.fileEndingVideo)))
		{
			intent.setDataAndType(Uri.fromFile(file), "video/*");
		}
		mContext.startActivity(intent);
	}
	
	//通过文件名判断是什么类型的文件
	private boolean checkEndsWithInStringArray(String checkItsEnd, 
					String[] fileEndings)
	{
		for(String aEnd : fileEndings)
		{
			if(checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
	
	//粘贴操作
	public void MyPaste()
	{
		if ( myTmpFile == null )
		{
			Builder builder = new Builder(mContext);
			builder.setTitle("提示");
			builder.setMessage("没有复制或剪切操作");
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();	
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
		}
		else
		{
			if ( myTmpOpt == 0 )//复制操作
			{
				if(new File(GetCurDirectory()+"/"+myTmpFile.getName()).exists())
				{
					Builder builder = new Builder(mContext);
					builder.setTitle("粘贴提示");
					builder.setMessage("该目录有相同的文件，是否需要覆盖？");
					builder.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									copyFile(myTmpFile,new File(GetCurDirectory()+"/"+myTmpFile.getName()));
									browseTo(new File(GetCurDirectory()));
								}
							});
					builder.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					builder.setCancelable(false);
					builder.create();
					builder.show();
				}	
				else
				{
					copyFile(myTmpFile,new File(GetCurDirectory()+"/"+myTmpFile.getName()));
					browseTo(new File(GetCurDirectory()));
				}
			}
			else if(myTmpOpt == 1)//粘贴操作
			{
				if(new File(GetCurDirectory()+"/"+myTmpFile.getName()).exists())
				{
					Builder builder = new Builder(mContext);
					builder.setTitle("粘贴提示");
					builder.setMessage("该目录有相同的文件，是否需要覆盖？");
					builder.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									moveFile(myTmpFile.getAbsolutePath(),GetCurDirectory()+"/"+myTmpFile.getName());
									browseTo(new File(GetCurDirectory()));
								}
							});
					builder.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					builder.setCancelable(false);
					builder.create();
					builder.show();
				}	
				else
				{
					moveFile(myTmpFile.getAbsolutePath(),GetCurDirectory()+"/"+myTmpFile.getName());
					browseTo(new File(GetCurDirectory()));	
				}
			}
		}
	}
	//删除整个文件夹
	public void MyDelete()
	{
		//取得当前目录
		File tmp=new File(this.currentDirectory.getAbsolutePath());
		//跳到上一级目录
		this.upOneLevel();
		//删除取得的目录
		if ( deleteFolder(tmp) )
		{
			Builder builder = new Builder(mContext);
			builder.setTitle("提示");
			builder.setMessage("删除成功");
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();	
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
		}
		else 
		{
			Builder builder = new Builder(mContext);
			builder.setTitle("提示");
			builder.setMessage("删除失败");
			builder.setPositiveButton(android.R.string.ok,
					new AlertDialog.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					});
			builder.setCancelable(false);
			builder.create();
			builder.show();
		}
		this.browseTo(this.currentDirectory);	
	}
	//新建文件夹
	public void Mynew()
	{
		final LayoutInflater factory = LayoutInflater.from(mContext);
		final View dialogview = factory.inflate(R.layout.dialog, null);
		//设置TextView
		((TextView) dialogview.findViewById(R.id.TextView_PROM)).setText("请输入新建文件夹的名称！");
		//设置EditText
		((EditText) dialogview.findViewById(R.id.EditText_PROM)).setText("文件夹名称...");
		
		Builder builder = new Builder(mContext);
		builder.setTitle("新建文件夹");
		builder.setView(dialogview);
		builder.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String value = ((EditText) dialogview.findViewById(R.id.EditText_PROM)).getText().toString();
						if ( newFolder(value) )
						{
							Builder builder = new Builder(mContext);
							builder.setTitle("提示");
							builder.setMessage("新建文件夹成功");
							builder.setPositiveButton(android.R.string.ok,
									new AlertDialog.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											//点击确定按钮之后,继续执行网页中的操作
											dialog.cancel();
										}
									});
							builder.setCancelable(false);
							builder.create();
							builder.show();
						}
						else
						{
							Builder builder = new Builder(mContext);
							builder.setTitle("提示");
							builder.setMessage("新建文件夹失败");
							builder.setPositiveButton(android.R.string.ok,
									new AlertDialog.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											//点击确定按钮之后,继续执行网页中的操作
											dialog.cancel();
										}
									});
							builder.setCancelable(false);
							builder.create();
							builder.show();	
						}
					}
				});
		builder.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});
		builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
					public void onCancel(DialogInterface dialog) {
						dialog.cancel();
					}
				});
		builder.show();
	}
	//新建文件夹
	public boolean newFolder(String file)
	{
		File dirFile = new File(this.currentDirectory.getAbsolutePath()+"/"+file);
		try
		{
			if (!(dirFile.exists()) && !(dirFile.isDirectory()))
			{
				boolean creadok = dirFile.mkdirs();
				if (creadok)
				{
					this.browseTo(this.currentDirectory);
					return true;
				}
				else
				{
					return false;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println(e);
			return false;
		}
		return true;
	}
	//删除文件
    public boolean deleteFile(File file)
	{
		boolean result = false;
		if (file != null)
		{
			try
			{
				File file2 = file;
				file2.delete();
				result = true;
			}
			catch (Exception e)
			{
				e.printStackTrace();
				result = false;
			}
		}
		return result;
	} 
    //删除文件夹
	public boolean deleteFolder(File folder)
	{
		boolean result = false;
		try
		{
			String childs[] = folder.list();
			if (childs == null || childs.length <= 0)
			{
				if (folder.delete())
				{
					result = true;
				}
			}
			else
			{
				for (int i = 0; i < childs.length; i++)
				{
					String childName = childs[i];
					String childPath = folder.getPath() + File.separator + childName;
					File filePath = new File(childPath);
					if (filePath.exists() && filePath.isFile())
					{
						if (filePath.delete())
						{
							result = true;
						}
						else
						{
							result = false;
							break;
						}
					}
					else if (filePath.exists() && filePath.isDirectory())
					{
						if (deleteFolder(filePath))
						{
							result = true;
						}
						else
						{
							result = false;
							break;
						}
					}
				}
				folder.delete();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			result = false;
		}
		return result;
	} 
	
	//处理文件，包括打开，重命名等操作
	public void fileOptMenu(final File file)
	{
		OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				if (which == 0)
				{
					openFile(file);
				}
				else if (which == 1)
				{
					//自定义一个带输入的对话框由TextView和EditText构成
					final LayoutInflater factory = LayoutInflater.from(mContext);
					final View dialogview = factory.inflate(R.layout.rename, null);
					//设置TextView的提示信息
					((TextView) dialogview.findViewById(R.id.TextView01)).setText("重命名");
					//设置EditText输入框初始值
					((EditText) dialogview.findViewById(R.id.EditText01)).setText(file.getName());
					
					Builder builder = new Builder(mContext);
					builder.setTitle("重命名");
					builder.setView(dialogview);
					builder.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									//点击确定之后
									String value = GetCurDirectory()+"/"+((EditText) dialogview.findViewById(R.id.EditText01)).getText().toString();
									if(new File(value).exists())
									{
										Builder builder = new Builder(mContext);
										builder.setTitle("重命名");
										builder.setMessage("文件名重复，是否需要覆盖？");
										builder.setPositiveButton(android.R.string.ok,
												new AlertDialog.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														String str2 = GetCurDirectory()+"/"+((EditText) dialogview.findViewById(R.id.EditText01)).getText().toString();
														file.renameTo(new File(str2));
														browseTo(new File(GetCurDirectory()));
													}
												});
										builder.setNegativeButton(android.R.string.cancel,
												new DialogInterface.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														dialog.cancel();
													}
												});
										builder.setCancelable(false);
										builder.create();
										builder.show();
									}
									else 
									{
										//重命名
										file.renameTo(new File(value));
										browseTo(new File(GetCurDirectory()));
									}
								}
							});
					builder.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
								public void onCancel(DialogInterface dialog) {
									dialog.cancel();
								}
							});
					builder.show();
				}
				else if ( which == 2 )
				{
					Builder builder = new Builder(mContext);
					builder.setTitle("删除文件");
					builder.setMessage("确定删除"+file.getName()+"？");
					builder.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									if ( deleteFile(file) )
									{
										Builder builder = new Builder(mContext);
										builder.setTitle("提示对话框");
										builder.setMessage("删除成功");
										builder.setPositiveButton(android.R.string.ok,
												new AlertDialog.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														//点击确定按钮之后
														dialog.cancel();
														browseTo(new File(GetCurDirectory()));
													}
												});
										builder.setCancelable(false);
										builder.create();
										builder.show();
									}
									else 
									{
										Builder builder = new Builder(mContext);
										builder.setTitle("提示对话框");
										builder.setMessage("删除失败");
										builder.setPositiveButton(android.R.string.ok,
												new AlertDialog.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														//点击确定按钮之后
														dialog.cancel();
													}
												});
										builder.setCancelable(false);
										builder.create();
										builder.show();	
									}
								}
							});
					builder.setNegativeButton(android.R.string.cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									dialog.cancel();
								}
							});
					builder.setCancelable(false);
					builder.create();
					builder.show();
				}
				else if ( which == 3 )//复制
				{
					//保存我们复制的文件目录
					myTmpFile = file;
					//这里我们用0表示复制操作
					myTmpOpt = 0;
				}
				else if ( which == 4 )//剪切
				{
					//保存我们复制的文件目录
					myTmpFile = file;
					//这里我们用0表示剪切操作
					myTmpOpt = 1;	 
				}
			}
		};
		//显示操作菜单
	    String[] menu={"打开","重命名","删除","复制","剪切"};
	    new AlertDialog.Builder(mContext)
	        .setTitle("请选择你要进行的操作")
	        .setItems(menu,listener)
	        .show();
	}
	//得到当前目录的绝对路劲
	public String GetCurDirectory()
	{
		return this.currentDirectory.getAbsolutePath();
	}
	//移动文件
	public void moveFile(String source, String destination)
	{
		new File(source).renameTo(new File(destination));   
	}
	//复制文件
	public void copyFile(File src, File target)
	{
		InputStream in = null;
		OutputStream out = null;

		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		try
		{
			in = new FileInputStream(src);
			out = new FileOutputStream(target);
			bin = new BufferedInputStream(in);
			bout = new BufferedOutputStream(out);

			byte[] b = new byte[8192];
			int len = bin.read(b);
			while (len != -1)
			{
				bout.write(b, 0, len);
				len = bin.read(b);
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (bin != null)
				{
					bin.close();
				}
				if (bout != null)
				{
					bout.close();
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
