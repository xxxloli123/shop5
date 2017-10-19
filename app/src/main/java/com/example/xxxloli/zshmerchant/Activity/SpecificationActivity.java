package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.interfaceconfig.Config;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpecificationActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specification);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        if (intent.getStringExtra("productId")==null){
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            return;
        }
        //声明WebSettings子类
        WebSettings webSettings = web.getSettings();
        //设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        //方式1. 加载一个网页：
        web.loadUrl(Config.Specification+intent.getStringExtra("productId"));
        //步骤3. 复写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        web.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }
}
