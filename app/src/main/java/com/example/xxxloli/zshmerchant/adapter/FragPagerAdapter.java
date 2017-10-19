package com.example.xxxloli.zshmerchant.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sgrape.BaseFragment;

import java.util.List;

/**
 * Created by sgrape on 2017/5/24.
 * e-mail: sgrape1153@gmail.com
 */

public class FragPagerAdapter extends FragmentPagerAdapter {

    private final String[] titles;
    protected List<BaseFragment> fragmentList;

    public FragPagerAdapter(FragmentManager fm, List<BaseFragment> frags, String... titles) {
        super(fm);
        this.fragmentList = frags;
        this.titles = titles;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return (titles != null && titles.length > position ) ? titles[position] : "";
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

}
