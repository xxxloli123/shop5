package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.FragPagerAdapter;
import com.example.xxxloli.zshmerchant.fragment.FeedbackRecordFragment;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;
import com.sgrape.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/10/24.
 */

public class FeedbackRecordActivity extends AppCompatActivity{
    @BindView(R.id.page_tabs)PagerSlidingTabStrip tabs;
    @BindView(R.id.page)ViewPager page;
    private String[] exceptionType ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_recird);
        ButterKnife.bind(this);
        exceptionType = new String[3];
        exceptionType[0] = getString(R.string.functionException);
        exceptionType[1] = getString(R.string.distributionProblem);
        exceptionType[2] =  getString(R.string.productSuggestion);
        ArrayList<BaseFragment> list = new ArrayList<>();
        list.add(new FeedbackRecordFragment());
        list.add(new FeedbackRecordFragment());
        list.add(new FeedbackRecordFragment());
        page.setAdapter(new FragPagerAdapter(getSupportFragmentManager(),list,getString(R.string.terraceFunctionException),getString(R.string.distributionIssue), getString(R.string.productSuggest)));
        tabs.setViewPager(page);
        page.setCurrentItem(0);
        tabs.setOnPagerTitleItemClickListener(new PagerSlidingTabStrip.OnPagerTitleItemClickListener() {
            @Override
            public void onSingleClickItem(int position) {
                //((FeedbackRecordFragment)page.getAdapter().instantiateItem(page,position))
                // .setUrl(exceptionType[position]);
            }

            @Override
            public void onDoubleClickItem(int position) {
            }
        });
        page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
//                ((FeedbackRecordFragment)page.getAdapter().instantiateItem(page,position)).setUrl(exceptionType[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
    @OnClick({R.id.back_rl})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
        }
    }
}
