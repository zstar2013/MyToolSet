package com.mytools.android.net.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.SearchableInfo;
import android.util.Log;

public class MyHttpRequest {

	private final String TAG = "tag";
	private String actionUri="";
	private static MyHttpRequest instance;
	public static String total_page;
	
	
	public MyHttpRequest() {
		super();
	}

	public static MyHttpRequest getInstance(){
		if(instance == null){
			instance = new MyHttpRequest();
		}
		return instance;
	}
	
	private JSONObject httpPostData(String uri, Map<String, String> params) throws Exception {

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(uri);
		JSONObject jsonObject = new JSONObject();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			jsonObject.put(entry.getKey(), entry.getValue());
		}
		httpPost.setEntity(new StringEntity(jsonObject.toString()));
		HttpResponse httpResponse = httpClient.execute(httpPost);
		int code = httpResponse.getStatusLine().getStatusCode();
		if (code == HttpStatus.SC_OK) {
			String strResult = EntityUtils.toString(httpResponse.getEntity());
			jsonObject = new JSONObject(strResult);
		} else {
			System.out.println("«Î«Û¥ÌŒÛ 404");
		}
		
		return jsonObject;
	}
	

	public ArrayList<Object> GetCataloglist(Map<String,String> map) throws Exception{
		Map<String, String> params = new HashMap<String, String>();
		JSONObject jsonObject = new JSONObject();
		ArrayList<Object> firstList = new ArrayList<Object>();
		for(Entry entry:map.entrySet()){
			params.put((String)entry.getKey(), (String)entry.getValue());
			}
		 
		 jsonObject = httpPostData(actionUri, params);
		 JSONArray jsonArray = jsonObject.getJSONArray("result");		 	 
		 for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject category = jsonArray.getJSONObject(i);
			Object bo = new Object();
			firstList.add(bo);
		}
		return firstList;
	}
	
	/*====================================From Local Begin ===========================================*/
	/*try {
		is = new FileInputStream(apkFileUrl);
		available = is.available();
		Log.i(TAG, "------available:" + available + "--------");			
		fos = new FileOutputStream(mFile);			
		buf = new byte[available];
		if (is != null) {						
					try {
						while ((numRead = is.read(buf)) != -1) {
							fos.write(buf, 0, numRead);
							showProgressDialog(numRead, mFile);
						}						
						
					} catch (IOException e) {
						e.printStackTrace();
						Log.i(TAG, "------------------------- fails ");
						
					}	
		} else {
			Toast.makeText(mContext, getResources().getString(R.string.no_apk_file_timeout), Toast.LENGTH_SHORT).show();
		}
		
		fos.close();
		is.close();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}*/
	/*====================================From Local End ===========================================*/
	
}
