package com.slowlife.lib.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by sgrape on 2017/4/28.
 */

public enum NetWorkUtil {
    HAVENONET(0), WIFI(1), MOBILE(2);
    private int network;

    NetWorkUtil(int type) {
        this.network = type;
    }


    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static NetWorkUtil isWifiConnected(Context context) {
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return WIFI;
            }
        } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            String _strSubTypeName = networkInfo.getSubtypeName();


            // TD-SCDMA   networkType is 17
            int networkType = networkInfo.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                    return MOBILE;
            }

        }
        return WIFI;
    }
}