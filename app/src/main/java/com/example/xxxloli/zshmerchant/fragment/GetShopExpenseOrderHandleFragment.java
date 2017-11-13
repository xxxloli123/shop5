package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.adapter.OrderListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/25.
 */
@SuppressLint("ValidFragment")

public class GetShopExpenseOrderHandleFragment extends FragOrderList {

    public GetShopExpenseOrderHandleFragment(){
        super();
    }

    public GetShopExpenseOrderHandleFragment(String lineOrderType){
        super(lineOrderType);
    }

}
