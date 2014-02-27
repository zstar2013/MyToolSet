package com.mytools.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class Helper {
	/**
	 * Checks if the application is installed on the SD card.
	 * 
	 * @return <code>true</code> if the application is installed on the sd card
	 */
	public static boolean isInstalledOnSdCard(Context context) {
		// check for API level 8 and higher
		if (VERSION.SDK_INT > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			PackageManager pm = context.getPackageManager();
			try {
				PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
				ApplicationInfo ai = pi.applicationInfo;
				return (ai.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) == ApplicationInfo.FLAG_EXTERNAL_STORAGE;
			} catch (NameNotFoundException e) {
				// ignore
			}
		}

		// check for API level 7 - check files dir
		try {
			String filesDir = context.getFilesDir().getAbsolutePath();
			if (filesDir.startsWith("/data/")) {
				return false;
			} else if (filesDir.contains("/mnt/")
					|| filesDir.contains("/sdcard/")) {
				return true;
			}
		} catch (Throwable e) {
			// ignore
		}

		return false;
	}

	public static String getContacts(Context context) {
		StringBuilder sb=new StringBuilder();
		ContentResolver cr = context.getContentResolver();
		Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);
		while (cursor.moveToNext()) {
			// get name
			int nameFiledColumnIndex = cursor
					.getColumnIndex(PhoneLookup.DISPLAY_NAME);
			
			String contact = cursor.getString(nameFiledColumnIndex);
			sb.append(contact);
			sb.append(";");

			String[] PHONES_PROJECTION = new String[] { "_id", "display_name",
					"data1", "data3" };//
			String contactId = cursor.getString(cursor
					.getColumnIndex(PhoneLookup._ID));
			Cursor phone = cr.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					PHONES_PROJECTION,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ contactId, null, null);
			// name type ..
			while (phone.moveToNext()) {
				int i = phone.getInt(0);
				String str = phone.getString(1);
				str = phone.getString(2);
				str = phone.getString(3);
			}
			phone.close();
			// addr
			Cursor addrCur = cr
					.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI,
							new String[] { "_id", "data1", "data2", "data3" },
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ "=" + contactId, null, null);
			while (addrCur.moveToNext()) {
				int i = addrCur.getInt(0);
				String str = addrCur.getString(1);
				str = addrCur.getString(2);
				str = addrCur.getString(3);
			}
			addrCur.close();

			// email
			Cursor emailCur = cr.query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI,
					new String[] { "_id", "data1", "data2", "data3" },
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ contactId, null, null);
			while (emailCur.moveToNext()) {
				int i = emailCur.getInt(0);
				String str = emailCur.getString(1);
				str = emailCur.getString(2);
				str = emailCur.getString(3);
			}
			emailCur.close();

		}
		cursor.close();
		return sb.toString();
	}
	
	/**
	 * android �л�ȡjϵ��
	 */
	private static void getContacts2(Context context){
		ContentResolver cr = context.getContentResolver();
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while(cursor.moveToNext()){
         //get name
         int nameFiledColumnIndex = cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME);
         String contact = cursor.getString(nameFiledColumnIndex);
         
            String[] PHONES_PROJECTION = new String[] { "_id","display_name","data1","data3"};//
            String contactId = cursor.getString(cursor.getColumnIndex(PhoneLookup._ID));
            Cursor phone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PHONES_PROJECTION, 
                                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId, null, null);
            //name type .. 
            while(phone.moveToNext()) {
             int i = phone.getInt(0);
             String str = phone.getString(1);
             str = phone.getString(2);
             str = phone.getString(3);
            }
            phone.close();
            //addr
            Cursor addrCur = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI ,
        new String[]{"_id","data1","data2","data3"}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId , null, null);
            while(addrCur.moveToNext()) {
             int i = addrCur.getInt(0);
             String str = addrCur.getString(1);
             str = addrCur.getString(2);
             str = addrCur.getString(3);
            }
            addrCur.close();
            
            //email
            Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI ,
        new String[]{"_id","data1","data2","data3"}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId , null, null);
            while(emailCur.moveToNext()) {
             int i = emailCur.getInt(0);
             String str = emailCur.getString(1);
             str = emailCur.getString(2);
             str = emailCur.getString(3);
            }
            emailCur.close();
            
        }
        cursor.close();
	}
	
	/**
	 * android�л�ȡͨ����¼
	 * @param context
	 */
	public static void getPhoneCall(Context context){
		String str = "";
        int type;
        long callTime;
        Date date;
        String time= "";
        ContentResolver cr = context.getContentResolver();
        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[]{CallLog.Calls.NUMBER,CallLog.Calls.CACHED_NAME,CallLog.Calls.TYPE, CallLog.Calls.DATE}, null, null,CallLog.Calls.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {   
            cursor.moveToPosition(i);
            str = cursor.getString(0);
            str = cursor.getString(1);
            type = cursor.getInt(2);
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = new Date(Long.parseLong(cursor.getString(3)));
            time = sfd.format(date);
           }
	}
	

	
	
}
