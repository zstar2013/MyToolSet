package com.mytools.android.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetStateHelper {
	
	
	private Context mContext;
	private static String TAG="NetStateHelper";
	
	public NetStateHelper(Context context) {
		mContext=context;
		
	}
	/*private static BroadcastReceiver mReceiver;
    private static IntentFilter mFilter;    
    private static void registerBroadcastForNet(Context mContext) {
        mFilter = new IntentFilter();
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                        NetworkInfo info = (NetworkInfo)
                                intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                         State state = info.getState();
                         if (state == State.CONNECTED) {      
                             
                           
                         } else if (state == State.DISCONNECTED) {
                             Toast.makeText(context, "��������ʧ�ܣ���ȷ��״̬�����½������", Toast.LENGTH_SHORT).show();
                         }
                    }
                }
            };
            mContext.registerReceiver(mReceiver, mFilter);
    }
    
    private static void unRegisterBroadcastForNet(Context mContext){
    	mContext.unregisterReceiver(mReceiver);
    }*/
	
	
	
	
    /**
     * �ж��Ƿ������Ѿ�����
     * isNetworkConnected
     * @param context
     * @return  
     *boolean 
     * @exception  
     * @since  1.0.0
     */
    public boolean isNetworkConnected(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
            if (mNetworkInfo != null) {  
                return mNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }
    /**
     * �ж�WIFI�����Ƿ����
     * isWifiConnected
     * @param context
     * @return  
     *boolean 
     * @exception  
     * @since  1.0.0
     */
    public boolean isWifiConnected(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager  
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);  
            if (mWiFiNetworkInfo != null) {  
                return mWiFiNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }
    /**
     * �ж�MOBILE�����Ƿ����
     * isMobileConnected
     * @param context
     * @return  
     *boolean 
     * @exception  
     * @since  1.0.0
     */
    public boolean isMobileConnected(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mMobileNetworkInfo = mConnectivityManager  
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);  
            if (mMobileNetworkInfo != null) {  
                return mMobileNetworkInfo.isAvailable();  
            }  
        }  
        return false;  
    }
    /**
     * ��ȡ��ǰ�������ӵ�������Ϣ
     * getConnectedType
     * @param context
     * @return  
     *int 
     * @exception  
     * @since  1.0.0
     */
    public static int getConnectedType(Context context) {  
        if (context != null) {  
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context  
                    .getSystemService(Context.CONNECTIVITY_SERVICE);  
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();  
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {  
                return mNetworkInfo.getType();  
            }  
        }  
        return -1;  
    }
    
    public static boolean isNetworkAvailable(Context context) {   
        ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);   
        if (connectivity == null) {   
            Log.i("NetWorkState", "Unavailabel");   
            return false;   
        } else {   
            NetworkInfo[] info = connectivity.getAllNetworkInfo();   
            if (info != null) {   
                for (int i = 0; i < info.length; i++) {   
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {   
                        Log.i("NetWorkState", "Availabel");   
                        return true;   
                    }   
                }   
            }   
        }   
        return false;   
    }
    
    
    
    //-----------------------------------------
    /**
    
     * @author sky

     * Email vipa1888@163.com

     * QQ:840950105

     * ��ȡ��ǰ������״̬  -1��û������  1��WIFI����2��wap����3��net����

     * @param context

     * @return

     */ 

    public static NetType getAPNType(Context context){ 

        NetType netType = NetType.DISCONNECT;  

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); 

         

        if(networkInfo==null){ 

            return netType; 

        } 

        int nType = networkInfo.getType(); 

        if(nType==ConnectivityManager.TYPE_MOBILE){ 

            Log.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is "+networkInfo.getExtraInfo()); 

            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){ 

                netType = NetType.CMNET; 

            } 

            else{ 

                netType = NetType.CMWAP; 

            } 

        } 

        else if(nType==ConnectivityManager.TYPE_WIFI){ 

            netType = NetType.WIFI; 

        } 

        return netType; 

    }
    
    enum NetType {
    	DISCONNECT,CMNET,CMWAP,WIFI
    }
}
