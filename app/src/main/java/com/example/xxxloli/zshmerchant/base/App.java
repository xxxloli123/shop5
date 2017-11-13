package com.example.xxxloli.zshmerchant.base;

import android.app.Application;

/**
 * app初始化
 *
 * Created by liugruirong on 2017/8/3.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppInfo.init(getApplicationContext());
    }
}
