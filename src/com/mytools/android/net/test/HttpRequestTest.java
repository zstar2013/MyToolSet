package com.mytools.android.net.test;

import com.mytools.android.net.HttpRequest;

import android.test.AndroidTestCase;
import android.util.Log;

public class HttpRequestTest extends AndroidTestCase {
	private final static String TAG="HttpRequestTest";
	public void testcase1(){
		String context=HttpRequest.getHTMLContext("http://www.baidu.com");
		Log.v(TAG, context);
	}

}
