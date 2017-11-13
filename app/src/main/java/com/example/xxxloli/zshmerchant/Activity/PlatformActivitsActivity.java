package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.fragment.AlreadyBuildFragmet;
import com.example.xxxloli.zshmerchant.fragment.AlreadyParticipationFragmet;
import com.example.xxxloli.zshmerchant.fragment.HotActivityFragmet;
import com.example.xxxloli.zshmerchant.fragment.NewBuildFragmet;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlatformActivitsActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.commodityTab)
    PagerSlidingTabStrip commodityTab;
    @BindView(R.id.show_viewpager)
    ViewPager showViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_platform_activits);
        ButterKnife.bind(this);
        initPagers();
    }

    /**
     * 根据选择的不同界面选择不同tabs
     */
    private void initPagers() {
        ArrayList<String> list = new ArrayList<>();
        list.add("热门活动");
        list.add("已参与");

        showViewpager.setAdapter(new Adapter(this.getSupportFragmentManager(), list));
        commodityTab.setViewPager(showViewpager);
        showViewpager.setCurrentItem(0);
    }


    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }

    public class Adapter extends FragmentPagerAdapter {
        private ArrayList<String> list;
        private Fragment newBuildFragmet, alreadyBuildFragmet;

        public Adapter(FragmentManager fm, ArrayList<String> list) {
            super(fm);
            this.list = list;
        }



        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (newBuildFragmet == null)
                        newBuildFragmet = new HotActivityFragmet();
                    return newBuildFragmet;
                case 1:
                    if (alreadyBuildFragmet == null)
                        alreadyBuildFragmet = new AlreadyParticipationFragmet();
                    return alreadyBuildFragmet;

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

}
