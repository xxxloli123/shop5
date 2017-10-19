package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.NewMessageFragmentAdapter;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewMessageActivity extends AppCompatActivity {

    @BindView(R.id.show_viewpager)
    ViewPager showViewpager;
    @BindView(R.id.commodityTab)
    PagerSlidingTabStrip commodityTab;
    @BindView(R.id.back_rl)
    RelativeLayout backRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        ButterKnife.bind(this);
        initPagers();
    }

    /**
     * 根据选择的不同界面选择不同tabs
     */
    private void initPagers() {
        ArrayList<String> list = new ArrayList<>();
        list.add("全部");
        list.add("平台公告");
        list.add("我的消息");

        showViewpager.setAdapter(new NewMessageFragmentAdapter(this.getSupportFragmentManager(), list));
        commodityTab.setViewPager(showViewpager);
        showViewpager.setCurrentItem(0);
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }
}
