package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.LineUpExpenseAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LineUpExpenseFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    Unbinder unbinder;
    @BindView(R.id.order_list)
    ListView orderList;
    @BindView(com.sgrape.library.R.id.refresh)
    SwipeRefreshLayout srl;
    @BindView(R.id.no_order)
    LinearLayout noOrder;
    @BindView(R.id.select_date_text)
    TextView selectDateText;
    @BindView(R.id.date)
    TextView date;

    private LineUpExpenseAdapter adapter;
    private int page = 0;
    private int year, month, day;
    private Calendar cal;
    private String dateString;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_line_up_expense;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void readInstanceState() {
        if (adapter == null) adapter = new Adapter();
        super.readInstanceState();
    }

    @Override
    protected void initListener() {
        orderList.setOnScrollListener(new OnScrollListener());
        srl.setOnRefreshListener(this);
    }

    @Override
    public void onStart() {
        page = 0;
        if (srl != null && !srl.isRefreshing())
            srl.setRefreshing(true);
        loadData();
        super.onStart();
    }

    @Override
    public void onRefresh() {

    }

    class Adapter extends LineUpExpenseAdapter {

        public Adapter() {
            super(getContext());
        }

        @Override
        protected void setData(View view, final OrderEntity order, final int position) {
            super.setData(view, order, position);
            final ViewHolder holder = (ViewHolder) view.getTag();
            holder.serialNumber.setText((adapter.getCount()-position)+"");
        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
        switch (tag.toString()) {
            case Config.GET_LineOrder:
                JSONArray arr = json.getJSONObject("ordersInfo").getJSONArray("aaData");
                List<OrderEntity> orders = adapter.getData();
                if (orders == null) orders = new ArrayList<>();
                if (page == 1 && !orders.isEmpty()) orders.clear();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    protected void init() {
        if (orderList.getAdapter() == null)
            orderList.setAdapter(adapter);
        if (orderList.getCount() > 0) {
            noOrder.setVisibility(View.GONE);
        } else {
            noOrder.setVisibility(View.VISIBLE);
        }
        getDate();
        loadData();
    }

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day = cal.get(Calendar.DAY_OF_MONTH);
        date.setText("今日"+year + "-" + (++month) + "-" + day);
    }

    @Override
    protected void loadData() {
//        订单查询lineOrder： 线上订单 yes 线下订单 no：
//        参数：[shopId, time, userId, lineOrder]
        firstLoad = false;
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", "402880e65ed0bda0015ed0c876e00007");
        map.put("time", year + "-" + (++month) + "-" + day);
        map.put("userId", "402880e65ed0bda0015ed0c876500005");
        map.put("lineOrder","yes");
        newCall(Config.Url.getUrl(Config.GET_LineOrder), map);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.select_date_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_date_text:
                selectDate();
                break;
        }
    }

    private void selectDate() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                dateString=(year + "-" + (++month) + "-" + day);
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), 0, listener, year, month, day);
        DatePicker datePicker = dialog.getDatePicker();
        long time = cal.getTimeInMillis();
        datePicker.setMaxDate(time);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (!date.getText().toString().equals(dateString)){
                    baseDateInquire(dateString);
                    date.setText(dateString);
                }
            }
        });
        dialog.show();
    }

    private void baseDateInquire(String dateString) {
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", "402880e65ed0bda0015ed0c876e00007");
        map.put("time", dateString);
        map.put("userId", "402880e65ed0bda0015ed0c876500005");
        map.put("lineOrder","yes");
        newCall(Config.Url.getUrl(Config.GET_LineOrder), map);
    }

    class OnScrollListener implements AbsListView.OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {

        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem,
                             int visibleItemCount, int totalItemCount) {
//            if (firstVisibleItem + visibleItemCount > orderList.getAdapter().getCount() - 5
//                    && orderList.getAdapter().getCount() % 20 == 0
//                    && orderList.getAdapter().getCount() != 0) {
//                loadData();
//            }
        }
    }
}
