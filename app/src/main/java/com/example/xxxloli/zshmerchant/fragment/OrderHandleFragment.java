package com.example.xxxloli.zshmerchant.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xxxloli.zshmerchant.BaseFragment;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.OrdersHandleFragmentAdapter;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;
import com.example.xxxloli.zshmerchant.view.ShSwitchView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/13.
 */

public class OrderHandleFragment extends BaseFragment {
    View v;
    @BindView(R.id.is_automatic_order)
    ShSwitchView isAutomaticOrder;
    @BindView(R.id.printer_img)
    ImageView printerImg;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order_handle, null);
        unbinder = ButterKnife.bind(this, v);
        initPagers();
        return v;
    }

    /**
     * 根据选择的不同界面选择不同tabs
     */
    private void initPagers() {
        ArrayList<String> list = new ArrayList<>();
        list.add("新订单");
        list.add("配送到家");
        list.add("预定单");
        list.add("到店消费");

        pager.setAdapter(new OrdersHandleFragmentAdapter(getActivity().getSupportFragmentManager(), list));
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.printer_img)
    public void onViewClicked() {
    }
}
