package com.slowlife.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.DisplayUtil;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptor;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.Circle;
import com.amap.api.maps2d.model.CircleOptions;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MapActivity extends AppCompatActivity implements LocationSource,
        AMapLocationListener, AMap.OnMapClickListener, AMap.OnMarkerDragListener {

    public static final String RESULT_ADDR = "result_addr";
    public static final String LATLNG = "latlng";
    MapView mapView;
    protected AMap aMap;
    protected UiSettings uiSettings;
    protected LocationSource.OnLocationChangedListener mListener;
    protected AMapLocationClient mlocationClient;
    // /声明mLocationOption对象
    protected AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean mFirstFix = false;
    private Marker mLocMarker;
    private SensorEventHelper mSensorHelper;
    private Circle mCircle;
    private Marker addr;
    private AMapLocation mapLocation;
    private GeocodeSearch geocoderSearch;
    private ExecutorService mExecutorService;
    private RegeocodeAddress result;
    private LatLonPoint point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        point = getIntent().getParcelableExtra(LATLNG);
        initMap();
    }

    /**
     * 初始化地图
     */
    private void initMap() {

        if (aMap == null) {
            if (mapView == null) return;
            aMap = mapView.getMap();
            uiSettings = aMap.getUiSettings();
            uiSettings.setScaleControlsEnabled(true);
        }
        aMap.setOnMapClickListener(this);
        aMap.setOnMarkerDragListener(this);
        mlocationClient = new AMapLocationClient(this);
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
        geocoderSearch = new GeocodeSearch(this);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.search) {
            Intent intent = new Intent(this, SearchAddrActivity.class);
            if (mapLocation != null) {
                LatLng latLng = new LatLng(mapLocation.getLatitude(), mapLocation.getLongitude());
                intent.putExtra(SearchAddrActivity.LATLNG, latLng);
                intent.putExtra(SearchAddrActivity.CITY, mapLocation.getProvider());
            }
            startActivityForResult(intent, 102);
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (mapView != null)
            mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
        mFirstFix = false;
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null)
            mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapView != null) mapView.onDestroy();
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
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
    }


    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {

        // latitude=29.589898#longitude=106.480093

        if (amapLocation != null && amapLocation.getErrorCode() == 0) {
            if (this.mapLocation == null) this.mapLocation = amapLocation;
            if (mListener != null) mListener.onLocationChanged(amapLocation);
            LatLng location = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
            if (!mFirstFix) {
                mFirstFix = true;
                addCircle(location, amapLocation.getAccuracy());//添加定位精度圆
                addMarker(location);//添加定位图标
//                mSensorHelper.setCurrentMarker(mLocMarker);//定位图标旋转
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
            } else {
                mCircle.setCenter(location);
                mCircle.setRadius(amapLocation.getAccuracy());
                mLocMarker.setPosition(location);
            }
//            changeCamera(
//                    CameraUpdateFactory.newCameraPosition(new CameraPosition(
//                            new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude()), 18, 30, 30)));
        } else {
            String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
            Toast.makeText(this, errText, Toast.LENGTH_SHORT).show();
            Log.e("AmapErr", errText);
        }
    }


    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    protected void changeCamera(CameraUpdate update) {
        if (aMap != null)
            aMap.moveCamera(update);
    }

    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    private void addMarker(LatLng latlng) {
        if (mLocMarker != null) {
            return;
        }
        Bitmap bMap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.gps_point);
        Matrix matrix = new Matrix();
        int size = DisplayUtil.dip2px(this, 5);
        matrix.postScale(size, size);
        bMap = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(), bMap.getHeight(), matrix, true);
        BitmapDescriptor des = BitmapDescriptorFactory.fromBitmap(bMap);

//		BitmapDescriptor des = BitmapDescriptorFactory.fromResource(R.drawable.navi_map_gps_locked);
        MarkerOptions options = new MarkerOptions();
        options.icon(des);
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        mLocMarker = aMap.addMarker(options);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (addr == null) {
            MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                    .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    .position(latLng)
                    .draggable(true);
            addr = aMap.addMarker(markerOption);
            addr.setDraggable(true);
        } else
            addr.setPosition(latLng);
        getAddresses(new LatLonPoint(latLng.latitude, latLng.longitude));
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        System.out.println(marker);
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            PoiItem poiItem = data.getParcelableExtra(SearchAddrActivity.RESULT);
            if (poiItem == null) return;
            LatLonPoint latLonPoint = poiItem.getLatLonPoint();
            LatLng latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
            if (addr == null) {
                MarkerOptions markerOption = new MarkerOptions().icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .position(latLng)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .draggable(true);
                addr = aMap.addMarker(markerOption);
                addr.showInfoWindow();
                addr.setDraggable(true);
            } else addr.setPosition(latLng);
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
            getAddresses(new LatLonPoint(poiItem.getLatLonPoint().getLatitude(), poiItem.getLatLonPoint().getLongitude()));
        }
    }

    @Override
    public void finish() {
        if (result != null) {
            Intent intent = new Intent();
            intent.putExtra(RESULT_ADDR, result);
            intent.putExtra(LATLNG, point);
            setResult(Activity.RESULT_OK, intent);
        }
        super.finish();
    }

    /**
     * 响应逆地理编码的请求
     */
    private void getAddresses(final LatLonPoint point) {
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
                    MapActivity.this.point = point;
                    handler.obtainMessage(0).sendToTarget();
                } catch (AMapException e) {
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    StringBuilder sb = new StringBuilder();
                    sb.append(result.getProvince())
                            .append(" ")
                            .append(result.getCity())
                            .append(" ")
                            .append(result.getDistrict())
                            .append(" ")
                            .append(result.getTownship());
                    addr.setTitle(sb.toString());
                    addr.showInfoWindow();
                    break;
                case 1:
                    Toast.makeText(MapActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
