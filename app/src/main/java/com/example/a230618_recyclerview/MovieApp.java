package com.example.a230618_recyclerview;

import android.app.Application;
import android.content.Context;

import com.example.a230618_recyclerview.data.network.NetworkBuilder;
import com.example.a230618_recyclerview.data.network.RetrofitService;

public class MovieApp extends Application {
    private RetrofitService mRetrofitService;

    @Override
    public void onCreate() {
        super.onCreate();
        mRetrofitService = NetworkBuilder.initRetrofitService();
    }

    public static MovieApp get(Context context) {
        return (MovieApp) context.getApplicationContext();
    }

    public RetrofitService getRetrofitService() {
        return mRetrofitService;
    }
}
