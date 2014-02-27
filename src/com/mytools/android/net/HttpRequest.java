package com.mytools.android.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class HttpRequest {
	private static final String TAG="TAG";
	/**
	 * HttpClient post data
	 * @param path
	 * @param params
	 * @throws Exception
	 */
	public static  JSONObject httpPostData(String path,Map<String, String> params)throws Exception{
		HttpClient httpclient=new DefaultHttpClient();
		HttpPost httppost=new HttpPost(path);
//		httppost.addHeader("Authorization", "your token"); //Auth token 
//		httppost.addHeader("Content-Type", "application/json");
//		httppost.addHeader("User-Agent", "imgfornote"); 
		JSONObject obj = new JSONObject(); 
		for(Map.Entry<String, String> entry:params.entrySet()){
			obj.put(entry.getKey(), entry.getValue());
		}
		Log.v("TAG", obj.toString());
		httppost.setEntity(new StringEntity(obj.toString())); 
		HttpResponse response; 
		response = httpclient.execute(httppost);
		int code = response.getStatusLine().getStatusCode(); 
		if(code==200){
			String rev = EntityUtils.toString(response.getEntity());
			obj = new JSONObject(rev);
		}
		return obj;
	}
	
	
	
	
	/**
	 * Http Get Request
	 * @param path
	 * @param params
	 * @param enc
	 * @return Boolean
	 * @throws Exception
	 */
	public static JSONObject getJsonRequest(String path,
			Map<String, String> params) {
		JSONObject jsonObject = null;
		try {
			 jsonObject=new JSONObject(getResultString(path, params));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonObject;
	}




	public static String getResultString(String path, Map<String, String> params) {
		StringBuilder sb = new StringBuilder(path);
		BufferedReader br = null;
		HttpURLConnection conn=null;
		if(params!=null){
			sb.append('?');
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append('=')
						.append(URLEncoder.encode(entry.getValue()))
						.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		try {
			URL url = new URL(sb.toString());
			 conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			if (conn.getResponseCode() == 200) {
				br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
				StringBuilder sbres=new StringBuilder();
				String str;
				while((str=br.readLine())!=null){
					sbres.append(str);
				}
				
				return sbres.toString();
			}
			return "";
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				br=null;
			}
		}
		return "";
	}
	
	
	
	/**
	 * Http Get Request
	 * @param path
	 * @param params
	 * @param enc
	 * @return Boolean
	 * @throws Exception
	 */
	public static boolean sendGetRequest(String path,
			Map<String, String> params, String enc) throws Exception {
		StringBuilder sb = new StringBuilder(path);
		sb.append('?');
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append(entry.getKey()).append('=')
					.append(URLEncoder.encode(entry.getValue(), enc))
					.append('&');
		}
		sb.deleteCharAt(sb.length() - 1);
		URL url = new URL(sb.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setConnectTimeout(5 * 1000);
		if (conn.getResponseCode() == 200) {
			return true;
		}
		return false;
	}

	/**
	 * Http Post Request
	 * @param path
	 * @param params
	 * @param enc
	 * @return Boolean
	 * @throws Exception
	 */
	public static boolean sendPostRequest(String path,
			Map<String, String> params, String enc) throws Exception {
		StringBuilder sb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				sb.append(entry.getKey()).append('=')
						.append(URLEncoder.encode(entry.getValue(), enc))
						.append('&');
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] entitydata = sb.toString().getBytes();
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setConnectTimeout(5 * 1000);
		conn.setDoOutput(true);
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				String.valueOf(entitydata.length));
		OutputStream outStream = conn.getOutputStream();
		outStream.write(entitydata);
		outStream.flush();
		outStream.close();
		if (conn.getResponseCode() == 200) {
			return true;
		}
		return false;
	}
	
	
	public static String getHTMLContext(String paramString)
	  {
	    String str = null;
	    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
	    HttpGet localHttpGet = new HttpGet(paramString);
	    try
	    {
	      InputStream localInputStream = localDefaultHttpClient.execute(localHttpGet).getEntity().getContent();
	      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
	      byte[] arrayOfByte = new byte[1024];
	      int i = localInputStream.read(arrayOfByte);
	      str = null;
	      if (i == -1)
	      {
	        str = new String(localByteArrayOutputStream.toByteArray());
	        return str;
	      }
	      localByteArrayOutputStream.write(arrayOfByte, 0, i);
	      int j = localInputStream.read(arrayOfByte);
	      i = j;
	    }
	    catch (ClientProtocolException localClientProtocolException)
	    {
	      localClientProtocolException.printStackTrace();
	      return str;
	    }
	    catch (IOException localIOException)
	    {
	      localIOException.printStackTrace();
	    }
	    return str;
	  }
}
