package com.example.xxxloli.zshmerchant.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.OrderListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by sgrape on 2017/5/25.
 * e-mail: sgrape1153@gmail.com
 */
@SuppressLint("ValidFragment")
public class FragOrderList extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private String type;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.no_order)
    LinearLayout noOrder;
    @BindView(com.sgrape.library.R.id.refresh)
    SwipeRefreshLayout srl;

    private int page = 0;
    protected OrderListAdapter adapter;
    protected String lineOrderType;
    protected boolean enable;

    public FragOrderList() {
    }

    public FragOrderList(String lineOrderType) {
        this(lineOrderType, true);
    }

    public FragOrderList( String lineOrderType, boolean enable) {
        super();
        this.lineOrderType = lineOrderType;
        this.enable = enable;
        getArguments().putString("lineOrderType", lineOrderType);
        getArguments().putBoolean("enable", enable);
    }

    @Override
    protected void init() {
        if (listview.getAdapter() == null)
            listview.setAdapter(adapter);
        if (listview.getCount() > 0) {
            noOrder.setVisibility(View.GONE);
        } else {
            noOrder.setVisibility(View.VISIBLE);
        }
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
        //订单处理 lineOrderType： 所有新订单All，ReserveOrder(预定单),NormalOrder(配送到家),ShopConsumption(到店消费单)
//        if (!firstLoad && srl != null && !srl.isRefreshing()) return;
        firstLoad = false;
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", "402880e65ed0bda0015ed0c876e00007");
        map.put("userId", "402880e65ed0bda0015ed0c876500005");
        map.put("lineOrderType",lineOrderType);
        newCall(Config.Url.getUrl(Config.GET_HandleOrder), map);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_list;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void fail(Object tag, int code, JSONObject json) throws JSONException {
        super.fail(tag, code, json);
        if (srl != null) srl.setRefreshing(false);
    }

    class Adapter extends OrderListAdapter {

        public Adapter() {
            super(getContext());
        }

        @Override
        protected void setData(View view, final OrderEntity order, final int position) {
            super.setData(view, order, position);
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.reject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    reject(order);
                }
            });
            holder.receivingOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    receive(order);
                }
            });
        }
    }
//                接单
    private void receive(final OrderEntity orderEntity) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_receive_or_reject, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final CheckBox checkBox = view.findViewById(R.id.checkbox);
        EditText causeEdit = view.findViewById(R.id.cause_edit);
        causeEdit.setVisibility(View.GONE);
        checkBox.setVisibility(View.VISIBLE);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//        商家接单 或拒单 receivedorder yes 接单 no 拒单 ；cause拒单原因;huidi配送yes no
                Map<String, Object> map = new HashMap<>();
                map.put("receivedorder", "yes");
                if (checkBox.isChecked()) map.put("huidi", "yes");
                else map.put("huidi", "nu");
                map.put("orderId", orderEntity.getId());
                map.put("userId", "402880e65ed0bda0015ed0c876500005");
                map.put("cause","");
                newCall(Config.Url.getUrl(Config.Receive_Reject), map);
                //List移除某Item
                adapter.getData().remove(orderEntity);
                adapter.notifyDataSetChanged();
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
//                    接毛线单啊
    private void reject(final OrderEntity orderEntity) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_receive_or_reject, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final EditText causeEdit = view.findViewById(R.id.cause_edit);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(causeEdit.getText().toString())){
                    Toast.makeText(getActivity(),"请输入拒绝原因",Toast.LENGTH_SHORT).show();
                    return;
                }
//        商家接单 或拒单 receivedorder yes 接单 no 拒单 ；cause拒单原因;huidi配送yes no
                Map<String, Object> map = new HashMap<>();
                map.put("receivedorder", "no");
                map.put("huidi", "yes");
                map.put("orderId", orderEntity.getId());
                map.put("userId", "402880e65ed0bda0015ed0c876500005");
                map.put("cause",causeEdit.getText().toString());
                newCall(Config.Url.getUrl(Config.Receive_Reject), map);
                //List移除某Item
                adapter.getData().remove(orderEntity);
                adapter.notifyDataSetChanged();
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
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
        switch (tag.toString()) {
            case Config.GET_HandleOrder:
                List<OrderEntity> orders = adapter.getData();
                if (orders == null) orders = new ArrayList<>();
                if (page == 1 && !orders.isEmpty()) orders.clear();
                JSONArray arr = json.getJSONObject("ordersInfo").getJSONArray("aaData");
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject cache = arr.getJSONObject(i);
                    orders.add(orders.size(), gson.fromJson(cache.toString(), OrderEntity.class));
                }
                adapter.notifyDataSetChanged(orders);
                if (orders.isEmpty()) noOrder.setVisibility(View.VISIBLE);
                else noOrder.setVisibility(View.GONE);
                srl.setRefreshing(false);
                firstLoad = false;
                break;
            case Config.Receive_Reject:

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
        if (adapter == null) adapter = new Adapter();
        super.readInstanceState();
        lineOrderType = getArguments().getString("lineOrderType");
        ArrayList<OrderEntity> orders = getArguments().getParcelableArrayList("orderlist");
        if (adapter == null) {
            adapter = new OrderListAdapter(getContext());
            adapter.setEnable(enable);
        }
        if (orders != null || adapter.getCount() == 0) adapter.notifyDataSetChanged(orders);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Bundle bundle = getArguments();
        getArguments().putString("rob", this.lineOrderType);
        bundle.putParcelableArrayList("orderlist", (ArrayList<OrderEntity>) adapter.getData());
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
