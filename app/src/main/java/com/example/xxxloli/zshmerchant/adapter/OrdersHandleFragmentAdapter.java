package com.example.xxxloli.zshmerchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.xxxloli.zshmerchant.fragment.GetShopExpenseOrderHandleFragment;
import com.example.xxxloli.zshmerchant.fragment.HomeDeliveryOrderHandleFragment;
import com.example.xxxloli.zshmerchant.fragment.NewOrderHandleFragment;
import com.example.xxxloli.zshmerchant.fragment.ReserveOrderHandleFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class OrdersHandleFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> list;
    private Fragment newOrderHandleFragment, homeDeliveryOrderHandleFragment, reserveOrderHandleFragment
            , getShopExpenseOrderHandleFragment;

    public OrdersHandleFragmentAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.list = list;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                //订单处理 lineOrderType： 所有新订单 All，ReserveOrder(预定单),NormalOrder(配送到家),ShopConsumption(到店消费单)
                if (newOrderHandleFragment == null)
                    newOrderHandleFragment = new NewOrderHandleFragment("All");
                return newOrderHandleFragment;
            case 1:
                if (homeDeliveryOrderHandleFragment == null)
                    homeDeliveryOrderHandleFragment = new HomeDeliveryOrderHandleFragment("NormalOrder");
                return homeDeliveryOrderHandleFragment;
            case 2:
                if (reserveOrderHandleFragment == null)
                    reserveOrderHandleFragment = new ReserveOrderHandleFragment("ReserveOrder");
                return reserveOrderHandleFragment;
            case 3:
                if (getShopExpenseOrderHandleFragment == null)
                    getShopExpenseOrderHandleFragment = new GetShopExpenseOrderHandleFragment("ShopConsumption");
                return getShopExpenseOrderHandleFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
