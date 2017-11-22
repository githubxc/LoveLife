package com.xc.lovelife.utils;

import android.util.Log;

import com.xc.lovelife.apimanager.Api;
import com.xc.lovelife.event.ImgService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by xum19 on 2017/11/22.
 */

public class RetrofitUtil {

    private volatile static RetrofitUtil sInstance;
    private Retrofit mRetrofit;
    private ImgService mImgService;
    private RetrofitUtil(){
        Log.i("RetrofitUtil: ", Api.url);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Api.url + "/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mImgService = mRetrofit.create(ImgService.class);
    }
    public static RetrofitUtil getInstance(){
        if (sInstance == null){
            synchronized(RetrofitUtil.class){
                if (sInstance == null){
                    sInstance = new RetrofitUtil();
                }
            }
        }
        return sInstance;
    }
    public ImgService getmImgService(){
        return mImgService;
    }
}
