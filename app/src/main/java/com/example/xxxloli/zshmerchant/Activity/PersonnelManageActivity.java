package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.PersonnelManageAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.helper.RecyclerLimit;
import com.interfaceconfig.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PersonnelManageActivity extends AppCompatActivity {
    @BindView(R.id.recycler_chef) RecyclerView recyclerChef;
    @BindView(R.id.recycler_waiter) RecyclerView recyclerWaiter;

    private PersonnelManageAdapter chefAdapter,waiterAdapter;
    private List<String[]> chefs,waiters;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personnel_manage);
        ButterKnife.bind(this);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);

        chefs = new LinkedList<>();
        chefs.add(new String[]{getString(R.string.addPersonnel)});
        waiters = new LinkedList<>();
        waiters.add(new String[]{getString(R.string.addPersonnel)});
        chefAdapter = new PersonnelManageAdapter(this,"ShopCook",chefs);
        waiterAdapter = new PersonnelManageAdapter(this,"ShopWaiter",waiters);
        linkNet();
        int lineHeight = (int)getResources().getDimension(R.dimen.line1);
        recyclerChef.setItemAnimator(new DefaultItemAnimator());
        recyclerWaiter.setItemAnimator(new DefaultItemAnimator());
        recyclerChef.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerWaiter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerChef.addItemDecoration(new RecyclerLimit(this, (byte) LinearLayout.HORIZONTAL,lineHeight, ContextCompat.getColor(this, R.color.background)));
        recyclerWaiter.addItemDecoration(new RecyclerLimit(this, (byte)LinearLayout.HORIZONTAL,lineHeight,ContextCompat.getColor(this, R.color.background)));
        recyclerChef.setAdapter(chefAdapter);
        recyclerWaiter.setAdapter(waiterAdapter);
    }
    @OnClick({R.id.back_rl, R.id.text_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
            case R.id.text_confirm:
                finish();
                break;
        }
    }
    private void linkNet(){
        Request.Builder request = new Request.Builder().url(Config.Url.getUrl(Config.getAllStaff));
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("shopkeeperId", shop.getShopkeeperId());
        requestBody.addFormDataPart("shopId", shop.getId());
        request.method("POST", requestBody.build());
        new OkHttpClient().newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(PersonnelManageActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final int responseCode = response.code();
                final String responseResult = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (responseCode != 200) {
                            Toast.makeText(PersonnelManageActivity.this, "获取失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        try {
                            JSONObject jsonObj = new JSONObject(responseResult).getJSONArray("list").getJSONObject(0);
                            JSONArray cookArray = jsonObj.getJSONArray("ShopCook");
                            for(int index = 0;index<cookArray.length();index++) {
                                JSONObject cook = cookArray.getJSONObject(index);
                                chefs.add(chefs.size() -1, new String[]{cook.getString("name"),cook.getString("phone"),cook.getString("id"),cook.getString("status")});
                            }
                            recyclerChef.getAdapter().notifyDataSetChanged();
                            JSONArray waiterArrat = jsonObj.getJSONArray("ShopWaiter");
                            for(int index = 0;index<waiterArrat.length();index++) {
                                JSONObject waiter = waiterArrat.getJSONObject(index);
                                waiters.add(waiters.size()-1, new String[]{waiter.getString("name"),waiter.getString("phone"),waiter.getString("id"),waiter.getString("status")});
                            }
                            recyclerWaiter.getAdapter().notifyDataSetChanged();
                        } catch (JSONException e) {
                            Toast.makeText(PersonnelManageActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
