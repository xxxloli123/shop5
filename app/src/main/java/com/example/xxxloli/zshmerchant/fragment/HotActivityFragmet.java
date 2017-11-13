package com.example.xxxloli.zshmerchant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.xxxloli.zshmerchant.Activity.AddCommodityActivity;
import com.example.xxxloli.zshmerchant.Activity.AddSpecialOfferCommodityActivity;
import com.example.xxxloli.zshmerchant.Activity.CommodityActivity;
import com.example.xxxloli.zshmerchant.Activity.ShopQRcodeActivity;
import com.example.xxxloli.zshmerchant.R;
import com.sgrape.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by xxxloli on 2017/10/17.
 */

public class HotActivityFragmet extends BaseFragment {

    @BindView(R.id.ZDMS)
    Button ZDMS;
    @BindView(R.id.TJSP)
    Button TJSP;
    Unbinder unbinder;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hot_activites;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ZDMS, R.id.TJSP})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.ZDMS:
//                描述：商家申请上传平台活动商品::bargaingoodStr[];type[Bargain(特价商品), Seckill(整点秒杀)
                intent.setClass(getActivity(), AddSpecialOfferCommodityActivity.class);
                intent.putExtra("type", "Seckill");
                startActivity(intent);
                break;
            case R.id.TJSP:
                intent.setClass(getActivity(), AddSpecialOfferCommodityActivity.class);
                startActivity(intent);
                break;
        }
    }
}
