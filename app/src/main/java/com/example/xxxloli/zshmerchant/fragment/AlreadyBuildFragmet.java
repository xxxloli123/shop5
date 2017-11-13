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
import com.example.xxxloli.zshmerchant.Activity.SpecificationActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.ActivitesAdapter;
import com.example.xxxloli.zshmerchant.adapter.ClassifyExamineAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Activites;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
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

public class AlreadyBuildFragmet extends BaseFragment implements ActivitesAdapter.Callback{

    Unbinder unbinder;
    @BindView(R.id.show)
    ListView show;

    private ActivitesAdapter activitesAdapter;
    private ArrayList<Activites> activitess;
    public static final String EDIT_Activites = "activites";
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
            case Config.EDIT_Activity_Status:
                Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                initView();
                break;
            case Config.GET_Activity:
                JSONArray arr = json.getJSONArray("listShopActivity");
                if (arr.length() == 0) return;
                activitess = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    activitess.add(gson.fromJson(arr.getString(arr.length()-i-1), Activites.class));
                }
                if (activitesAdapter!=null)return;
                activitesAdapter = new ActivitesAdapter(getActivity(), activitess,this);
                show.setAdapter(activitesAdapter);
                activitesAdapter.refresh(activitess);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        dbManagerShop = DBManagerShop.getInstance(getContext());
        shop = dbManagerShop.queryById((long) 2333).get(0);
        initView();
        return rootView;
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
        newCall(Config.Url.getUrl(Config.GET_Activity), params);
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
                title.setText("确认删除吗");
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> params = new HashMap<>();
//                productClassId 分类ID， userId 用户id
                        params.put("productClassId", activitess.get((Integer) v.getTag()).getId());
                        params.put("userId", shop.getShopkeeperId());
                        newCall(Config.Url.getUrl(Config.DELETE_Classify), params);
                        //List移除某Item
                        activitess.remove(activitess.get((Integer) v.getTag()));
                        activitesAdapter.refresh(activitess);
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
            case R.id.edit_TV:
                Intent intent = null;
                switch (activitess.get((Integer) v.getTag()).getType()){
//                    FullCut("满减活动"),Collection("成为会员送优惠券"),ShareFollow("分享关注送优惠券"),
//                            FullGive("满赠活动")FreeShipping("满免配送费活动");
                    case "Collection":
                        intent = new Intent(getActivity(), CWHYSYHJActivity.class);
                        intent.putExtra(EDIT_Activites, activitess.get((Integer) v.getTag()));
                        startActivity(intent);
                        break;
                    case "FullCut":
                        intent = new Intent(getActivity(), MJActivity.class);
                        intent.putExtra(EDIT_Activites, activitess.get((Integer) v.getTag()));
                        startActivity(intent);
                        break;
                    case "ShareFollow":
                        intent = new Intent(getActivity(), FXGZSYHQActivity.class);
                        intent.putExtra(EDIT_Activites, activitess.get((Integer) v.getTag()));
                        startActivity(intent);
                        break;
                    case "FullGive":
                        intent = new Intent(getActivity(), MZActivity.class);
                        intent.putExtra(EDIT_Activites, activitess.get((Integer) v.getTag()).getId());
                        startActivity(intent);
                        break;
                    case "FreeShipping":
                        intent = new Intent(getActivity(), MMPSFHDActivity.class);
                        intent.putExtra(EDIT_Activites, activitess.get((Integer) v.getTag()).getId());
                        startActivity(intent);
                        break;
                }
                break;
            case R.id.issueORsold_out_TV:
                //        活动状态  Wait_audit("未发布 "), Normal("已发布"), Stop("已下架");
                if (activitess.get((Integer) v.getTag()).getStatus().equals("Normal")){
                    issueORsoldOut("Stop",activitess.get((Integer) v.getTag()).getId());
                    updataItem((Integer) v.getTag(),"已下架","发布");
                }
                else {
                    issueORsoldOut("Normal",activitess.get((Integer) v.getTag()).getId());
                    updataItem((Integer) v.getTag(),"已发布","下架");
                }
                break;
        }
    }

//    getChildAt(position)方法获取到的是当前可见的第position项，获取的时候还需要做一个位置计算
    public  void updataItem(int position,String status,String operation){
        int firstvisible = show.getFirstVisiblePosition();
        int lastvisibale = show.getLastVisiblePosition();
        if(position>=firstvisible&&position<=lastvisibale){
            View view = show.getChildAt(position - firstvisible);
            ActivitesAdapter.ViewHolder viewHolder = (ActivitesAdapter.ViewHolder) view.getTag();
            viewHolder.statusTV.setText(status);
            viewHolder.issueORsoldOutTV.setText(operation);
            //然后使用viewholder去更新需要更新的view。
        }
    }

    private void issueORsoldOut(String status,String id) {
//        shopactivityStr{id;status;[Wait_audit(未发布), Normal(已发布), Stop(已下架);]}
//        参数：[shopactivityStr, userId]
        Map<String, Object> params = new HashMap<>();
        JSONObject shopactivityStr = new JSONObject();
        try {
            shopactivityStr.put("id", id);
            shopactivityStr.put("status", status);

            params.put("shopactivityStr", shopactivityStr);
            params.put("userId", shop.getShopkeeperId());
            newCall(Config.Url.getUrl(Config.EDIT_Activity_Status), params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser)initView();
    }
}
