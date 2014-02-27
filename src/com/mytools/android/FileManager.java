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

	//����ļ�ϵͳ�ĸ�Ŀ¼
	private void browseToRoot() 
	{
		browseTo(new File("/"));
    }
	//������һ��Ŀ¼
	private void upOneLevel()
	{
		if(this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
	}
	//���ָ����Ŀ¼,������ļ�����д򿪲���
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
	//��ָ���ļ�
	protected void openFile(File aFile)
	{
		Intent intent = new Intent();
		intent.setAction(android.content.Intent.ACTION_VIEW);
		File file = new File(aFile.getAbsolutePath());
		// ȡ���ļ���
		String fileName = file.getName();
		// ���ݲ�ͬ���ļ����������ļ�
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
	
	//ͨ���ļ����ж���ʲô���͵��ļ�
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
	
	//ճ������
	public void MyPaste()
	{
		if ( myTmpFile == null )
		{
			Builder builder = new Builder(mContext);
			builder.setTitle("��ʾ");
			builder.setMessage("û�и��ƻ���в���");
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
			if ( myTmpOpt == 0 )//���Ʋ���
			{
				if(new File(GetCurDirectory()+"/"+myTmpFile.getName()).exists())
				{
					Builder builder = new Builder(mContext);
					builder.setTitle("ճ����ʾ");
					builder.setMessage("��Ŀ¼����ͬ���ļ����Ƿ���Ҫ���ǣ�");
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
			else if(myTmpOpt == 1)//ճ������
			{
				if(new File(GetCurDirectory()+"/"+myTmpFile.getName()).exists())
				{
					Builder builder = new Builder(mContext);
					builder.setTitle("ճ����ʾ");
					builder.setMessage("��Ŀ¼����ͬ���ļ����Ƿ���Ҫ���ǣ�");
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
	//ɾ�������ļ���
	public void MyDelete()
	{
		//ȡ�õ�ǰĿ¼
		File tmp=new File(this.currentDirectory.getAbsolutePath());
		//������һ��Ŀ¼
		this.upOneLevel();
		//ɾ��ȡ�õ�Ŀ¼
		if ( deleteFolder(tmp) )
		{
			Builder builder = new Builder(mContext);
			builder.setTitle("��ʾ");
			builder.setMessage("ɾ���ɹ�");
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
			builder.setTitle("��ʾ");
			builder.setMessage("ɾ��ʧ��");
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
	//�½��ļ���
	public void Mynew()
	{
		final LayoutInflater factory = LayoutInflater.from(mContext);
		final View dialogview = factory.inflate(R.layout.dialog, null);
		//����TextView
		((TextView) dialogview.findViewById(R.id.TextView_PROM)).setText("�������½��ļ��е����ƣ�");
		//����EditText
		((EditText) dialogview.findViewById(R.id.EditText_PROM)).setText("�ļ�������...");
		
		Builder builder = new Builder(mContext);
		builder.setTitle("�½��ļ���");
		builder.setView(dialogview);
		builder.setPositiveButton(android.R.string.ok,
				new AlertDialog.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String value = ((EditText) dialogview.findViewById(R.id.EditText_PROM)).getText().toString();
						if ( newFolder(value) )
						{
							Builder builder = new Builder(mContext);
							builder.setTitle("��ʾ");
							builder.setMessage("�½��ļ��гɹ�");
							builder.setPositiveButton(android.R.string.ok,
									new AlertDialog.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											//���ȷ����ť֮��,����ִ����ҳ�еĲ���
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
							builder.setTitle("��ʾ");
							builder.setMessage("�½��ļ���ʧ��");
							builder.setPositiveButton(android.R.string.ok,
									new AlertDialog.OnClickListener() {
										public void onClick(DialogInterface dialog, int which) {
											//���ȷ����ť֮��,����ִ����ҳ�еĲ���
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
	//�½��ļ���
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
	//ɾ���ļ�
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
    //ɾ���ļ���
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
	
	//�����ļ��������򿪣��������Ȳ���
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
					//�Զ���һ��������ĶԻ�����TextView��EditText����
					final LayoutInflater factory = LayoutInflater.from(mContext);
					final View dialogview = factory.inflate(R.layout.rename, null);
					//����TextView����ʾ��Ϣ
					((TextView) dialogview.findViewById(R.id.TextView01)).setText("������");
					//����EditText������ʼֵ
					((EditText) dialogview.findViewById(R.id.EditText01)).setText(file.getName());
					
					Builder builder = new Builder(mContext);
					builder.setTitle("������");
					builder.setView(dialogview);
					builder.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									//���ȷ��֮��
									String value = GetCurDirectory()+"/"+((EditText) dialogview.findViewById(R.id.EditText01)).getText().toString();
									if(new File(value).exists())
									{
										Builder builder = new Builder(mContext);
										builder.setTitle("������");
										builder.setMessage("�ļ����ظ����Ƿ���Ҫ���ǣ�");
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
										//������
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
					builder.setTitle("ɾ���ļ�");
					builder.setMessage("ȷ��ɾ��"+file.getName()+"��");
					builder.setPositiveButton(android.R.string.ok,
							new AlertDialog.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									if ( deleteFile(file) )
									{
										Builder builder = new Builder(mContext);
										builder.setTitle("��ʾ�Ի���");
										builder.setMessage("ɾ���ɹ�");
										builder.setPositiveButton(android.R.string.ok,
												new AlertDialog.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														//���ȷ����ť֮��
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
										builder.setTitle("��ʾ�Ի���");
										builder.setMessage("ɾ��ʧ��");
										builder.setPositiveButton(android.R.string.ok,
												new AlertDialog.OnClickListener() {
													public void onClick(DialogInterface dialog, int which) {
														//���ȷ����ť֮��
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
				else if ( which == 3 )//����
				{
					//�������Ǹ��Ƶ��ļ�Ŀ¼
					myTmpFile = file;
					//����������0��ʾ���Ʋ���
					myTmpOpt = 0;
				}
				else if ( which == 4 )//����
				{
					//�������Ǹ��Ƶ��ļ�Ŀ¼
					myTmpFile = file;
					//����������0��ʾ���в���
					myTmpOpt = 1;	 
				}
			}
		};
		//��ʾ�����˵�
	    String[] menu={"��","������","ɾ��","����","����"};
	    new AlertDialog.Builder(mContext)
	        .setTitle("��ѡ����Ҫ���еĲ���")
	        .setItems(menu,listener)
	        .show();
	}
	//�õ���ǰĿ¼�ľ���·��
	public String GetCurDirectory()
	{
		return this.currentDirectory.getAbsolutePath();
	}
	//�ƶ��ļ�
	public void moveFile(String source, String destination)
	{
		new File(source).renameTo(new File(destination));   
	}
	//�����ļ�
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
