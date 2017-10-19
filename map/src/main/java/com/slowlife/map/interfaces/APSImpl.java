package com.slowlife.map.interfaces;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sgrape on 2017/5/13.
 * e-mail: sgrape1153@gmail.com
 */

public class APSImpl implements APSInterface, LocationSource,
        AMapLocationListener {
    private Context act;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    // /声明mLocationOption对象
    private AMapLocationClientOption mLocationOption;
    private AMapLocation mapLocation;
    private RegeocodeAddress result;
    private LatLonPoint point;
    private GeocodeSearch geocoderSearch;
    private ExecutorService mExecutorService;
    private List<OnApsChanged> listeners;

    public APSImpl(Context activity) {
        act = activity;
        listeners = new ArrayList<>();
    }


    public RegeocodeAddress getResult() {
        return result;
    }

    public LatLonPoint getPoint() {
        return point;
    }

    @Override
    public void onCreate() {
        mlocationClient = new AMapLocationClient(act);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
        geocoderSearch = new GeocodeSearch(act);
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        deactivate();
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(act);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
        if (mExecutorService!=null)
            mExecutorService.shutdown();
    }


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            if (this.mapLocation == null) this.mapLocation = amapLocation;
            if (mListener != null) mListener.onLocationChanged(amapLocation);
            if (point == null)
                point = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
            else {
                point.setLatitude(amapLocation.getLatitude());
                point.setLongitude(amapLocation.getLongitude());
            }
            getAddresses();
        } else {
            String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
            System.out.println(errText);
            for (OnApsChanged oap : listeners) oap.Fail();
        }
    }

    /**
     * 响应逆地理编码的请求
     */
    private void getAddresses() {
        if (mExecutorService == null) {
            mExecutorService = Executors.newSingleThreadExecutor();
        }

        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    RegeocodeQuery query = new RegeocodeQuery(point, 200,
                            GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
                    result = geocoderSearch.getFromLocation(query);// 设置同步逆地理编码请求
                    if (result.getAdCode() != null
                            && result.getCityCode() != null
                            && result.getProvince() != null)
                        handler.obtainMessage(0).sendToTarget();
                } catch (AMapException e) {
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        });
    }

    public void addListener(OnApsChanged l) {
        if (!listeners.contains(l))
            listeners.add(l);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    for (OnApsChanged oac : listeners) oac.onChanged(result, point);
                    break;
                case 1:
                    for (OnApsChanged oap : listeners) oap.Fail();
                    break;
            }
        }
    };
}
