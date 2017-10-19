package com.sgrape.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by sgrape on 2017/5/23.
 * e-mail: sgrape1153@gmail.com
 */

public class OkHttpCallback implements Callback {
    private Impl impl;
    private Handler handler = null;

    public OkHttpCallback(@NonNull Impl impl) {
        if (impl == null) {
            throw new NullPointerException("impl == null in okhttpcallback");
        }
        this.impl = impl;
        initHandler();
    }

    private void initHandler() {
        if (handler == null)
            handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(impl.getContext(), "网络错误", Toast.LENGTH_SHORT).show();
                try {
                    impl.fail(null, -1, null);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });
        e.printStackTrace();
    }

    @Override
    public void onResponse(Call call, final Response response) throws IOException {
        if (response.code() != 200) {
            final Object tag = response.request().tag();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("http responseCode = " + response.code());
                        Toast.makeText(impl.getContext(), "服务器错误", Toast.LENGTH_SHORT).show();
                        impl.fail(tag, response.code(), null);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                final Object tag = response.request().tag();
                final JSONObject json = new JSONObject(response.body().string());
                Log.d("DEBUG", json.toString());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (json.getInt("statusCode") == 200) {
                                impl.onSuccess(tag, json);
                            } else {
                                Toast.makeText(impl.getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                                Log.e("okhttpCallback", json.toString());
                                impl.fail(tag, response.code(), json);
                            }
                        } catch (JSONException e) {
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public static interface Impl {
        void onSuccess(Object tag, JSONObject json) throws JSONException;

        void fail(Object tag, int code, JSONObject json) throws JSONException;

        @NonNull
        Context getContext();
    }
}
