package com.mytools.android.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.EncodingUtils;

import android.content.Context;

public class FileLoader {
	public static void getFileText(Context context) {  
        try {  
            InputStream in = context.getResources().getAssets().open("data1.xml");// 文件名字为rose.txt  
            int length = in.available();  
            byte[] buffer = new byte[length];  
            in.read(buffer);  
            String res = EncodingUtils.getString(buffer, "UTF-8");  
            System.out.println(res);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
          
    }  
	public static InputStream getFileStream(Context context) {  
        try {  
            return context.getResources().getAssets().open("data1.xml");// 文件名字为rose.txt  

        } catch (IOException e) {  
            e.printStackTrace();  
        }
		return null;  
          
    }  
}
