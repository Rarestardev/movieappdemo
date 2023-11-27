package com.rarestar.empirebesttv.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_client {
    private static Retrofit retrofit;
    public static final String url = "http://192.168.197.39/app/";
    public static Retrofit_client client;

    public Retrofit_client(){
        retrofit= new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized Retrofit_client getInstance(){
        if (client == null){
            client = new Retrofit_client();
        }
        return client;
    }
    public Retrofit_interface getApi(){
        return retrofit.create(Retrofit_interface.class);
    }
}
