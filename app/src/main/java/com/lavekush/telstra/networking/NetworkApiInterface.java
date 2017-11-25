package com.lavekush.telstra.networking;

import com.lavekush.telstra.vo.DataContainer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NetworkApiInterface {

    @GET("2iodh4vg0eortkl/facts.json")
    Call<ResponseBody> getRowData();
}