package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.FragPagerAdapter;
import com.example.xxxloli.zshmerchant.fragment.FragLoginPwd;
import com.example.xxxloli.zshmerchant.fragment.FragLoginSms;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;
import com.sgrape.BaseActivity;
import com.sgrape.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/20 0020.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.login_viewpager)
    ViewPager viewPager;
    List<BaseFragment> frags = new ArrayList<>();
    @BindView(R.id.back_rl)
    RelativeLayout backRl;

    /**
     * 初始化控件
     */
    protected void init() {
//        if (((MyApplicatio)getApplication()).getInfo()!=null){
//            startActivity(new Intent(this,MainActivity.class));
//            finish();
//            return;
//        }
        frags.add(new FragLoginPwd());
        frags.add(new FragLoginSms());
        viewPager.setAdapter(new FragPagerAdapter(getSupportFragmentManager(), frags, "服务密码", "短信登录"));
        tabs.setViewPager(viewPager);
    }

    @Override
    protected void initListener() {
        tabs.setOnPagerTitleItemClickListener(new PagerSlidingTabStrip.OnPagerTitleItemClickListener() {
            @Override
            public void onSingleClickItem(int position) {
            }

            @Override
            public void onDoubleClickItem(int position) {
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }
}
