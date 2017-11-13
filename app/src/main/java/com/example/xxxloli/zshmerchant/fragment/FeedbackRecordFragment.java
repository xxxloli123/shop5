package com.example.xxxloli.zshmerchant.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.FeedbackRecordAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.helper.RecyclerLimit;
import com.example.xxxloli.zshmerchant.objectmodel.FeedbackRecord;
import com.interfaceconfig.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/24.
 */

public class FeedbackRecordFragment extends com.sgrape.BaseFragment {
    @BindView(R.id.switch_update)SwipeRefreshLayout update;
    @BindView(R.id.recycler)RecyclerView recycler;
    @BindView(R.id.text_noData)TextView noDate;
    private List<FeedbackRecord> data;
    private boolean isAdd,isDisplay ;
    private FeedbackRecordAdapter adapter;
    private Map<String,String> map;
    Unbinder unbinder;

    private DBManagerShop dbManagerShop;
    private Shop shop;

    private SwipeRefreshLayout.OnRefreshListener refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            isAdd = true;
            newCall(Config.Url.getUrl(Config.getPlatformException), map);
        }
    };
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_terrace_function_exception;
    }

    @Override
    public void onClick(View v) {}
    public void setUrl(String exceptionType){
        data.clear();
        map.put("type",exceptionType);
        newCall(Config.Url.getUrl(Config.getPlatformException), map);
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        List<FeedbackRecord> feedbackRecords = new ArrayList<>();
        JSONArray jsonArray = json.getJSONObject("opinionInfo").getJSONArray("aaData");
        for(int index = 0;index<jsonArray.length();index++) {
            JSONObject jsonObj = jsonArray.getJSONObject(index);
            JSONArray imageArray = jsonObj.getJSONArray("imgs");
            List<String> images = new ArrayList<>();
            for(int j = 0;j< imageArray.length();j++)
                images.add(imageArray.getJSONObject(j).getString("imgName"));
            feedbackRecords.add(new FeedbackRecord(jsonObj.getString("createDate"),jsonObj.getString("opinionContent"),jsonObj.isNull("replyContent")?"":jsonObj.getString("replyContent"),images));
        }
        if(isAdd) {
            update.setRefreshing(false);
            data.clear();
        }
        data.addAll(feedbackRecords);
        adapter.notifyDataSetChanged();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        dbManagerShop= DBManagerShop.getInstance(getActivity());
        shop=dbManagerShop.queryById((long) 2333).get(0);
        map = new HashMap<>();
        map.put("userId",shop.getShopkeeperId());
        map.put("type","FunctionException");
        if(isDisplay)
            newCall(Config.Url.getUrl(Config.getPlatformException), map);
        data = new ArrayList<>();
        isAdd = false;
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        recycler.addItemDecoration(new RecyclerLimit(getActivity(),(byte) LinearLayout.HORIZONTAL));
        adapter = new FeedbackRecordAdapter(getActivity(),data);
        recycler.setAdapter(adapter);
        update.setOnRefreshListener(refreshListener);
        return rootView;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden)
            newCall(Config.Url.getUrl(Config.getPlatformException), map);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isDisplay = isVisibleToUser;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
