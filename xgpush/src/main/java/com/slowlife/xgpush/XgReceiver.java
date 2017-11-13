package com.slowlife.xgpush;

import android.app.Notification;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.widget.Toast;

import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sgrape on 2017/4/29.
 */

public class XgReceiver extends XGPushBaseReceiver {

    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {
        System.out.println(xgPushRegisterResult);
    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {
        System.out.println(s);
    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {
        System.out.println(s);
    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage xgPushTextMessage) {
        System.out.println(xgPushTextMessage);
        PushObserver.notifyPsuh("title=" + xgPushTextMessage.getTitle() +
                "\ncontent=" + xgPushTextMessage.getContent() +
                "\ncustomContent=" + xgPushTextMessage.getCustomContent());
        XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
        build.setSound(
                RingtoneManager.getActualDefaultRingtoneUri(
                        context, RingtoneManager.TYPE_ALARM)) // 设置声音

                .setDefaults(Notification.DEFAULT_VIBRATE) // 振动
                .setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult message) {
        System.out.println(message);
        if (message.getActionType() == XGPushClickedResult.NOTIFACTION_CLICKED_TYPE) {
            // 通知在通知栏被点击啦。。。。。
            // APP自己处理点击的相关动作
            // 这个动作可以在activity的onResume也能监听，请看第3点相关内容
            String text = "通知被打开 :" + message;
            Toast.makeText(context, "广播接收到通知被点击:" + text,
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult xgPushShowedResult) {
        System.out.println(xgPushShowedResult);
    }


    public static interface PushCallback {

        void onPush(XGPushTextMessage xgPushTextMessage);

        void onPush(String message);
    }

    public static class PushObserver {
        private static List<PushCallback> callbacks = new ArrayList<>();

        public static void regisiter(PushCallback callback) {
            if (!callbacks.contains(callback)) callbacks.add(callback);
        }

        public static void unRegisiter(PushCallback callback) {
            if (callbacks.contains(callback)) callbacks.remove(callback);
        }

        public void clear() {
            callbacks.clear();
        }

        public static void notifyPsuh(XGPushTextMessage xgPushTextMessage) {
            for (PushCallback callback : callbacks) {
                callback.onPush(xgPushTextMessage);
            }
        }

        public static void notifyPsuh(String message) {
            for (PushCallback callback : callbacks) {
                callback.onPush(message);
            }
        }
    }
}
