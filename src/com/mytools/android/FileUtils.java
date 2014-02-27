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
	 * 读取文件的内容
	 * 
	 * @param filename
	 *            文件名称
	 * @return
	 * @throws Exception
	 */
	public static String readFile(Context context, String filename)
			throws Exception {
		// 获得输入流
		File file = new File(filename);
		FileInputStream inStream = new FileInputStream(file);
		// new一个缓冲区
		byte[] buffer = new byte[1024];
		int len = 0;
		// 使用ByteArrayOutputStream类来处理输出流
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			// 写入数据
			outStream.write(buffer, 0, len);
		}
		// 得到文件的二进制数据
		byte[] data = outStream.toByteArray();
		// 关闭流
		outStream.close();
		inStream.close();
		return new String(data);
	}

	private static String getFileTextFromAssets(Context context, String path,
			String code) {
		try {
			InputStream in = context.getResources().getAssets().open(path);// 文件名字为rose.txt
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

		// 取得SDCard当前的状态
		String sDcString = android.os.Environment.getExternalStorageState();

		if (sDcString.equals(android.os.Environment.MEDIA_MOUNTED)) {

			// 取得sdcard文件路径
			File pathFile = android.os.Environment
					.getExternalStorageDirectory();

			android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());

			// 获取SDCard上BLOCK总数
			long nTotalBlocks = statfs.getBlockCount();

			// 获取SDCard上每个block的SIZE
			long nBlocSize = statfs.getBlockSize();

			// 获取可供程序使用的Block的数量
			long nAvailaBlock = statfs.getAvailableBlocks();

			// 获取剩下的所有Block的数量(包括预留的一般程序无法使用的块)
			long nFreeBlock = statfs.getFreeBlocks();

			// 计算SDCard 总容量大小MB
			long nSDTotalSize = nTotalBlocks * nBlocSize / 1024 / 1024;

			// 计算 SDCard 剩余大小MB
			long nSDFreeSize = nAvailaBlock * nBlocSize / 1024 / 1024;
		}// end of if
			// end of func
	}

	/**
	 * 读取文件的内容
	 * 
	 * @param filename
	 *            文件名称
	 * @return
	 * @throws Exception
	 */
	public String readFile(String filename) throws Exception {
		// 获得输入流
		FileInputStream inStream = context.openFileInput(filename);
		// new一个缓冲区
		byte[] buffer = new byte[1024];
		int len = 0;
		// 使用ByteArrayOutputStream类来处理输出流
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		while ((len = inStream.read(buffer)) != -1) {
			// 写入数据
			outStream.write(buffer, 0, len);
		}
		// 得到文件的二进制数据
		byte[] data = outStream.toByteArray();
		// 关闭流
		outStream.close();
		inStream.close();
		return new String(data);
	}

	/**
	 * 以默认私有方式保存文件内容至SDCard中
	 * 
	 * @param filename
	 * @param content
	 * @throws Exception
	 */
	public void saveToSDCard(String filename, String content) throws Exception {
		// 通过getExternalStorageDirectory方法获取SDCard的文件路径
		File file = new File(Environment.getExternalStorageDirectory(),
				filename);
		// 获取输出流
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * 以默认私有方式保存文件内容，存放在手机存储空间中
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
	 * 以追加的方式保存文件内容
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void saveAppend(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_APPEND);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * 以允许其他应用从该文件中读取内容的方式保存文件(Context.MODE_WORLD_READABLE)
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void saveReadable(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_READABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * 以允许其他应用往该文件写入内容的方式保存文件
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void saveWriteable(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_WRITEABLE);
		outStream.write(content.getBytes());
		outStream.close();
	}

	/**
	 * 以允许其他应用对该文件读和写的方式保存文件(MODE_WORLD_READABLE与MODE_WORLD_WRITEABLE)
	 * 
	 * @param filename
	 *            文件名称
	 * @param content
	 *            文件内容
	 * @throws Exception
	 */
	public void saveRW(String filename, String content) throws Exception {
		FileOutputStream outStream = context.openFileOutput(filename,
				Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);
		// Context.MODE_WORLD_READABLE(1) +
		// Context.MODE_WORLD_WRITEABLE(2),其实可用3替代
		outStream.write(content.getBytes());
		outStream.close();
	}

}
