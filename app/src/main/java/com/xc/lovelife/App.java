package com.xc.lovelife;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by xum19 on 2017/11/22.
 */

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob数据
        Bmob.initialize(this, "7b4254208ded45ed2a03b9deba8c3deb");


    }
}
