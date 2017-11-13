package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.Classify2Adapter;
import com.example.xxxloli.zshmerchant.adapter.ClassifyAdapter;
import com.example.xxxloli.zshmerchant.adapter.CommodityAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.example.xxxloli.zshmerchant.util.OnItemClickListener;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommodityActivity extends BaseActivity implements CommodityAdapter.Callback,Classify2Adapter.Callback {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.classify)
    MyListView classify;
    @BindView(R.id.edit_classify)
    Button editClassify;

    @BindView(R.id.edit_commodity_2)
    Button editCommodityTab;
    @BindView(R.id.add_bt)
    Button addBt;
    @BindView(R.id.classify_2_show)
    RecyclerView classify2Show;
    @BindView(R.id.commodity_show)
    ListView commodityShow;

    private ArrayList<Classify> classifies, classifies2;
    private ArrayList<Commodity> commodities;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    private CommodityAdapter commodityAdapter;
    private Classify2Adapter classify2Adapter;
    private ClassifyAdapter classifyAdapter;
    private Classify classifyE;
    public static final String EDIT_Commodity = "commodity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_commodity);
        ButterKnife.bind(this);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        initView();
        listener();
    }

    private void listener() {
        classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                classifyAdapter.changeSelected(position);
                initClassify2(classifies.get(position).getId());
                classifyE=classifies.get(position);
            }
        });
    }

    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity;
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
        newCall(Config.Url.getUrl(Config.GET_Classify_1), params);
    }

    private void initClassify2(String fatherId) {
        if (fatherId.equals("")){
            commodities=new ArrayList<>();
            commodityAdapter.refresh(commodities);
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
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
                startActivity(new Intent(this, CommodityClassify_1_Activity.class));
                break;
            case R.id.edit_commodity_2:
                if (classifyE==null){
                    Toast.makeText(this, "请添加一级分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(this, CommodityClassify_2_Activity.class);
                intent.putExtra("fatherId", classifyE.getId());
                startActivity(intent);
                break;
        }
    }

    @OnClick(R.id.add_bt)
    public void onViewClicked() {
        startActivity(new Intent(this, AddCommodityActivity.class));
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_Classify_1:
                JSONArray arr = json.getJSONArray("listclass");
                if (arr.length() == 0)return;
                classifies = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    classifies.add(gson.fromJson(arr.getString(i), Classify.class));
                }
                classifyE=classifies.get(0);
                initClassify2(classifyE.getId());
                classifyAdapter = new ClassifyAdapter(CommodityActivity.this, classifies);
                classify.setAdapter(classifyAdapter);
                break;
            case Config.GET_Classify_2:
                JSONArray arr2 = json.getJSONArray("listclass");
                classifies2 = new ArrayList<>();
                if (arr2.length() == 0){
                    if (classify2Adapter!=null){
                        classify2Adapter.refresh(classifies2);
                        commodities=new ArrayList<>();
                        commodityAdapter.refresh(commodities);
                    }
                    return;
                }
                Gson gson2 = new Gson();
                for (int i = 0; i < arr2.length(); i++) {
                    classifies2.add(gson2.fromJson(arr2.getString(i), Classify.class));
                }
                if (classify2Adapter!=null){
                    classify2Adapter.refresh(classifies2);
                }
                initCommodity(classifies2.get(0).getId());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                classify2Show.setLayoutManager(linearLayoutManager);
                classify2Adapter=new Classify2Adapter(this,classifies2,this);
                classify2Show.setAdapter(classify2Adapter);
                break;
            case Config.GET_Commodity:
                JSONArray arr3 = json.getJSONObject("productsInfo").getJSONArray("aaData");
                commodities = new ArrayList<>();
                if (arr3.length() == 0) {
                    if (commodityAdapter!=null){
                        commodityAdapter.refresh(commodities);
                    }
                    return;
                }
                Gson gson3 = new Gson();
                Log.e("commodities","丢了个雷姆"+json);
                for (int i = 0; i < arr3.length(); i++) {
                    commodities.add(gson3.fromJson(arr3.getString(arr3.length() - i - 1), Commodity.class));
                }
                if (commodityAdapter!=null){
                    commodityAdapter.refresh(commodities);
                    return;
                }
                commodityAdapter=new CommodityAdapter(this,commodities,this);
                commodityShow.setAdapter(commodityAdapter);
                break;
            case Config.EDIT_CommodityStatus:
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.edit_bt:
                Intent intent = new Intent(this, EditCommodityActivity.class);
                intent.putExtra(EDIT_Commodity, (Serializable) commodities.get((Integer) v.getTag()));
                startActivity(intent);
                break;
            case R.id.suspension_of_sale:
                //                status 取值：：：{Wait_audit(未发布 ), Normal(已发布), Stop(已下架)};]
                if (commodities.get((Integer) v.getTag()).getStatus().equals("Normal")){
                    putawayORsoldOut((Integer) v.getTag(),"Stop");
                }else putawayORsoldOut((Integer) v.getTag(),"Normal");
                break;
            case R.id.classify_name:
                classify2Adapter.changeSelected((Integer) v.getTag());
                initCommodity(classifies2.get((Integer) v.getTag()).getId());
                break;
        }
    }

    //    getChildAt(position)方法获取到的是当前可见的第position项，获取的时候还需要做一个位置计算
    public  void updataItem(int position){
        int firstvisible = commodityShow.getFirstVisiblePosition();
        int lastvisibale = commodityShow.getLastVisiblePosition();
        if(position>=firstvisible&&position<=lastvisibale){
            View view = commodityShow.getChildAt(position - firstvisible);
            CommodityAdapter.ViewHolder viewHolder = (CommodityAdapter.ViewHolder) view.getTag();
            if (commodities.get(position).getStatus().equals("Normal")){
                viewHolder.backgroundLL.setBackgroundResource(R.color.hint1_text_color);
                viewHolder.suspensionOfSale.setText("上架");
            }else {
                viewHolder.backgroundLL.setBackgroundResource(R.color.white);
                viewHolder.suspensionOfSale.setText("停售");
            }
            //然后使用viewholder去更新需要更新的view。
        }
    }

    private void putawayORsoldOut(int p,String status) {
//        更改商品状态::productStr[id商品ID；status 取值：：：{Wait_audit(未发布 ), Normal(已发布), Stop(已下架)};]
//        参数：[productStr]
        Map<String, Object> params1 = new HashMap<>();
        JSONObject productStr = new JSONObject();
        try {
            productStr.put("id", commodities.get(p).getId());
            productStr.put("status", status);
            params1.put("productStr", productStr);
            newCall(Config.Url.getUrl(Config.EDIT_CommodityStatus), params1);
            updataItem(p);
            commodities.get(p).setStatus(status);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initCommodity(String classId) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
        params.put("classId", classId);
        newCall(Config.Url.getUrl(Config.GET_Commodity), params);
    }
}
