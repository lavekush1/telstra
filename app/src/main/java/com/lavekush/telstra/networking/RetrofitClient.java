package com.lavekush.telstra.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lavekush.telstra.util.AppConstant.BASE_URL;

public class RetrofitClient {

    private static Retrofit mRetrofitCore;

    public static Retrofit getNetworkClient() {
        if (mRetrofitCore == null) {
            mRetrofitCore = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofitCore;
    }
}
