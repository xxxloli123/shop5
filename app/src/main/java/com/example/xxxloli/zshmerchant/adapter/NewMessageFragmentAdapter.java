package com.example.xxxloli.zshmerchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.xxxloli.zshmerchant.fragment.AllMessageFragment;
import com.example.xxxloli.zshmerchant.fragment.MeMessageFragment;
import com.example.xxxloli.zshmerchant.fragment.PlatformMessageFragment;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/27 0027.
 */

public class NewMessageFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<String> list;
    private Fragment allMessageFragment, platformMessageFragment, meMessageFragment;

    public NewMessageFragmentAdapter(FragmentManager fm, ArrayList<String> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            type==全部 all 公告 消息 PlatformNotice(平台公告),ShopNews(商家消息);
            case 0:
                if (allMessageFragment == null)
                    allMessageFragment = new AllMessageFragment("all");
                return allMessageFragment;
            case 1:
                if (platformMessageFragment == null)
                    platformMessageFragment = new PlatformMessageFragment("PlatformNotice");
                return platformMessageFragment;
            case 2:
                if (meMessageFragment == null)
                    meMessageFragment = new MeMessageFragment("ShopNews");
                return meMessageFragment;
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
