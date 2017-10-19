package com.example.xxxloli.zshmerchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.danxx.tabstrip.FlymeTabStrip;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.ClassifyAdapter;
import com.example.xxxloli.zshmerchant.adapter.ClassifyExamineAdapter;
import com.example.xxxloli.zshmerchant.adapter.CommodityAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.example.xxxloli.zshmerchant.objectmodel.Tab;
import com.example.xxxloli.zshmerchant.objectmodel.Type;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class CommodityActivity extends BaseActivity implements CommodityAdapter.Callback{

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.classify)
    MyListView classify;
    @BindView(R.id.edit_classify)
    Button editClassify;
    @BindView(R.id.commodityTab)
    FlymeTabStrip commodityTab;
    @BindView(R.id.edit_commodity_2)
    Button editCommodityTab;
    @BindView(R.id.show_viewpager)
    ViewPager showViewpager;
    @BindView(R.id.add_bt)
    Button addBt;

    private ArrayList<Classify> classifies ,classifies2;
    private ArrayList<Tab> tabs = new ArrayList<>();
    private ArrayList<Commodity> commodities ;
    private List<View> viewList;
    private ViewPagerAdapter viewPagerAdapter;
    private ClassifyAdapter classifyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity;
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", "402880e65ed0bda0015ed0c876e00007");
        newCall(Config.Url.getUrl(Config.GET_Classify_1), params);
    }

    private void initClassify2(String fatherId) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", "402880e65ed0bda0015ed0c876e00007");
        params.put("fatherId", fatherId);
        newCall(Config.Url.getUrl(Config.GET_Classify_2), params);
    }

    @OnClick({R.id.back_rl, R.id.edit_classify, R.id.edit_commodity_2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.edit_classify:
                break;
            case R.id.edit_commodity_2:
                break;
        }
    }

    @OnClick(R.id.add_bt)
    public void onViewClicked() {
        startActivity(new Intent(CommodityActivity.this,AppCompatActivity .class));
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        switch (tag.toString()) {
        case Config.GET_Classify_1:
            JSONArray arr = json.getJSONArray("listclass");
            if (arr.length() == 0) return;
            classifies = new ArrayList<>();
            Gson gson = new Gson();
            for (int i = 0; i < arr.length(); i++) {
                classifies.add(gson.fromJson(arr.getString(arr.length()-i-1), Classify.class));
            }
            initClassify2(classifies.get(0).getFatherId());

            classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    classifyAdapter.changeSelected(i);
                    initClassify2(classifies.get(i).getFatherId());
                }
            });
            break;
            case Config.GET_Classify_2:
                JSONArray arr2 = json.getJSONArray("listclass");
                if (arr2.length() == 0) return;
                classifies2 = new ArrayList<>();
                Gson gson2 = new Gson();
                for (int i = 0; i < arr2.length(); i++) {
                    classifies2.add(gson2.fromJson(arr2.getString(arr2.length()-i-1), Classify.class));
                }
                changeTab(classifies2.size());
//                    startActivity(new Intent(ShopInfoActivity.this, LoginActivity.class));
                break;
        }
    }

    private void changeTab(int length) {
        viewList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            View view = LayoutInflater.from(CommodityActivity.this).inflate(R.layout.viewpager_commodity, null);
            viewList.add(view);
        }
        viewPagerAdapter = new ViewPagerAdapter();
        showViewpager.setAdapter(viewPagerAdapter);
        viewPagerAdapter.notifyDataSetChanged();
        commodityTab.setViewPager(showViewpager);
    }

    @Override
    public void click(View v) {
        switch (v.getId()){
            case R.id.edit_bt:

                break;
        }
    }

    private class ViewPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return classifies2.size();
        }

        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            final View view = viewList.get(position);
            final ListView show = view.findViewById(R.id.show_list);

            RequestBody requestBody2 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("shopId", "402880e65ed0bda0015ed0c876e00007")
                    .addFormDataPart("classId", classifies2.get(position).getId()).build();
            Request request = new Request.Builder().url(Config.Url.getUrl(Config.GET_Commodity)).post(requestBody2).build();
            new OkHttpClient().newCall(request).enqueue(new SimpleCallback(CommodityActivity.this) {
                @Override
                public void onSuccess(String tag, JSONObject json) throws JSONException {
                    Toast.makeText(CommodityActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                    JSONArray arr = json.getJSONArray("aaData");
                    if (arr.length() == 0) return;
                    commodities = new ArrayList<>();
                    Gson gson = new Gson();
                    for (int i = 0; i < arr.length(); i++) {
                        commodities.add(gson.fromJson(arr.getString(arr.length() - i - 1), Commodity.class));
                    }
                    CommodityAdapter commodityAdapter = new CommodityAdapter(CommodityActivity.this, commodities,CommodityActivity.this);

                    show.setAdapter(commodityAdapter);
                    container.addView(view);
                }
            });
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
            return classifies.get(position).getProductClassName();
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
