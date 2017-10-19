package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.OrderingEvaluateFragmentAdapter;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderingEvaluateActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.commodityTab)
    PagerSlidingTabStrip commodityTab;
    @BindView(R.id.show_viewpager)
    ViewPager showViewpager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_evaluate);
        ButterKnife.bind(this);
        initPagers();
    }


    /**
     * 根据选择的不同界面选择不同tabs
     */
    private void initPagers() {
        ArrayList<String> list = new ArrayList<>();
        list.add("未回复");
        list.add("全部评价");

        showViewpager.setAdapter(new OrderingEvaluateFragmentAdapter(this.getSupportFragmentManager(), list));
        commodityTab.setViewPager(showViewpager);
        showViewpager.setCurrentItem(0);
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }
}
