package com.sgrape;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.interfaceconfig.Config;
import com.sgrape.http.OkHttpCallback;
import com.sgrape.http.OkHttpCallback.Impl;

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

public abstract class BaseFragment extends Fragment implements Impl, View.OnClickListener {

    protected View rootView;
    private Unbinder unbinder;
    protected boolean firstLoad = true;
    private OkHttpCallback callback;
    private OkHttpClient httpClient;

    public BaseFragment() {
        this(new Bundle());
    }

    public BaseFragment(Bundle bundle) {
        setArguments(bundle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            if (getView() != null) rootView = getView();
            else rootView = inflater.inflate(getLayoutId(), null, false);
            readInstanceState();
        }
        unbinder = ButterKnife.bind(this, rootView);
        callback = new OkHttpCallback(this);
        init();
        initListener();
        if (getUserVisibleHint() && firstLoad) {
            loadData();
        }
        if (httpClient == null) httpClient = new OkHttpClient();
        return rootView;
    }

    protected void readInstanceState() {
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && rootView != null && firstLoad) {
            Log.d(getClass().getSimpleName(), "setUserVisibleHint");
            loadData();
        }
    }

    protected void init() {
    }

    protected void initListener() {

    }

    protected void loadData() {
    }


    protected <T> T searchView(@IdRes int id) {
        return (T) getView().findViewById(id);
    }

    @Override
    public void onDestroyView() {
        if (unbinder != null)
            unbinder.unbind();
        Bundle bundle = getArguments();
        bundle.clear();
        super.onDestroyView();
    }

    protected abstract
    @LayoutRes
    int getLayoutId();

    @Override
    public void fail(Object tag, int code, JSONObject json) throws JSONException {
        firstLoad = false;
    }

    @Override
    public Context getContext() {
        if (rootView != null) return rootView.getContext();
        return getView().getContext();
    }


    protected void newCall(String url, Map<String, ? extends Object> params) {
        String tag = url;
        if (url.startsWith(Config.LOCAL_HOST)) {
            tag = url.substring(Config.LOCAL_HOST.length());
        } else if (url.startsWith(Config.HOST)) {
            tag = url.substring(Config.HOST.length());
        }
        newCall(url, params, tag);
    }

    protected void newCall(String url, Map<String, ? extends Object> params, Object tag) {
        if (isEmpty(url)) {
            Toast.makeText(getContext(), "url = null", Toast.LENGTH_SHORT).show();
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
        Request.Builder request = new Request.Builder()
                .tag(tag).url(url);
        if (requestBuilder == null)
            request.post(RequestBody.create(MediaType.parse("application/json"), "1"));
        else request.post(requestBuilder.build());
        if (httpClient == null) httpClient = new OkHttpClient();
        httpClient.newCall(request.build()).enqueue(callback);
    }

    protected boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        else return false;
    }
}
