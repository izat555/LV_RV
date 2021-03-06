package com.example.a230618_recyclerview.data.network;

import com.example.a230618_recyclerview.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkBuilder {
    private static RetrofitService sRetrofitService = null;

    public static RetrofitService initRetrofitService() {
        if (sRetrofitService == null) {
            sRetrofitService = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(RetrofitService.class);
        }

        return sRetrofitService;
    }

    private static OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(
                        new Interceptor() {
                            @Override
                            public Response intercept(Chain chain) throws IOException {
                                Request.Builder ongoing = chain.request()
                                        .newBuilder()
                                        .addHeader("Accept", "application/json;versions=1");


                                return chain.proceed(ongoing.build());
                            }
                        }
                ).build();
    }
}
