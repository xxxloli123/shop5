package com.example.xxxloli.zshmerchant.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
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
//    public static final String URI = "uri";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specification);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        if (intent.getStringExtra("productId")==null){
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        init();
    }

    @SuppressLint("JavascriptInterface")
    private void init() {
//        String url = getIntent().getStringExtra(URI);
        WebSettings setting = web.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(false);
        setting.setAppCacheEnabled(true);
        setting.setUseWideViewPort(true);//关键点
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setDisplayZoomControls(false);
        setting.setAllowFileAccess(false); // 允许访问文件
        setting.setBuiltInZoomControls(false); // 设置显示缩放按钮
        setting.setSupportZoom(false); // 支持缩放
        setting.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web.addJavascriptInterface(new JavaScriptCallAndroid(), "Android");
//        webView.loadUrl("file:///android_asset/test.html");
        web.loadUrl(Config.Url.getUrl(Config.Specification)+getIntent().getStringExtra("productId"));
//        webView.loadDataWithBaseURL(Config.LOCAL_HOST, null, "html/text", "utf-8",
//                Config.Url.getUrl("slowlife/app/appGeneral/ruleGZ.html"));
        web.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();
                view.loadUrl(url);
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    class JavaScriptCallAndroid {
//        @JavascriptInterface
//        public void tel(String phone) {
//            //用intent启动拨打电话
//            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
//            startActivity(intent);
//        }

        @JavascriptInterface
        public void goBack() {
            if (web.canGoBack()) web.goBack();
            else finish();
        }
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (web.canGoBack()) web.goBack();
            else finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
