package com.mytools.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class ConnectionUtils {

	public static String doConnection(String strurl) {
		String result = "";
		HttpURLConnection conn = null;
		InputStream stream = null;
		BufferedReader br = null;
		URL url;
		try {
			url = new URL(strurl);
			conn = (HttpURLConnection) url.openConnection();			
			if (conn != null) {
				conn.setDoInput(true);
				conn.setConnectTimeout(3000);
				conn.setRequestMethod("GET");

				String location = conn.getRequestProperty("location");
				int resCode = conn.getResponseCode();				
				conn.connect();				
				stream = conn.getInputStream();
				br = new BufferedReader(new InputStreamReader(stream));
				String str = "";
				while ((str = br.readLine())!= null) {
					result += str;
				}
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return "";
		} finally {
			try {
				if (br != null) {
					br.close();
					br = null;
				}
				if (stream != null) {
					stream.close();
					stream = null;
				}
				if (conn != null) {
					conn.disconnect();
					conn = null;
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;

	}

	public static String getHtmlBody(String strHtml) {
		if(strHtml!=""||strHtml!=null){
			strHtml = strHtml.substring(strHtml.indexOf("<body>") + 6,
					strHtml.length());
			strHtml = strHtml.substring(0, strHtml.indexOf("</body>"));
		}
		
		return strHtml.trim();
	}

	public static boolean isStringIPType(String check) {
		Pattern p = Pattern
				.compile("(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)){3}");
		Matcher m = p.matcher(check);
		return m.matches();
	}

	public static boolean cheackConnection(String strurl) {
		return isStringIPType(getHtmlBody(doConnection(strurl)));
	}
	
	
	 public static boolean isNetworkAvailable(Context context) {
			
			try {
				ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				if (connectivity != null) {
				
					NetworkInfo info = connectivity.getActiveNetworkInfo();
					if (info != null && info.isConnected()) {
					
						if (info.getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			} catch (Exception e) {
			return false;
			}
			return false;
		}
}
