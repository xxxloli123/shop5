package com.example.xxxloli.zshmerchant.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by sgrape on 2017/5/16.
 * e-mail: sgrape1153@gmail.com
 */

public abstract class SimpleCallback implements Callback {
    private Context context;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SimpleCallback(Context context) {
        this(context, null);
    }

    public SimpleCallback() {
    }

    public SimpleCallback(Context context, SwipeRefreshLayout srl) {
        this.context = context;
        swipeRefreshLayout = srl;
    }

    protected Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case 0:
                        JSONObject json = new JSONObject(msg.obj.toString());
                        System.out.println(json.toString());
                        if (json.getJSONObject("result").getInt("statusCode") == 200)
                            onSuccess(json.getString("tag"), json.getJSONObject("result"));
                        else {
                            onFail(json);
                        }
                        break;
                    case 1:
                        onFail((Call) msg.obj);
                }
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public void onFailure(Call call, IOException e) {
        handler.obtainMessage(1, call).sendToTarget();
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        JSONObject json = new JSONObject();
        try {
            json.put("tag", response.request().tag().toString());
            json.put("result", new JSONObject(response.body().string()));
            handler.obtainMessage(0, json).sendToTarget();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public abstract void onSuccess(String tag, JSONObject json) throws JSONException;

    public void onFail(Call call) {
        if (context != null)
            Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
    }

    public void onFail(JSONObject json) throws JSONException {
        if (context != null)
            Toast.makeText(context, json.getJSONObject("result").getString("message"), Toast.LENGTH_SHORT).show();
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
}
