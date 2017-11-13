package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.adapter.EvaluateListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Message;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class NoReplyEvaluateFragment extends FragEvaluateList {

    public NoReplyEvaluateFragment(){
        super();
    }

    public NoReplyEvaluateFragment( String type){
        super(type);
    }

    @Override
    protected void readInstanceState() {
        super.readInstanceState();
    }
}
