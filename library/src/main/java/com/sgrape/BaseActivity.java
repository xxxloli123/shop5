package com.sgrape;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.interfaceconfig.Config;
import com.sgrape.http.OkHttpCallback;
import com.sgrape.library.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by sgrape on 2017/5/23.
 * e-mail: sgrape1153@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity implements OkHttpCallback.Impl, View.OnClickListener {

    protected OkHttpClient httpClient;
    private Unbinder unbinder;
    protected OkHttpCallback httpCallback = new OkHttpCallback(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpClient = new OkHttpClient();
        setContentView(getLayoutId());
        init();
        initListener();
        initData();
    }

    protected void initData() {

    }

    protected void init() {
    }

    protected void initListener() {

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.header_back) {
            finish();
        }
    }

    protected abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected <T> T searchView(@IdRes int id) {
        return (T) findViewById(id);
    }

    @Override
    public void fail(Object tag, int code, JSONObject json) throws JSONException {
        System.out.println("fail");
        if (json != null) {
            Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        super.onDestroy();
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }

    protected void newCall(String url, Map<String, ? extends Object> params) {
        if (isEmpty(url)) {
            Toast.makeText(this, "url = null", Toast.LENGTH_SHORT).show();
            System.out.println("url = null");
            return;
        }
        MultipartBody.Builder requestBuilder = null;
        if (params != null) {
            requestBuilder = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM);
            Set<String> keys = params.keySet();
            Iterator iterator = keys.iterator();
            while (iterator.hasNext()) {
                final String key = (String) iterator.next();
                requestBuilder.addFormDataPart(key, String.valueOf(params.get(key)));
            }
        }
        String tag = url;
        if (url.startsWith(Config.LOCAL_HOST)) {
            tag = url.substring(Config.LOCAL_HOST.length());
        } else if (url.startsWith(Config.HOST)) {
            tag = url.substring(Config.HOST.length());
        }
        Request.Builder request = new Request.Builder()
                .tag(tag).url(url);
        if (requestBuilder == null)
            request.post(RequestBody.create(MediaType.parse("application/json"), "1"));
        else request.post(requestBuilder.build());
        newCall(request.build());
    }

    protected void newCall(Request request) {
        httpClient.newCall(request).enqueue(httpCallback);
    }

    protected boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        else return false;
    }


}
