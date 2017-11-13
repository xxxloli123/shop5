package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.CWHYSYHJActivity;
import com.example.xxxloli.zshmerchant.Activity.FXGZSYHQActivity;
import com.example.xxxloli.zshmerchant.Activity.MJActivity;
import com.example.xxxloli.zshmerchant.Activity.MMPSFHDActivity;
import com.example.xxxloli.zshmerchant.Activity.MZActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.ActivitesAdapter;
import com.example.xxxloli.zshmerchant.adapter.ActivityCommodityAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Activites;
import com.example.xxxloli.zshmerchant.objectmodel.ActivityCommodity;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xxxloli on 2017/10/17.
 */

public class AlreadyParticipationFragmet extends BaseFragment implements ActivityCommodityAdapter.Callback{

    Unbinder unbinder;
    @BindView(R.id.show)
    ListView show;

    private ActivityCommodityAdapter activityCommodityAdapter;
    private ArrayList<ActivityCommodity> activityCommodities;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_already_build;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.DELETE_ActivityCommodity:
                Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                initView();
                break;
            case Config.GET_ActivityCommodity:
                JSONArray arr = json.getJSONArray("list");
                if (arr.length() == 0) return;
                 activityCommodities= new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    activityCommodities.add(gson.fromJson(arr.getString(arr.length()-i-1), ActivityCommodity.class));
                }
                if (activityCommodityAdapter!=null)return;
                activityCommodityAdapter = new ActivityCommodityAdapter(getActivity(), activityCommodities,this);
                show.setAdapter(activityCommodityAdapter);
                activityCommodityAdapter.refresh(activityCommodities);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        dbManagerShop=DBManagerShop.getInstance(getActivity());
        shop=dbManagerShop.queryById((long) 2333).get(0);
        return rootView;
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
        newCall(Config.Url.getUrl(Config.GET_ActivityCommodity), params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void click(final View v) {
        switch (v.getId()){
            case R.id.delete_TV:
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sure, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
                TextView title = view1.findViewById(R.id.title);
                Button sure = view1.findViewById(R.id.sure_bt);
                Button cancel = view1.findViewById(R.id.cancel_bt);
                title.setText("确认下架吗");
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> params = new HashMap<>();
//                        商家下架已上传的平台活动商品::bargaingoodsId:商品id 参数：[bargaingoodsId, userId]
                        params.put("bargaingoodsId", activityCommodities.get((Integer) v.getTag()).getId());
                        params.put("userId", shop.getShopkeeperId());
                        newCall(Config.Url.getUrl(Config.DELETE_ActivityCommodity), params);
                        //List移除某Item
                        activityCommodities.remove(activityCommodities.get((Integer) v.getTag()));
                        activityCommodityAdapter.refresh(activityCommodities);
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
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
}
