package com.lavekush.telstra.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.lavekush.telstra.util.AppConstant.BASE_URL;


/**
 * Singleton class for creating network to ensure no duplicate client available in the memory
 */
public class RetrofitClient {

    private static Retrofit sRetrofitCore;

    //Private constructor to ensure no can call new
    private RetrofitClient (){

    }

    public static Retrofit getNetworkClient() {

        //If null then create new one
        if (sRetrofitCore == null) {
            sRetrofitCore = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();  // We can add lot of interceptor if require before build method called
        }

        return sRetrofitCore;
    }
}
