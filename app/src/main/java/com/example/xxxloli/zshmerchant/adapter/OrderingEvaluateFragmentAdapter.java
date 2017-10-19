package com.example.xxxloli.zshmerchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.xxxloli.zshmerchant.fragment.AllEvaluateFragment;
import com.example.xxxloli.zshmerchant.fragment.NoReplyEvaluateFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class OrderingEvaluateFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> list;
    private Fragment noReplyEvaluateFragment, allEvaluateFragment;

    public OrderingEvaluateFragmentAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.list = list;
    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                if (noReplyEvaluateFragment == null)
                    noReplyEvaluateFragment = new NoReplyEvaluateFragment("rob", null);
                return noReplyEvaluateFragment;
            case 1:
                if (allEvaluateFragment == null)
                    allEvaluateFragment = new AllEvaluateFragment(null, "ReceivedOrder");
                return allEvaluateFragment;

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
