package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.CommodityAdapter;
import com.example.xxxloli.zshmerchant.adapter.CommunityAdapter;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.util.GreenDaoHelp;
import com.example.xxxloli.zshmerchant.util.ToastUtil;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommunityActivity extends BaseActivity implements CommunityAdapter.Callback{

    @BindView(R.id.community_list)
    MyListView communityList;

    private Shop shop;
    private CommunityAdapter communityAdapter;
    private ArrayList<Classify> classifies;
    private String[] shopTransportArea={};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_community;
    }

    @Override
    protected void init() {
        shop= GreenDaoHelp.GetShop(this);
        classifies=new ArrayList<>();
        if (shop.getShopTransportArea()!=null)shopTransportArea=shop.getShopTransportArea().split(",");
        Log.e("shopTransportArea","丢了个雷姆"+ Arrays.toString(shopTransportArea));
        for (String aShopTransportArea : shopTransportArea) {
            Classify classify = new Classify();
            classify.setProductClassName(aShopTransportArea);
            classifies.add(classify);
        }
        communityAdapter=new CommunityAdapter(this,classifies,this);
        communityList.setAdapter(communityAdapter);
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        ToastUtil.showToast(this,json.getString("message"));
        GreenDaoHelp.UpdateShop(shop);
    }

    @OnClick({R.id.back_rl, R.id.add_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.add_tv:
                add();
                break;
        }
    }

    private void add() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_classify_1, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        final Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final EditText classify_name = view.findViewById(R.id.classify_name);
        classify_name.setHint("请输入小区名");
        final EditText number_ET = view.findViewById(R.id.number_ET);
        number_ET.setVisibility(View.GONE);
            TextView title=view.findViewById(R.id.title);
            title.setText("添加小区");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(classify_name.getText().toString())){
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
                    shopStr.put("id", shop.getId());
                    shopStr.put("shopTransportArea", classify_name.getText().toString());

                    params.put("shopStr", shopStr);
                    params.put("userId", shop.getShopkeeperId());
                    Log.e("EDIT_SHOP_INFO","丢了个雷姆"+params);
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                    shop.setShopTransportArea((classifies.isEmpty())?classify_name.getText().toString():
                            shop.getShopTransportArea()+","+classify_name.getText().toString());
                    Classify classify=new Classify();
                    classify.setProductClassName(classify_name.getText().toString());
                    classifies.add(classify);
                    communityAdapter.refresh(classifies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    public void click(final View v) {
        View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_sure, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView title = view1.findViewById(R.id.title);
        Button sure = view1.findViewById(R.id.sure_bt);
        Button cancel = view1.findViewById(R.id.cancel_bt);
        title.setText("确认删除吗");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
                    shopStr.put("id", shop.getId());
                    shopStr.put("shopTransportArea", classifies.get((Integer) v.getTag())
                            .toString()+",del");

                    params.put("shopStr", shopStr);
                    params.put("userId", shop.getShopkeeperId());
                    Log.e("EDIT_SHOP_INFO","丢了个雷姆"+params);
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                    shop.setShopTransportArea(shop.getShopTransportArea().replace(","+
                            shopTransportArea[(int) v.getTag()],""));
                    classifies.remove((int) v.getTag());
                    communityAdapter.refresh(classifies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view1);
        alertDialog.show();
    }
}
