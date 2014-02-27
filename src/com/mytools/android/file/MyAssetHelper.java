/**
 * MyAssetHelper.java
 * com.mytools.android.file
 *
 * Function£º TODO 
 *
 *   ver     date      		author
 * ©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤©¤
 *   		 2013-5-21 		zstar
 *
 * Copyright (c) 2013, TNT All Rights Reserved.
*/

package com.mytools.android.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.AssetManager;

/**
 * ClassName:MyAssetHelper
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   zstar
 * @version  
 * @since    Ver 1.1
 * @Date	 2013-5-21		ÏÂÎç3:17:24
 *
 * @see 	 
 */
public class MyAssetHelper {
	private String[] files = null;
	public String[] getfileFromAssets(Context context,String path) {
		AssetManager assetManager = context.getAssets();
		try {
			files = assetManager.list(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return files;

	}

	public List listHtmlOfAssets(Context context) {
		List list = new ArrayList();
		files = getfileFromAssets(context,"html");
		for (int i = 0; i < files.length; i++) {
			HashMap map = new HashMap();
			map.put("htmlname", files[i]);
			list.add(map);
		}
		return list;
	}
}

