package com.lavekush.telstra.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by e01065 on 11/25/2017.
 */

public class Util {

    private static final String TAG = Util.class.getSimpleName();

    public static boolean isConnectedWithInternet(Context context) {
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            for (NetworkInfo info : networkInfo) {
                if (info.getTypeName().equalsIgnoreCase(AppConstant.NETWORK_TYPE_WIFI)
                        || info.getTypeName().equalsIgnoreCase(AppConstant.NETWORK_TYPE_MOBILE)) {
                    if (info.isConnected()) {
                        return true;
                    }
                }
            }
        } catch (NullPointerException npe) {
            Log.e(TAG, "NetworkError", npe);
        }

        return false;
    }
}
