package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.EditCommodityActivity;
import com.example.xxxloli.zshmerchant.Activity.MessageContentActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.MessageListAdapter;
import com.example.xxxloli.zshmerchant.adapter.OrderListAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Message;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

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
import butterknife.Unbinder;

/**
 * Created by sgrape on 2017/5/25.
 * e-mail: sgrape1153@gmail.com
 */
@SuppressLint("ValidFragment")
public class FragMessageList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.no_message)
    LinearLayout noMessage;
    Unbinder unbinder;
    private String type;
    @BindView(R.id.listview)
    ListView listview;

    @BindView(com.sgrape.library.R.id.refresh)
    SwipeRefreshLayout srl;

    private int page = 0;
    protected MessageListAdapter adapter;
    protected boolean enable;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    public static final String READ_Message = "messages";

    public FragMessageList() {
    }

    public FragMessageList( String type) {
        this( type, true);
    }

    public FragMessageList( String type, boolean enable) {
        super();
        this.type = type;
        this.enable = enable;
        getArguments().putString("type", type);
        getArguments().putBoolean("enable", enable);
    }

    @Override
    protected void init() {
        dbManagerShop = DBManagerShop.getInstance(getContext());
        shop = dbManagerShop.queryById((long) 2333).get(0);
        if (listview.getAdapter() == null)
            listview.setAdapter(adapter);
        if (listview.getCount() > 0) {
            noMessage.setVisibility(View.GONE);
        } else {
            noMessage.setVisibility(View.VISIBLE);
        }
        loadData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView != null && isVisibleToUser) onRefresh();
    }


    @Override
    protected void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MessageContentActivity.class);
                intent.putExtra(READ_Message, (Serializable) adapter.getData().get(position));
                startActivity(intent);
            }
        });
        listview.setOnScrollListener(new OnScrollListener());
        srl.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
//        查询商家消息 type==全部all 公告 消息 PlatformNotice(平台公告),ShopNews(商家消息);
//        参数：[shopId, type]
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", shop.getId());
        map.put("userId", shop.getShopkeeperId());
        map.put("type",type);
        newCall(Config.Url.getUrl(Config.GET_Message), map);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_message_list;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void fail(Object tag, int code, JSONObject json) throws JSONException {
        super.fail(tag, code, json);
        if (srl != null) srl.setRefreshing(false);
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_Message:
                List<Message> messages = adapter.getData();
                if (messages == null) messages = new ArrayList<>();
                if (!messages.isEmpty()) messages.clear();
                JSONArray arr = json.getJSONObject("shopnews").getJSONArray("aaData");
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject cache = arr.getJSONObject(i);
                    messages.add(messages.size(), gson.fromJson(cache.toString(), Message.class));
                }
                adapter.notifyDataSetChanged(messages);
                if (messages.isEmpty()) noMessage.setVisibility(View.VISIBLE);
                else noMessage.setVisibility(View.GONE);
                srl.setRefreshing(false);
                firstLoad = false;
                break;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        if (srl != null && !srl.isRefreshing())
            srl.setRefreshing(true);
        loadData();
    }

    class Adapter extends MessageListAdapter {
        public Adapter() {
            super(getContext());
        }
    }

    @Override
    protected void readInstanceState() {
        super.readInstanceState();
        if (adapter == null) adapter = new Adapter();
        type = getArguments().getString("type");
        if (adapter == null) {
            adapter = new MessageListAdapter(getContext());
            adapter.setEnable(enable);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = getArguments();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        init();
        return rootView;
    }

    @OnClick(R.id.no_message)
    public void onViewClicked() {
    }

    class OnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
            if (firstVisibleItem + visibleItemCount > listview.getAdapter().getCount() - 5
                    && listview.getAdapter().getCount() % 20 == 0
                    && listview.getAdapter().getCount() != 0) {
                loadData();
            }
        }
    }
}
