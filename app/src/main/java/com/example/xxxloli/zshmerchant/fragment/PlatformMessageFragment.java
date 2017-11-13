package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.MessageContentActivity;
import com.example.xxxloli.zshmerchant.adapter.MessageListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class PlatformMessageFragment extends FragMessageList {

    public PlatformMessageFragment(){
        super();
    }

    public PlatformMessageFragment( String status){
        super(status);
    }

    @Override
    protected void readInstanceState() {
        super.readInstanceState();
    }
}
