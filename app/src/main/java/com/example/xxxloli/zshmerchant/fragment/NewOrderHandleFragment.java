package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.OrderListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.interfaceconfig.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class NewOrderHandleFragment extends FragOrderList {

    public NewOrderHandleFragment(){
        super();
    }

    public NewOrderHandleFragment(String lineOrderType){
        super(lineOrderType);
    }

    @Override
    protected void readInstanceState() {
        super.readInstanceState();
    }

}
