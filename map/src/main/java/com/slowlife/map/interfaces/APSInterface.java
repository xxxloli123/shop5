package com.slowlife.map.interfaces;

import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.RegeocodeAddress;

/**
 * Created by sgrape on 2017/5/13.
 * e-mail: sgrape1153@gmail.com
 */

public interface APSInterface {
    void onCreate();

    void onPause();


    public static interface APSInstance {
        void setAPS(APSInterface aps);
    }

    public static interface OnApsChanged {
        void onChanged(RegeocodeAddress address, LatLonPoint point);

        void Fail();
    }

}
