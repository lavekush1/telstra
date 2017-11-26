package com.lavekush.telstra.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.lavekush.telstra.R;

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

    public static Snackbar showSnackBarIndefinite(Context context, View view, String message) {
        Snackbar snackbar = null;

        try {
            snackbar = Snackbar
                    .make(view, message, Snackbar.LENGTH_INDEFINITE)
                    .setAction("dismiss", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Log.d("Event", "OnClick invoked !");
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(context.getResources().getColor(R.color.colorAccent));

            // Changing action button text color
            View sbView = snackbar.getView();
            for (int i = 0; i < ((Snackbar.SnackbarLayout) sbView).getChildCount(); i++) {
                if (((Snackbar.SnackbarLayout) sbView).getChildAt(i) instanceof TextView) {
                    ((TextView) ((Snackbar.SnackbarLayout) sbView).getChildAt(i)).setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    ((TextView) ((Snackbar.SnackbarLayout) sbView).getChildAt(i)).setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.text_size_medium));
                    ((TextView) ((Snackbar.SnackbarLayout) sbView).getChildAt(i)).setMaxLines(10);
                    break;
                }
            }
            snackbar.show();
        } catch (Exception e) {
            Log.i("showSnackBar", "exception during display snackbar", e);
        }
        return snackbar;
    }
}
