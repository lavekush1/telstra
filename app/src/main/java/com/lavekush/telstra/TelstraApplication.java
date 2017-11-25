package com.lavekush.telstra;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lavekush.telstra.util.AppConstant.BASE_URL;

public class TelstraApplication extends Application{

    private static Retrofit mRetrofitCore;

    @Override
    public void onCreate() {
        super.onCreate();
    }


    public static Retrofit getNetworkClient() {
        if (mRetrofitCore ==null) {
            mRetrofitCore = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofitCore;
    }
}
