package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.SearchBluetoothActivity;
import com.example.xxxloli.zshmerchant.Activity.ShopInfoActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.OrdersHandleFragmentAdapter;
import com.example.xxxloli.zshmerchant.base.AppInfo;
import com.example.xxxloli.zshmerchant.bluetooth.BtService;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.OrderEntity;
import com.example.xxxloli.zshmerchant.print.PrintQueue;
import com.example.xxxloli.zshmerchant.print.PrintUtil;
import com.example.xxxloli.zshmerchant.printutil.PrintOrderDataMaker;
import com.example.xxxloli.zshmerchant.printutil.PrinterWriter;
import com.example.xxxloli.zshmerchant.printutil.PrinterWriter58mm;
import com.example.xxxloli.zshmerchant.util.ToastUtil;
import com.example.xxxloli.zshmerchant.view.PagerSlidingTabStrip;
import com.example.xxxloli.zshmerchant.view.ShSwitchView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/13.
 */

public class OrderHandleFragment extends BaseFragment {
    View v;
    @BindView(R.id.is_automatic_order)
    ShSwitchView isAutomaticOrder;
    @BindView(R.id.printer_img)
    ImageView printerImg;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;
    Unbinder unbinder;

    private static Timer timer;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    /**
     * bluetooth adapter
     */
    BluetoothAdapter mAdapter;

    /**
     * 根据选择的不同界面选择不同tabs
     */
    private void initPagers() {
        ArrayList<String> list = new ArrayList<>();
        list.add("新订单");
        list.add("配送到家");
        list.add("预定单");
        list.add("到店消费");

        pager.setAdapter(new OrdersHandleFragmentAdapter(getActivity().getSupportFragmentManager(), list));
        tabs.setViewPager(pager);
        pager.setCurrentItem(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initPagers();
        dbManagerShop = DBManagerShop.getInstance(getActivity());
        shop = dbManagerShop.queryById((long) 2333).get(0);
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!TextUtils.isEmpty(AppInfo.btAddress)){
            printerImg.setImageResource(R.drawable.printer4);
        }
        if (shop.getAutoOrder()!=null&&shop.getAutoOrder().equals("yes")){
            isAutomaticOrder.setOn(true);
            autoOrderReceiving();
        }
        else isAutomaticOrder.setOn(false);
        isAutomaticOrder.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                if (isOn) {
                    editAuto("yes");
                } else {
                    editAuto("no");

                }
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        if (!TextUtils.isEmpty(AppInfo.btAddress)){
            printerImg=rootView.findViewById(R.id.printer_img);
            printerImg.setImageResource(R.drawable.printer4);
        }
        super.onResume();
    }

    private void editAuto(String submit) {
        Map<String, Object> params = new HashMap<>();
        JSONObject shopStr = new JSONObject();
        try {
//            autoOrder 自动接单 开启yes 关闭no;
            shopStr.put("id", shop.getId());
            shopStr.put("autoOrder", submit);

            params.put("shopStr", shopStr);
            params.put("userId", shop.getShopkeeperId());
            newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_order_handle;
    }

    @OnClick(R.id.printer_img)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),SearchBluetoothActivity.class));
    }

    private void autoOrderReceiving() {
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
//                商家自动打印：获取待打印订单
//                参数：[shopId]
                Map<String, Object> params = new HashMap<>();
                params.put("shopId", shop.getId());
                newCall(Config.Url.getUrl(Config.GET_AutoOrder), params);
//                Message message = new Message( );
//                message.what = 1;
//                handler.sendMessage(message);
            }
        },0,1000*3);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_AutoOrder:
            if (json.getInt("statusCode") == 200) {
                JSONArray arr = json.getJSONArray("listorder");
                if (arr.length() == 0) return;
                if (TextUtils.isEmpty(AppInfo.btAddress)){
                    ToastUtil.showToast(getActivity(),"已自动接单。。。\n如需打印订单请开启打印机功能");
                    return;
                }
                for (int i=0;i<arr.length();i++){
                        if ( mAdapter.getState()== BluetoothAdapter.STATE_OFF ){//蓝牙被关闭时强制打开
                            mAdapter.enable();
                            ToastUtil.showToast(getActivity(),"蓝牙被关闭请打开...");
                        }else {
                            OrderEntity orderEntity=new Gson().fromJson(arr.getString(arr.length() - i - 1), OrderEntity.class);
                            PrintOrderDataMaker printOrderDataMaker = new PrintOrderDataMaker(getActivity(),"",
                                    PrinterWriter58mm.TYPE_58, PrinterWriter.HEIGHT_PARTING_DEFAULT,orderEntity);
                            ArrayList<byte[]> printData = (ArrayList<byte[]>) printOrderDataMaker
                                    .getPrintData(PrinterWriter58mm.TYPE_58);
                            PrintQueue.getQueue(getActivity()).add(printData);
                        }
                }
            }
        break;
            case Config.EDIT_SHOP_INFO:
                Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                if (json.getString("statusCode").equals("200")){
                    if (shop.getAutoOrder()==null||shop.getAutoOrder().equals("no")){
                        shop.setAutoOrder("yes");
                        dbManagerShop.updateShop(shop);
                        autoOrderReceiving();
                    }else {
                        shop.setAutoOrder("no");
                        dbManagerShop.updateShop(shop);
                        stopTimer();
                    }
                }
                Log.e("EDIT_SHOP_INFO", "丢了个雷姆" + json);
//                    startActivity(new Intent(ShopInfoActivity.this, LoginActivity.class));
                break;
        }
    }

    // 停止定时器
    public static void stopTimer(){
        if(timer != null){
            timer.cancel();
            // 一定设置为null，否则定时器不会被回收
            timer = null;
        }
    }

//    final Handler handler = new Handler( ) {
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case 1:
//                    ToastUtil.showToast(getActivity(),"丢了个雷姆");
//                    break;
//            }
//            super.handleMessage(msg);
//        }
//    };

}
