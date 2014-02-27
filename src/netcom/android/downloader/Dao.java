package netcom.android.downloader;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Dao {
	private DBHelper dbHelper;

	public Dao(Context context) {
		dbHelper=new DBHelper(context);
	}

	/**
	 * 判断数据库中是否已经有该记录
	 * @param urlstr
	 * @return
	 */
	public boolean isHasInfos(String urlstr) {
		SQLiteDatabase database=dbHelper.getReadableDatabase();
		 String sql = "select count(*)  from download_info where url=?";
		          Cursor cursor = database.rawQuery(sql, new String[] { urlstr });
		          cursor.moveToFirst();
		          int count = cursor.getInt(0);
		          cursor.close();
		          return count == 0;

	}

	public void saveInfos(List<DownLoadInfo> infos) {
		 SQLiteDatabase database = dbHelper.getWritableDatabase();
		         for (DownLoadInfo info : infos) {
		              String sql = "insert into download_info(thread_id,start_pos, end_pos,compelete_size,url) values (?,?,?,?,?)";
		              Object[] bindArgs = { info.getThreadId(), info.getStartPos(),
		                    info.getEndPos(), info.getCompleteSize(), info.getUrl() };
		              database.execSQL(sql, bindArgs);
		          }

	}

	public List<DownLoadInfo> getInfos(String urlstr) {
		  List<DownLoadInfo> list = new ArrayList<DownLoadInfo>();
		          SQLiteDatabase database = dbHelper.getReadableDatabase();
		           String sql = "select thread_id, start_pos, end_pos,compelete_size,url from download_info where url=?";
		           Cursor cursor = database.rawQuery(sql, new String[] { urlstr });
		           while (cursor.moveToNext()) {
		              DownLoadInfo info = new DownLoadInfo(cursor.getInt(0),
		                      cursor.getInt(1), cursor.getInt(2), cursor.getInt(3),
		                      cursor.getString(4));
		               list.add(info);
		           }
		           cursor.close();
		           return list;

	}

	public void updataInfos(int threadId, int completeSize, String urlstr) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		        String sql = "update download_info set compelete_size=? where thread_id=? and url=?";
		         Object[] bindArgs = { completeSize, threadId, urlstr };
		         try{
		        	 database.execSQL(sql, bindArgs);
		         }catch (Exception e){
		        	 //有可能出现数据库先关闭的异常
		        	 e.printStackTrace();
		         }
		        

		
	}

	public void closeDb() {
		dbHelper.close();
	}

	public void delete(String urlstr) {
		SQLiteDatabase database = dbHelper.getReadableDatabase();
		         database.delete("download_info", "url=?", new String[] { urlstr });
		         database.close();

		
	}

}
