/**
 * FileConventer.java
 * com.mytools.android.file
 *
 * Function£º TODO 
 *
 *   ver     date      		author
 * ©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤
 *   		 2013-5-15 		zstar
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
*/

package com.mytools.android.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * ClassName:FileConventer
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zstar
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-15		ÏÂÎç9:47:23
 *
 * @see 	 
 */
public class FileConventer {
	
	  public String BufferedReaderDemo(String path) throws IOException{
	         File file=new File(path);
	         if(!file.exists()||file.isDirectory())
	             throw new FileNotFoundException();
	         BufferedReader br=new BufferedReader(new FileReader(file));
	         String temp=null;
	         StringBuffer sb=new StringBuffer();
	         temp=br.readLine();
	         while(temp!=null){
	             sb.append(temp+" ");
	             temp=br.readLine();
	         }
	         return sb.toString();
	     }
}

