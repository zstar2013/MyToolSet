package com.mytools.android;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

public class FileUtils {

	private final static String DEFAULT_CODE = "UTF-8";
	private Context context;

	public static Bitmap getImageFromAssetFile(Context context, String fileName) {

		Bitmap image = null;

		try {

			AssetManager am = context.getAssets();

			InputStream is = am.open(fileName);

			image = BitmapFactory.decodeStream(is);

			is.close();

		} catch (Exception e) {

		}

		return image;

	}

	public static String getFileTextFromAssets(Context context, String path) {
		return getFileTextFromAssets(context, path, DEFAULT_CODE);

	}

	/**
	 * ��ȡ�ļ�������
	 * 
	 * @param filename
	 *            �ļ�����
	 * @return
	 * @throws Exception
	 */
	public static String readFile(Context context, String filename)
			throws Exception {
		// ���������
		File file = new File(filename);
		FileInputStream inStream = new FileInputStream(file);
		// newһ��������
		byte[] buffer = new byte[1024];
		int len = 0;
		// ʹ��ByteArrayOutputStream�������������
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			// д������
			outStream.write(buffer, 0, len);
		}
		// �õ��ļ��Ķ���������
		byte[] data = outStream.toByteArray();
		// �ر���
		outStream.close();
		inStream.close();
		return new String(data);
	}

	private static String getFileTextFromAssets(Context context, String path,
			String code) {
		try {
			InputStream in = context.getResources().getAssets().open(path);// �ļ�����Ϊrose.txt
			int length = in.available();
			byte[] buffer = new byte[length];
			in.read(buffer);
			String res = EncodingUtils.getString(buffer, code);
			return res;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}

	private static String getFileTextFromSdcard(Context context, String path,
			String code) {
		return code;

	}

	public void SDCardSizeTest() {

		// ȡ��SDCard��ǰ��״̬
		String sDcString = android.os.Environment.getExternalStorageState();

		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {

			// ȡ��sdcard�ļ�·��
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();

			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

			// ��ȡSDCard��BLOCK����
			long nTotalBlocks = statfs.getBlockCount();

			// ��ȡSDCard��ÿ��block��SIZE
			long nBlocSize = statfs.getBlockSize();

			// ��ȡ�ɹ�����ʹ�õ�Block������
			long nAvailaBlock = statfs.getAvailableBlocks();

			// ��ȡʣ�µ�����Block������(����Ԥ����һ������޷�ʹ�õĿ�)
			long nFreeBlock = statfs.getFreeBlocks();

			// ����SDCard ��������СMB
			long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

			// ���� SDCard ʣ���СMB
			long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
		}// end of if
			// end of func
	}

	/**
	 * ��ȡ�ļ�������
	 * 
	 * @param filename
	 *            �ļ�����
	 * @return
	 * @throws Exception
	 */
	public String readFile(String filename) throws Exception {
		// ���������
		FileInputStream inStream = context.openFileInput(filename);
		// newһ��������
		byte[] buffer = new byte[1024];
		int len = 0;
		// ʹ��ByteArrayOutputStream�������������
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			// д������
			outStream.write(buffer, 0, len);
		}
		// �õ��ļ��Ķ���������
		byte[] data = outStream.toByteArray();
		// �ر���
		outStream.close();
		inStream.close();
		return new String(data);
	}

	/**
	 * ��Ĭ��˽�з�ʽ�����ļ�������SDCard��
	 * 
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public void saveToSDCard(String filename, String content) throws Exception {
		// ͨ��getExternalStorageDirectory������ȡSDCard���ļ�·��
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		// ��ȡ�����
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * ��Ĭ��˽�з�ʽ�����ļ����ݣ�������ֻ��洢�ռ���
	 * 
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public void save(String filename, String content) throws Exception {
		//
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_PRIVATE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * ��׷�ӵķ�ʽ�����ļ�����
	 * 
	 * @param filename
	 *            �ļ�����
	 * @param content
	 *            �ļ�����
	 * @throws Exception
	 */
	public void saveAppend(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_APPEND);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * ����������Ӧ�ôӸ��ļ��ж�ȡ���ݵķ�ʽ�����ļ�(Context.MODE_WORLD_READABLE)
	 * 
	 * @param filename
	 *            �ļ�����
	 * @param content
	 *            �ļ�����
	 * @throws Exception
	 */
	public void saveReadable(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * ����������Ӧ�������ļ�д�����ݵķ�ʽ�����ļ�
	 * 
	 * @param filename
	 *            �ļ�����
	 * @param content
	 *            �ļ�����
	 * @throws Exception
	 */
	public void saveWriteable(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_WRITEABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * ����������Ӧ�öԸ��ļ�����д�ķ�ʽ�����ļ�(MODE_WORLD_READABLE��MODE_WORLD_WRITEABLE)
	 * 
	 * @param filename
	 *            �ļ�����
	 * @param content
	 *            �ļ�����
	 * @throws Exception
	 */
	public void saveRW(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		// Context.MODE_WORLD_READABLE(1) +
		// Context.MODE_WORLD_WRITEABLE(2),��ʵ����3���
		outStream.write(content.getBytes());
		outStream.close();
	}

}
