package com.example.xxxloli.zshmerchant;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.util.CacheActivity;
import com.example.xxxloli.zshmerchant.util.SaveUtils;
import com.example.xxxloli.zshmerchant.view.SystemBarTintManager;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/19 .
 * 项目中所有的activity继承基类BaseActivity做统一处理
 */
public class BaseActivity extends AppCompatActivity implements Callback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.blue);//状态栏设置为透明色
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setNavigationBarTintResource(Color.TRANSPARENT);//导航栏设置为透明色
        }
        //统一使用框架
        /**
         * view绑定框架
         *ButterKnife.bind(this);
         */
        /**
         * 图片处理框架可查看api了解属性
         *  Picasso.with(this).load(url).into(view);
         */
    }

    protected void init() {
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    /**
     * 返回及返回动画
     */
    public void onBack() {
        finish();
//        activity.getParent().overridePendingTransition(
//                    R.anim.in_from_right, R.anim.out_to_left);
    }

    /**
     * 安全退出账号清除保存的数据
     */
    public void exitLogin() {
        SaveUtils.clear(this);
        /**
         * 根据退出时的情况是否有必要清除除登录以外的activity页面
         * 属于优化内存
         */
        CacheActivity.finishActivity();
    }

    @Override
    public void finish() {
        if (CacheActivity.activityList.contains(this)) CacheActivity.activityList.remove(this);
        super.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onFailure(final Call call, final IOException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BaseActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
                onFail(call, e);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        final String jsonStr = response.body().string();
        System.out.println(jsonStr);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject json = new JSONObject(jsonStr);
                    if (json.getInt("statusCode") == 200)
                        onSuccess(call, response, json);
                    else{
                        Toast.makeText(BaseActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                        onFail(call, null);
                    }
                } catch (JSONException e) {
                    Toast.makeText(BaseActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    protected void onFail(Call call, IOException e) {

    }

    protected void onSuccess(Call call, Response response, JSONObject json) throws JSONException {

    }

    protected boolean isEmpty(CharSequence txt) {
        return TextUtils.isEmpty(txt);
    }

    protected <T extends View> T findView(@IdRes int id) {
        return (T) findViewById(id);
    }
}
