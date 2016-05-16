package com.sfj.android.view.module;

import com.sfj.android.requests.MainRequest;
import com.sfj.android.requests.UserRequest;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/5/6.
 */
@Module
public class ApiServiceModule {
    private static final String BASE_URL = "http://www.shifangju.com/m/";

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(){
        return new OkHttpClient();
    }


    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Provides
    @Singleton
    public MainRequest provideMainRequest(Retrofit retrofit){
        return retrofit.create(MainRequest.class);
    }

    @Provides
    @Singleton
    public UserRequest provideUserRequest(Retrofit retrofit){
        return retrofit.create(UserRequest.class);
    }

}
