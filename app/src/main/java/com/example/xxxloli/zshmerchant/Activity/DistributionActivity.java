package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistributionActivity extends BaseActivity implements AMapLocationListener {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.is_huidi)
    TextView isHuidi;
    @BindView(R.id.distribution_service)
    RelativeLayout distributionService;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.location_RL)
    RelativeLayout locationRL;
    @BindView(R.id.address_TV)
    TextView addressTV;
    @BindView(R.id.text)
    TextView text;

    private AMapLocationClient mlocationClient;
    // /声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_distribution);
        ButterKnife.bind(this);
        initLocation();
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
    }

    private void initLocation() {
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
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_distribution;
    }

    @OnClick({R.id.back_rl, R.id.distribution_service,R.id.selective_dissemination})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.distribution_service:
                startActivity(new Intent(this, DistributionServiceActivity.class));
                break;
            case R.id.selective_dissemination:
                SelectiveDissemination();
                break;
        }
    }

    private void SelectiveDissemination() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_selective_dissemination, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final EditText distance = view.findViewById(R.id.distance);
        final EditText price = view.findViewById(R.id.price);
        if (shop.getDistance()!=null)distance.setText(shop.getDistance());
        if (shop.getDeliveryFee()!=null)price.setText(shop.getDeliveryFee());
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(distance.getText().toString())) {
                    Toast.makeText(DistributionActivity.this, "请输入距离", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEmpty(price.getText().toString())) {
                    Toast.makeText(DistributionActivity.this, "请输入价格", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
                    shopStr.put("id", shop.getId());
//                    deliveryFee 商家自己的配送费/distance 商 家自己配送的配送距离
                    shopStr.put("distance", distance.getText().toString());
                    shopStr.put("deliveryFee", price.getText().toString());

                    params.put("shopStr", shopStr);
                    params.put("userId", shop.getShopkeeperId());
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                    shop.setDistance(distance.getText().toString());
                    shop.setDeliveryFee(price.getText().toString());
                    alertDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        dbManagerShop.updateShop(shop);
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                addressTV.setText(amapLocation.getAddress());
                img.setVisibility(View.GONE);
                text.setVisibility(View.GONE);
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
                    shopStr.put("id", shop.getId());
//                    id 店铺id,lng 经度，lat 纬度
                    shopStr.put("lng", amapLocation.getLongitude() + "");
                    shopStr.put("lat", amapLocation.getLatitude() + "");

                    params.put("shopStr", shopStr);
                    params.put("userId", shop.getShopkeeperId());
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @OnClick(R.id.location_RL)
    public void onViewClicked() {
        //启动定位
        mlocationClient.startLocation();
    }
}
