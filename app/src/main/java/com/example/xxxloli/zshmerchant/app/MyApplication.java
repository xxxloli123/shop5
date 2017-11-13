package com.example.xxxloli.zshmerchant.app;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.slowlife.xgpush.XgReceiver;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */

public class MyApplication extends Application {

    public static Context mContext;
    private String token = "";

    @Override
    public void onCreate() {
        super.onCreate();
        if (isMainProcess()) {
            // 为保证弹出通知前一定调用本方法，需要在application的onCreate注册
            // 收到通知时，会调用本回调函数。
            // 相当于这个回调会拦截在信鸽的弹出通知之前被截取
            // 一般上针对需要获取通知内容、标题，设置通知点击的跳转逻辑等等
            XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {

                @Override
                public void handleNotify(XGNotifaction xGNotifaction) {
                    Log.i("test", "处理信鸽通知：" + xGNotifaction);
                    // 获取标签、内容、自定义内容
                    String title = xGNotifaction.getTitle();
                    String content = xGNotifaction.getContent();
                    String customContent = xGNotifaction.getCustomContent();
                    XgReceiver.PushObserver.notifyPsuh("title=" + title +
                            "\ncontent=" + content +
                            "\ncustomContent=" + customContent);
                    // 其它的处理
                    // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                    xGNotifaction.doNotify();
                }
            });
        }
    }

    public String getToken() {
        return token;
    }


    public boolean isMainProcess() {
        ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
        String mainProcessName = getPackageName();
        int myPid = android.os.Process.myPid();
        for (ActivityManager.RunningAppProcessInfo info : processInfos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }
}
