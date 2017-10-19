package com.example.xxxloli.zshmerchant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.xxxloli.zshmerchant.BaseFragment;
import com.example.xxxloli.zshmerchant.R;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ShopFragment extends BaseFragment{
    View v;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order_handle, null);
        return v;
    }
}
