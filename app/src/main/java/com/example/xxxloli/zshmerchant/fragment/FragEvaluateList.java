package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.EvaluateListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Message;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by sgrape on 2017/5/25.
 * e-mail: sgrape1153@gmail.com
 */
@SuppressLint("ValidFragment")
public class FragEvaluateList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.no_message)
    LinearLayout noMessage;
    Unbinder unbinder;
    private String type;
    @BindView(R.id.listview)
    ListView listview;

    @BindView(com.sgrape.library.R.id.refresh)
    SwipeRefreshLayout srl;

    private int page = 0;
    protected String rob;
    protected EvaluateListAdapter adapter;
    protected String status;
    protected boolean enable;

    public FragEvaluateList() {
    }

    public FragEvaluateList(String rob, String status) {
        this(rob, status, null);
    }

    public FragEvaluateList(String rob, String status, String type) {
        this(rob, status, type, true);
    }

    public FragEvaluateList(String rob, String status, String type, boolean enable) {
        super();
        this.rob = rob;
        this.status = status;
        this.type = type;
        this.enable = enable;
        getArguments().putString("rob", this.rob);
        getArguments().putString("status", this.status);
        getArguments().putString("type", type);
        getArguments().putBoolean("enable", enable);
    }

    @Override
    protected void init() {
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
        listview.setOnScrollListener(new OnScrollListener());
        srl.setOnRefreshListener(this);
    }

    @Override
    protected void loadData() {
        List<Message> messages = adapter.getData();
        Toast.makeText(getActivity(),"丢了个雷姆"+messages.size(),Toast.LENGTH_SHORT);
        if (messages == null) messages = new ArrayList<>();
        ++page;
        if (page == 1 && !messages.isEmpty()) messages.clear();
        for (int i = 0; i < 5; i++) {
            Message message = new Message();
            message.setId(i);
            message.setContent("香蕉君或许会迟到但永不缺席" + i);
            messages.add(message);
        }
        adapter.notifyDataSetChanged(messages);
        if (messages.isEmpty()) noMessage.setVisibility(View.VISIBLE);
        else noMessage.setVisibility(View.GONE);
        srl.setRefreshing(false);
        firstLoad = false;
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
            case Config.SHOP_TYPE:
                List<Message> messages = adapter.getData();
                adapter.notifyDataSetChanged(messages);
                if (messages.isEmpty()) noMessage.setVisibility(View.VISIBLE);
                else noMessage.setVisibility(View.GONE);
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

    @Override
    protected void readInstanceState() {
        super.readInstanceState();
        rob = getArguments().getString("rob");
        status = getArguments().getString("status");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = getArguments();
        getArguments().putString("rob", this.rob);
        getArguments().putString("status", this.status);
        unbinder.unbind();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
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
