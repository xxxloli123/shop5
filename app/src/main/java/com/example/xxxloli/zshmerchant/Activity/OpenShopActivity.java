package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.SelectAreaAdapter;
import com.example.xxxloli.zshmerchant.adapter.ShopSecondTypeAdapter;
import com.example.xxxloli.zshmerchant.adapter.ShopTypeAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.ShopType;
import com.example.xxxloli.zshmerchant.objectmodel.Street;
import com.example.xxxloli.zshmerchant.util.Common;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.example.xxxloli.zshmerchant.view.TimeButton;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.slowlife.lib.MD5;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OpenShopActivity extends BaseActivity implements
        AMapLocationListener {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.name_edit)
    EditText nameEdit;
    @BindView(R.id.phone_edit)
    EditText phoneEdit;

    @BindView(R.id.verification_code_edit)
    EditText verificationCodeEdit;
    @BindView(R.id.register_pwd1)
    EditText registerPwd1;
    @BindView(R.id.register_pwd2)
    EditText registerPwd2;
    @BindView(R.id.shop_type)
    TextView shopType;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.verification_code)
    TimeButton verificationCode;
    @BindView(R.id.street)
    TextView street;
    @BindView(R.id.shop_typeRL)
    RelativeLayout shopTypeRL;


    private AMapLocationClient mlocationClient;
    // /声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private ArrayList<ShopType> fristType;
    private ArrayList<ShopType> secondType;
    private ShopSecondTypeAdapter shopSecondTypeAdapter;
    private ArrayList<Street> streets;
    private String smsId = "";
    private String classId,className,lng,lat,pro,city,district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        loadData();
        initView();
        getAreaDate();
    }

    private void initView() {
        phoneEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Common.matchePhone(phoneEdit.getText().toString().trim())) {
                    verificationCode.setEnabled(true);
                } else verificationCode.setEnabled(false);
            }
        });
    }

    private void loadData() {
        //商家类型
        RequestBody requestBody2 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("fatherId", "").build();
        Request request = new Request.Builder().url(Config.Url.getUrl(Config.SHOP_TYPE)).post(requestBody2).build();
        new OkHttpClient().newCall(request).enqueue(new SimpleCallback(this) {
            @Override
            public void onSuccess(String tag, JSONObject jsonObject) throws JSONException {
                JSONArray arr = jsonObject.getJSONArray("genericClassList");
                fristType = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    fristType.add(gson.fromJson(arr.getString(i), ShopType.class));
                }
                Map<String, String> params1 = new HashMap<>();
                params1.put("fatherId", fristType.get(0).getId());
                newCall(Config.Url.getUrl(Config.SHOP_TYPE), params1);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_open_shop;
    }

    private void getAreaDate() {
        getAmapLocation();
    }

    private void getAmapLocation() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2 * 60 * 1000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    @OnClick({R.id.back_rl, R.id.verification_code, R.id.button, R.id.street, R.id.shop_type})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.verification_code:
                final String phone = phoneEdit.getText().toString().trim();
                if (!Common.matchePhone(phone)) {
                    Toast.makeText(getContext(), "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                verificationCode.setTextAfters("").setTextAfter("秒后重发").setTextBefore("获取验证码").setLenght(60 * 1000);
                verificationCode.start();
                newCall(Config.Url.getUrl(Config.SMS_CODE), Common.getCode(phone, "0"));
                break;
            case R.id.button:
                register();
                break;
            case R.id.street:
                selectAreaDialog();
                break;
            case R.id.shop_type:
                selectType();
                loadData();
                break;
        }
    }

    private void selectType() {
        final PopupWindow popupWindow = new PopupWindow(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_shop_type, null);
        ListView firstLevel = view.findViewById(R.id.first_level);
        final ListView secondLevel = view.findViewById(R.id.second_level);

        if (fristType == null) fristType = new ArrayList<>();
        final ShopTypeAdapter shopTypeAdapter = new ShopTypeAdapter(this, fristType);
        firstLevel.setAdapter(shopTypeAdapter);

        if (secondType == null) return;
        shopSecondTypeAdapter = new ShopSecondTypeAdapter(this, secondType);
        secondLevel.setAdapter(shopSecondTypeAdapter);
        firstLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                shopTypeAdapter.changeSelected(i);
                Map<String, String> params1 = new HashMap<>();
                Log.e("区域", "丢了一个雷姆 getFatherId " + fristType.get(0).getId());
                params1.put("fatherId", fristType.get(i).getId());
                newCall(Config.Url.getUrl(Config.SHOP_TYPE), params1);
            }
        });
        secondLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                shopType.setText(secondType.get(i).getGenericClassName().toString());
                shopSecondTypeAdapter.changeSelected(i);
                classId=secondType.get(i).getId();
                className=secondType.get(i).getGenericClassName();
                popupWindow.dismiss();
            }
        });
        // 设置可以获得焦点
        popupWindow.setFocusable(true);
        // 设置弹窗内可点击
        popupWindow.setTouchable(true);
        // 设置弹窗外可点击
        popupWindow.setOutsideTouchable(true);
        popupWindow.setContentView(view);
        Log.e("高", "高" + (getScreenSize(this)[1] - shopTypeRL.getBottom()));
        popupWindow.setHeight(getScreenSize(this)[1] - shopTypeRL.getBottom());
        popupWindow.setWidth(getScreenSize(this)[0]);
        popupWindow.showAsDropDown(shopType);

    }

    private void selectAreaDialog() {
        View view = LayoutInflater.from(OpenShopActivity.this).inflate(R.layout.dialog_select_area, null);
        final AlertDialog areaDialog = new AlertDialog.Builder(OpenShopActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        View delete = view.findViewById(R.id.back_rl);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                areaDialog.dismiss();
            }
        });
        ListView listView = view.findViewById(R.id.listview);
        if (streets==null)return;
        SelectAreaAdapter selectAreaAdapter =new SelectAreaAdapter(this,streets);
        listView.setAdapter(selectAreaAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                street.setText(streets.get(i).getStartStreet());
                areaDialog.dismiss();
            }
        });
        areaDialog.setView(view);
        areaDialog.show();
    }

    private void register() {
        String code = verificationCodeEdit.getText().toString().trim();
        String phone = phoneEdit.getText().toString().trim();
        String pwd = registerPwd1.getText().toString().trim();
        String name = nameEdit.getText().toString().trim();
        if (!Common.matchePhone(phone)) {
            Toast.makeText(this, "手机格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(code)) {
            Toast.makeText(this, "请填写验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(name)) {
            Toast.makeText(this, "请填写用户名", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pwd) || !pwd.matches("^[0-9a-zA-Z_\\*\\.\\?\\-\\+]{6,20}$")) {
            Toast.makeText(this, "密码格式不对,请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!TextUtils.equals(pwd, registerPwd2.getText().toString().trim())) {
            Toast.makeText(this, "两次密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(classId)) {
            Toast.makeText(this, "请选择商家类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(street.getText().toString())) {
            Toast.makeText(this, "请选择区域", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        JSONObject shopStr = new JSONObject();
        JSONObject smsStr = new JSONObject();
        try {
            shopStr.put("shopkeeperPhone", phone);
            shopStr.put("shopkeeperName", name);
            shopStr.put("classId", classId);
            shopStr.put("className", className);
            shopStr.put("pro", pro);
            shopStr.put("city", city);
            shopStr.put("district", district);
            shopStr.put("street", street.getText().toString());
            shopStr.put("lng", lng);
            shopStr.put("lat", lat);

            smsStr.put("code", code);
            smsStr.put("id", smsId);
            smsStr.put("phone", phone);

            params.put("shopStr", shopStr);
            params.put("smsStr", smsStr);
            params.put("password", MD5.md5Pwd(pwd));
            newCall(Config.Url.getUrl(Config.REGISTER), params);
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //得到屏幕高宽
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return new int[]{outMetrics.widthPixels, outMetrics.heightPixels};
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //可在其中解析amapLocation获取相应内容。
                pro=amapLocation.getProvince();
                city=amapLocation.getCity();
                district=amapLocation.getDistrict();
                lat=amapLocation.getLatitude()+"";
                lng=amapLocation.getLongitude()+"";
                Map<String, String> params = new HashMap<>();
                params.put("pro", amapLocation.getProvince());
                params.put("city", amapLocation.getCity());
                params.put("district", amapLocation.getDistrict());
                newCall(Config.Url.getUrl(Config.REGISTER_ADDRESS), params);
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.SMS_CODE:
                this.smsId = json.getJSONObject("sms").getString("id");
                Log.e("","丢了个雷姆"+smsId);
                break;
            case Config.REGISTER_ADDRESS:
                streets=new ArrayList<>();
                JSONArray arr1 = json.getJSONArray("tarifflist");
                Gson gson1 = new Gson();
                for (int i = 0; i < arr1.length(); i++) {
                    streets.add(gson1.fromJson(arr1.getString(i), Street.class));
                }
                break;
            case Config.SHOP_TYPE:
                JSONArray arr = json.getJSONArray("genericClassList");
                secondType = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    secondType.add(gson.fromJson(arr.getString(i), ShopType.class));
                }
                if (shopSecondTypeAdapter!=null)shopSecondTypeAdapter.refresh(secondType);
                break;
            case Config.REGISTER:
//                ((MyApplication) getApplication()).setInfo(new Gson().fromJson(
//                        json.getString("user"), Info.class));

                startActivity(new Intent(OpenShopActivity.this, XXRZActivity.class));
                Toast.makeText(this, ""+json, Toast.LENGTH_SHORT).show();
                Log.e("REGISTER","丢了个雷姆    "+json);
//                finish();
                break;
        }
    }

}
