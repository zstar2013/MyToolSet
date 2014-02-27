package netcom.android.downloader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownLoader {
	private String urlstr;
	private String localfile;
	private int threadCount;
	private Handler mHandler;
	private Dao dao;
	private int fileSize;
	private List<DownLoadInfo> infos;
	private static final int INIT = 1;
	private static final int DOWNLOADING = 2;
	private static final int PAUSE = 3;
	private int state = INIT;

	public DownLoader(String urlstr, String localfile, int threadCount,
			Context context, Handler handler) {
		this.urlstr = urlstr;
		this.localfile = localfile;
		this.threadCount = threadCount;
		this.mHandler = handler;
		dao = new Dao(context);
	}
	
	public void getSpeed(){
		
	}

	/**
	 * 初始化
	 */
	private void init() {
		try {
			URL url = new URL(urlstr);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("GET");
			fileSize = connection.getContentLength();

			File file = new File(localfile);
			if (!file.exists()) {
				file.createNewFile();
			}
			// 本地访问文件
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			accessFile.setLength(fileSize);
			accessFile.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 判断是否是第一次 下载
	 * 
	 * @param urlstr
	 * @return
	 */
	private boolean isFirst(String urlstr) {
		return dao.isHasInfos(urlstr);
	}

	/**
	 * 判断是否正在下载
	 * 
	 * @return
	 */
	public boolean isDownLoading() {
		return state == DOWNLOADING;
	}

	public void downLoad() {
		if (infos != null) {
			if (state == DOWNLOADING) {
				return;
			}
			state = DOWNLOADING;
			for (DownLoadInfo info : infos) {
				new MyThread(info.getThreadId(), info.getStartPos(),
						info.getEndPos(), info.getCompleteSize(), info.getUrl())
						.start();
			}
		}
	}

	public LoadInfo getDownLoaderInfos() {
		if (isFirst(urlstr)) {
			Log.v("TAG", "isFirst");
			init();
			int range = fileSize / threadCount;
			infos = new ArrayList<DownLoadInfo>();
			for (int i = 0; i < threadCount - 1; i++) {
				DownLoadInfo info = new DownLoadInfo(i, i * range, (i + 1)
						* range - 1, 0, urlstr);
				infos.add(info);
			}
			DownLoadInfo info = new DownLoadInfo(threadCount - 1,
					(threadCount - 1) * range, fileSize - 1, 0, urlstr);
			infos.add(info);
			dao.saveInfos(infos);
			LoadInfo loadInfo = new LoadInfo(fileSize, 0, urlstr);
			return loadInfo;

		} else {
			infos = dao.getInfos(urlstr);
			Log.v("TAG", "not isFirst size=" + infos.size());
			int size = 0;
			int compeleteSize = 0;
			for (DownLoadInfo info : infos) {
				compeleteSize += info.getCompleteSize();
				size += info.getEndPos() - info.getStartPos() + 1;
			}
			return new LoadInfo(size, compeleteSize, urlstr);

		}

	}

	public class MyThread extends Thread {
		private int threadId;
		private int startPos;
		private int endPos;
		private int completeSize;
		private String urlstr;

		public MyThread(int threadId, int startPos, int endPos,
				int completeSize, String urlstr) {
			this.threadId = threadId;
			this.startPos = startPos;
			this.endPos = endPos;
			this.completeSize = completeSize;
			this.urlstr = urlstr;
		}

		public void run() {

			HttpURLConnection connection = null;
			RandomAccessFile randomAccessFile = null;
			InputStream is = null;
			BufferedInputStream bs=null;
			try {
				URL url = new URL(urlstr);
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Range", "bytes="
						+ (startPos + completeSize) + "-" + endPos);
				randomAccessFile = new RandomAccessFile(localfile, "rwd");
				randomAccessFile.seek(startPos + completeSize);
				// 将要下载的文件写到保存在保存路径下的文件中
				is = connection.getInputStream();
				bs=new BufferedInputStream(is);
				byte[] buffer =new byte[1048576];
				int length = -1;
				while ((length = bs.read(buffer)) != -1) {
					randomAccessFile.write(buffer, 0, length);
					completeSize += length;
					// 更新数据库中的下载信息
					dao.updataInfos(threadId, completeSize, urlstr);
					// 用消息将下载信息传给进度条，对进度条进行更新
					Message message = Message.obtain();
					message.what = 1;
					message.obj = urlstr;
					message.arg1 = length;
					mHandler.sendMessage(message);
					if (state == PAUSE) {
						return;
					}
				}

			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if(bs!=null){
						bs.close();
					}
					is.close();
					randomAccessFile.close();
					connection.disconnect();
					dao.closeDb();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	public void delete(String urlstr) {
		dao.delete(urlstr);
	}

	public void pause() {
		state = PAUSE;
	}

	public void reset() {
		state = INIT;
	}

}
