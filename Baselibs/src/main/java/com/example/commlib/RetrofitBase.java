package com.example.commlib;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitBase {
    public Retrofit getRetrofit() {
        //网络请求
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://47.116.14.251:8888/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit;
    }

}
