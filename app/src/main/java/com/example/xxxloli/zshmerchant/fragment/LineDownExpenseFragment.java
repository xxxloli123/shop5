package com.example.xxxloli.zshmerchant.fragment;

import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.Classify2Adapter;
import com.example.xxxloli.zshmerchant.adapter.LineDownExpenseAdapter;
import com.example.xxxloli.zshmerchant.adapter.LineDownExpenseAdapter1;
import com.example.xxxloli.zshmerchant.base.AppInfo;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.example.xxxloli.zshmerchant.print.PrintQueue;
import com.example.xxxloli.zshmerchant.printutil.PrintOrderDataMaker;
import com.example.xxxloli.zshmerchant.printutil.PrinterWriter;
import com.example.xxxloli.zshmerchant.printutil.PrinterWriter58mm;
import com.example.xxxloli.zshmerchant.util.ToastUtil;
import com.example.xxxloli.zshmerchant.view.MyListView;
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
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrDefaultHandler2;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;

/**
 * Created by Administrator on 2017/9/22.
 */

public class LineDownExpenseFragment extends BaseFragment implements LineDownExpenseAdapter1.Callback{

    Unbinder unbinder;
    @BindView(R.id.select_date_text)
    TextView selectDateText;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.no_order)
    LinearLayout noOrder;
    @BindView(R.id.listview)
    MyListView listview;
    @BindView(R.id.ptr_frame_layout)
    PtrClassicFrameLayout ptrFrameLayout;

    private int page = 0;
    private int FQyear, FQmonth, FQday;
    private Calendar cal;
    private String dateString;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    private ArrayList<OrderEntity> orders;
    private LineDownExpenseAdapter1 lineDownExpenseAdapter1;
    private String initMonth,initDay;

    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_line_up_expense;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_LineOrder:
                ptrFrameLayout.refreshComplete();

                JSONArray arr = json.getJSONObject("ordersInfo").getJSONArray("aaData");
                if (orders == null) orders = new ArrayList<>();
                if (!orders.isEmpty()) orders.clear();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    final JSONObject cache = arr.getJSONObject(i);
                    orders.add(orders.size(), gson.fromJson(cache.toString(), OrderEntity.class));
                }
                if (orders.isEmpty()) noOrder.setVisibility(View.VISIBLE);
                else noOrder.setVisibility(View.GONE);
                firstLoad = false;
                if (lineDownExpenseAdapter1!=null){
                    lineDownExpenseAdapter1.refresh(orders);
                    return;
                }
                lineDownExpenseAdapter1=new LineDownExpenseAdapter1(getActivity(),orders,this);
                listview.setAdapter(lineDownExpenseAdapter1);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        if (firstLoad){
            getData();
        }
        return rootView;
    }

    private void initView() {
        final PtrClassicDefaultHeader header = new PtrClassicDefaultHeader(getActivity());
        header.setLastUpdateTimeKey("丢了个雷姆");
        ptrFrameLayout.setHeaderView(header);
        ptrFrameLayout.addPtrUIHandler(header);
        final PtrClassicDefaultFooter footer = new PtrClassicDefaultFooter(getActivity());
        footer.setLastUpdateTimeKey("丢了个雷姆");
        ptrFrameLayout.setFooterView(footer);
        ptrFrameLayout.addPtrUIHandler(footer);
    }

    @Override
    protected void init() {
        dbManagerShop = DBManagerShop.getInstance(getContext());
        shop = dbManagerShop.queryById((long) 2333).get(0);
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (listview.getCount() > 0) {
            noOrder.setVisibility(View.GONE);
        } else {
            noOrder.setVisibility(View.VISIBLE);
        }
        initView();
    }

    //获取当前日期
    private void getData() {
        cal = Calendar.getInstance();
        FQyear = cal.get(Calendar.YEAR);       //获取年月日时分秒
        FQmonth = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        FQday = cal.get(Calendar.DAY_OF_MONTH);
        date.setText("今日" + FQyear + "-" + (++FQmonth) + "-" + FQday);
        initMonth=(++FQmonth<10)? "0"+FQmonth:""+FQmonth;
        initDay=(FQday<10)? "0"+FQday:""+FQday;
        baseDateInquire(FQyear + "-" + initMonth + "-" + initDay);
    }

    private void baseDateInquire(String dateString) {
//        订单查询lineOrder： 线上订单 yes 线下订单 no：
//        参数：[shopId, time, userId, lineOrder, startPage, pageSize]
        Map<String, Object> map = new HashMap<>();
        map.put("shopId", shop.getId());
        map.put("time", dateString);
        map.put("userId", shop.getShopkeeperId());
        map.put("lineOrder", "no");
        map.put("startPage", ++page);
        map.put("pageSize", "20");
        newCall(Config.Url.getUrl(Config.GET_LineOrder), map);
    }

    @Override
    protected void initListener() {
        ptrFrameLayout.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return PtrDefaultHandler2.checkContentCanBePulledUp(frame, content, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        baseDateInquire(FQyear + "-" + FQmonth + "-" + FQday);
                    }
                }, 1500);
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        page=0;
                        baseDateInquire(FQyear + "-" + FQmonth + "-" + FQday);
                    }
                }, 1500);
            }
        });
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
                FQyear = year;
                FQmonth = ++month;
                FQday = day;
                initMonth=(FQmonth<10)? "0"+FQmonth:""+FQmonth;
                initDay=(FQday<10)? "0"+FQday:""+FQday;
                dateString = (year + "-" + initMonth + "-" + initDay);
                if (!date.getText().toString().equals(dateString)) {
                    page = 0;
                    firstLoad=false;
                    date.setText(dateString);
                    baseDateInquire(dateString);
                }
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), 0, listener, FQyear, FQmonth, FQday);
        DatePicker datePicker = dialog.getDatePicker();
        long time = cal.getTimeInMillis();
        datePicker.setMaxDate(time);
        dialog.show();
    }

    @Override
    public void click(View v) {
        switch (v.getId()) {
            case R.id.print_order:
                if (TextUtils.isEmpty(AppInfo.btAddress)) {
                    ToastUtil.showToast(getActivity(), "请设置打印机功能");
                    return;
                }
                if (mAdapter.getState() == BluetoothAdapter.STATE_OFF) {//蓝牙被关闭时强制打开
                    mAdapter.enable();
                    ToastUtil.showToast(getActivity(), "蓝牙被关闭请打开...");
                } else {
                    PrintOrderDataMaker printOrderDataMaker = new PrintOrderDataMaker(getActivity(), "",
                            PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT, orders.get((Integer) v.getTag()));
                    ArrayList<byte[]> printData = (ArrayList<byte[]>) printOrderDataMaker
                            .getPrintData(PrinterWriter58mm.TYPE_58);
                    PrintQueue.getQueue(getActivity()).add(printData);
                }
                break;
        }
    }

//    private void isCancelOrder() {
//        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_cancel_oder, null);
//        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
//        Button sure = view.findViewById(R.id.sure_bt);
//        Button cancel = view.findViewById(R.id.cancel_bt);
//        sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });
//        alertDialog.setView(view);
//        alertDialog.show();
//    }
}
