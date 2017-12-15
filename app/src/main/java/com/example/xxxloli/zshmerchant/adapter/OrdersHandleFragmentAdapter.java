package com.example.xxxloli.zshmerchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.example.xxxloli.zshmerchant.fragment.FragOrderList;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class OrdersHandleFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> list;
    private Fragment newOrderHandleFragment, homeDeliveryOrderHandleFragment, reserveOrderHandleFragment
            , getShopExpenseOrderHandleFragment,UnPayed;

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
                    newOrderHandleFragment = new FragOrderList("All");
                return newOrderHandleFragment;
            case 1:
                if (homeDeliveryOrderHandleFragment == null)
                    homeDeliveryOrderHandleFragment = new FragOrderList("NormalOrder");
                return homeDeliveryOrderHandleFragment;
            case 2:
                if (reserveOrderHandleFragment == null)
                    reserveOrderHandleFragment = new FragOrderList("ReserveOrder");
                return reserveOrderHandleFragment;
            case 3:
                if (getShopExpenseOrderHandleFragment == null)
                    getShopExpenseOrderHandleFragment = new FragOrderList("ShopConsumption");
                return getShopExpenseOrderHandleFragment;
            case 4:
                if (UnPayed == null)
                    UnPayed = new FragOrderList("UnPayed");
                return UnPayed;

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
