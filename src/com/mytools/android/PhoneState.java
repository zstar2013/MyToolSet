package com.mytools.android;

import android.content.Context;
import android.telephony.TelephonyManager;

public class PhoneState {
	
	public static String fetch_status(Context context){  
	    TelephonyManager tm = (TelephonyManager) context  
	    .getSystemService(Context.TELEPHONY_SERVICE);//      
	    String str = "";  
	    str += "DeviceId(IMEI) = " + tm.getDeviceId() + "\n";    
	    str += "DeviceSoftwareVersion = " + tm.getDeviceSoftwareVersion() + "\n";    
	    str += "Line1Number = " + tm.getLine1Number() + "\n";    
	    str += "NetworkCountryIso = " + tm.getNetworkCountryIso() + "\n";    
	    str += "NetworkOperator = " + tm.getNetworkOperator() + "\n";    
	    str += "NetworkOperatorName = " + tm.getNetworkOperatorName() + "\n";    
	    str += "NetworkType = " + tm.getNetworkType() + "\n";    
	    str += "PhoneType = " + tm.getPhoneType() + "\n";    
	    str += "SimCountryIso = " + tm.getSimCountryIso() + "\n";   
	    str += "SimOperator = " + tm.getSimOperator() + "\n";    
	    str += "SimOperatorName = " + tm.getSimOperatorName() + "\n";    
	    str += "SimSerialNumber = " + tm.getSimSerialNumber() + "\n";    
	    str += "SimState = " + tm.getSimState() + "\n";    
	    str += "SubscriberId(IMSI) = " + tm.getSubscriberId() + "\n";    
	    str += "VoiceMailNumber = " + tm.getVoiceMailNumber() + "\n";    
	    return str;  
	} 
}
