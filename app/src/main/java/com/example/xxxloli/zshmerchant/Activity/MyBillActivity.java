package com.example.xxxloli.zshmerchant.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.CheckBillAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.CheckBill;
import com.example.xxxloli.zshmerchant.util.PayResult;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.slowlife.lib.MD5;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicDefaultFooter;
import in.srain.cube.views.ptr.PtrClassicDefaultHeader;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler2;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MyBillActivity extends BaseActivity {


    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.weixin_Bt)
    Button weixinBt;
    @BindView(R.id.alipay_Bt)
    Button alipayBt;
    @BindView(R.id.bill_list)
    ListView billList;
    @BindView(R.id.select_data_Tv)
    TextView selectDataTv;
    @BindView(R.id.no_order)
    LinearLayout noOrder;
    @BindView(R.id.ptr_frame_layout)
    PtrFrameLayout ptrFrameLayout;
    @BindView(R.id.select_data_LL)
    LinearLayout selectDataLL;

    private DBManagerShop dbManagerShop;
    private Shop shop;
    private static final int SDK_PAY_FLAG = 1;
    private int initYear, initMonth, initDay;
    private Calendar cal;
    private int page = 0;
    private ArrayList<CheckBill> checkBills;
    private CheckBillAdapter checkBillAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_my_bill);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        if (billList.getCount() > 0) {
            noOrder.setVisibility(View.GONE);
        } else {
            noOrder.setVisibility(View.VISIBLE);
        }
        PtrClassicDefaultHeader ptrClassicDefaultHeader = new PtrClassicDefaultHeader(this);

        ptrFrameLayout.setHeaderView(ptrClassicDefaultHeader);
        ptrFrameLayout.addPtrUIHandler(ptrClassicDefaultHeader);

        PtrClassicDefaultFooter ptrClassicDefaultFooter = new PtrClassicDefaultFooter(this);
        ptrFrameLayout.setFooterView(ptrClassicDefaultFooter);
        ptrFrameLayout.addPtrUIHandler(ptrClassicDefaultFooter);

        ptrFrameLayout.setPtrHandler(new PtrHandler2() {
            @Override
            public boolean checkCanDoLoadMore(PtrFrameLayout frame, View content, View footer) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, footer);
            }

            @Override
            public void onLoadMoreBegin(PtrFrameLayout frame) {
                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initView();
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
                        page = 0;
                        initView();
                    }
                }, 1500);
            }
        });

        getDate();
    }

    private void initView() {
        Map<String, Object> map1 = new HashMap<>();
//        [shopId, startPage, pageSize, time]
        map1.put("shopId", shop.getId());
        map1.put("startPage", ++page);
        map1.put("pageSize", "20");
        String month = (initMonth < 10) ? "0" + initMonth : initMonth + "";
        String day = (initDay < 10) ? "0" + initDay : initDay + "";
        map1.put("time", initYear + "-" + month + "-" + day);
        newCall(Config.Url.getUrl(Config.GET_Bills), map1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_bill;
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.Bound_Alipay:
                Log.e("Bound_Alipay", "丢了个雷姆" + json);
                alipay(json.getString("alipayBody"));
                break;
            case Config.GET_Bills:
                Log.e("GET_Bills", "丢了个雷姆" + json);
                JSONArray arr = json.getJSONObject("accountInfo").getJSONArray("aaData");
                ptrFrameLayout.refreshComplete();
                if (checkBills == null) checkBills = new ArrayList<>();
                if (page == 1 && !checkBills.isEmpty()) checkBills.clear();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    checkBills.add(gson.fromJson(arr.getString(i), CheckBill.class));
                }
                if (checkBills.isEmpty()) noOrder.setVisibility(View.VISIBLE);
                else noOrder.setVisibility(View.GONE);
                if (checkBillAdapter != null) {
                    checkBillAdapter.refresh(checkBills);
                    return;
                }
                checkBillAdapter = new CheckBillAdapter(this, checkBills);
                billList.setAdapter(checkBillAdapter);
                break;
        }
    }

    @OnClick({R.id.back_rl, R.id.weixin_Bt, R.id.alipay_Bt, R.id.select_data_Tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.weixin_Bt:
                wechat();
                break;
            case R.id.alipay_Bt:
                Map<String, Object> map = new HashMap<>();
                map.put("userId", shop.getShopkeeperId());
                newCall(Config.Url.getUrl(Config.Bound_Alipay), map);
                break;
        }
    }

    private void wechat() {
        final PayReq req = new PayReq();
        req.appId = "wx662f86b3d838aed7";
        req.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
        req.packageValue = "Sign=WXPay";
        final IWXAPI api = WXAPIFactory.createWXAPI(this, "wxa16fd44d55d01e5c");
        //  是否支持支付
        try {
            boolean reg = api.registerApp("wx662f86b3d838aed7");
            if (!reg) {
                Toast.makeText(this, "注册到微信失败", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (Exception e) {
        }
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userId", shop.getShopkeeperId())
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(Config.Url.getUrl(Config.Bound_Weixin))
                .build();
        new OkHttpClient().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.obtainMessage(0, "网络错误").sendToTarget();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.code() != 200) {
                    mHandler.obtainMessage(0, "服务器错误").sendToTarget();
                    return;
                }
                final String buf = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!TextUtils.isEmpty(buf)) {
                                JSONObject res = new JSONObject(buf);
                                Log.e("get server pay params:", buf);
                                if (res.getInt("statusCode") != 200) {
                                    Toast.makeText(MyBillActivity.this, res.getString("message"), Toast.LENGTH_SHORT).show();
                                    return;
                                }
//                                has有
                                if (null != res && res.has("wxresult")) {
                                    JSONObject json = res.getJSONObject("wxresult");

                                    final PayReq req = new PayReq();
//                                    PayReq request = new PayReq();
//                                    request.appId = "wxd930ea5d5a258f4f";
//                                    request.partnerId = "1900000109";
//                                    request.prepayId= "1101000000140415649af9fc314aa427",;
//                                    request.packageValue = "Sign=WXPay";
//                                    request.nonceStr= "1101000000140429eb40476f8896f4c9";
//                                    request.timeStamp= "1398746574";
//                                    request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//                                    api.sendReq(request);
                                    req.appId = "wx662f86b3d838aed7";
                                    req.partnerId = json.getJSONArray("mch_id").getString(0);
                                    req.prepayId = json.getJSONArray("prepay_id").getString(0);
                                    req.nonceStr = json.getJSONArray("nonce_str").getString(0);
                                    req.timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
                                    req.packageValue = "Sign=WXPay";
//                                    req.extData = "app data"; // optional

                                    StringBuffer signsb = new StringBuffer();
                                    signsb.append("appid=wx662f86b3d838aed7&noncestr=")
                                            .append(req.nonceStr)
                                            .append("&package=")
                                            .append(req.packageValue)
                                            .append("&partnerid=")
                                            .append(req.partnerId)
                                            .append("&prepayid=")
                                            .append(req.prepayId)
                                            .append("&timestamp=")
                                            .append(req.timeStamp)
                                            .append("&key=")
                                            .append("2c9ad8435dd0534e015de3bbba6d0054");
                                    req.sign = MD5.md5(signsb.toString()).toUpperCase();

                                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                    //api.registerApp("wx662f86b3d838aed7");
                                    boolean result = api.sendReq(req);
                                    if (result)
                                        Toast.makeText(MyBillActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(MyBillActivity.this, "调用支付失败", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MyBillActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.d("PAY_GET", "服务器请求错误");
                                Toast.makeText(MyBillActivity.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e("PAY_GET", "异常：" + e.getMessage());
                            mHandler.obtainMessage(0, "异常：" + e.getMessage()).sendToTarget();
                        }
                    }
                });
            }
        });

    }

    //获取当前日期
    private void getDate() {
        cal = Calendar.getInstance();
        initYear = cal.get(Calendar.YEAR);       //获取年月日时分秒
        initMonth = cal.get(Calendar.MONTH) + 1;   //获取到的月份是从0开始计数
        initDay = cal.get(Calendar.DAY_OF_MONTH);
        selectDataTv.setText(initYear + "." + (initMonth) + "." + initDay);
        initView();
    }

    private void selectData() {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, final int year, int month, int day) {
                //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                initYear = year;
                initMonth = month;
                initDay = day;
                page = 0;

                String iM=(initMonth<10)? "0"+initMonth:""+initMonth;
                String iD=(initDay<10)? "0"+initDay:""+initDay;
                String dateString = (year + "-" + iM + "-" + iD);
                if (!selectDataTv.getText().toString().equals(dateString)) {
                    page = 0;
                    selectDataTv.setText(dateString);
                }
                initView();
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog = new DatePickerDialog(this, 0, listener, initYear, initMonth, initDay);
        DatePicker datePicker = dialog.getDatePicker();
        long time = cal.getTimeInMillis();
        datePicker.setMaxDate(time);
        dialog.show();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(MyBillActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(MyBillActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    private void alipay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(MyBillActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @OnClick(R.id.select_data_LL)
    public void onViewClicked() {
        selectData();
    }
}
