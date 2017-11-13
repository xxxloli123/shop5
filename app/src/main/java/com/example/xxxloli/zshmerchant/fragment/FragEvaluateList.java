package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.EvaluateListAdapter1;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Evaluate;
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

/**
 * Created by sgrape on 2017/5/25.
 * e-mail: sgrape1153@gmail.com
 */
@SuppressLint("ValidFragment")
public class FragEvaluateList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, EvaluateListAdapter1.Callback {

    @BindView(R.id.no_message)
    LinearLayout noMessage;
    @BindView(R.id.listview)
    RecyclerView listview;
    private String type;

    @BindView(com.sgrape.library.R.id.refresh)
    SwipeRefreshLayout srl;

    protected String status;
    protected boolean enable;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    private ArrayList<Evaluate> evaluates;
    private EvaluateListAdapter1 evaluateListAdapter1;

    public FragEvaluateList() {
    }

    public FragEvaluateList(String type) {
        this(type, true);
    }

    public FragEvaluateList(String type, boolean enable) {
        super();
        this.type = type;
        this.enable = enable;
        getArguments().putString("type", type);
        getArguments().putBoolean("enable", enable);
    }

    @Override
    protected void init() {
        dbManagerShop = DBManagerShop.getInstance(getActivity());
        shop = dbManagerShop.queryById((long) 2333).get(0);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (rootView != null && isVisibleToUser) onRefresh();
    }

    @Override
    protected void loadData() {
//        商家获取全部评价，获取未回复评价 type=[All==全部，NotReply==未回复]
//        参数：[shopId, type]
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", shop.getId());
        map.put("type", type);
        newCall(Config.Url.getUrl(Config.GET_Evaluate), map);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_evaluate_list;
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
            case Config.GET_Evaluate:
                srl.setRefreshing(false);
                firstLoad = false;
                evaluates = new ArrayList<>();
                JSONArray arr = json.getJSONArray("evaluate");
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject cache = arr.getJSONObject(i);
                    evaluates.add(evaluates.size(), gson.fromJson(cache.toString(), Evaluate.class));
                }
                if (evaluates.isEmpty()) {
                    noMessage.setVisibility(View.VISIBLE);
                    if (evaluateListAdapter1 != null) evaluateListAdapter1.refresh(evaluates);
                    return;
                } else noMessage.setVisibility(View.GONE);
                if (evaluateListAdapter1 != null) {
                    evaluateListAdapter1.refresh(evaluates);
                    return;
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                listview.setLayoutManager(linearLayoutManager);
                evaluateListAdapter1 = new EvaluateListAdapter1(getActivity(), evaluates, this);
                listview.setAdapter(evaluateListAdapter1);
                break;
            case Config.Reply_Evaluate:
                Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onRefresh() {
        if (srl != null && !srl.isRefreshing())
            srl.setRefreshing(true);
        loadData();
    }

//    class Adapter extends EvaluateListAdapter {
//
//        public Adapter() {
//            super(getContext());
//        }
//
//        @Override
//        protected void setData(View view, final Evaluate evaluate, final int position) {
//            super.setData(view, evaluate, position);
//            final ViewHolder holder = (ViewHolder) view.getTag();
//            holder.sure.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
////                    商家回复评价 evaluateId：评价id； replycomment：回复内容
////                    参数：[evaluateId, replycomment]
//                    String string=holder.reply.getText().toString();
//                    if (isEmpty1(string)){
//                        Toast.makeText(getActivity(), "请输入回复", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    Map<String, Object> params = new HashMap<>();
//                    params.put("evaluateId", evaluate.getId());
//                    params.put("replycomment", string);
//                    newCall(Config.Url.getUrl(Config.Reply_Evaluate), params);
//                    if (type.equals("NotReply")){
//                        //List移除某Item
//                        adapter.getData().remove(evaluate);
//                        adapter.notifyDataSetChanged();
//                    }else loadData();
//                }
//            });
//        }
//    }
    @Override
    protected void initListener() {
        srl.setOnRefreshListener(this);
    }

    private boolean isEmpty1(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        else return false;
    }
//
//    @Override
//    protected void readInstanceState() {
//        super.readInstanceState();
//        if (adapter == null) adapter = new Adapter();
//        rob = getArguments().getString("rob");
//        status = getArguments().getString("status");
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getArguments().putString("status", this.status);
    }

    @Override
    public void click(View v) {

    }
}
