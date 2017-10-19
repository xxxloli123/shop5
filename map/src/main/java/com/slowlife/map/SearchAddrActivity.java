package com.slowlife.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps2d.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import java.util.ArrayList;
import java.util.List;

public class SearchAddrActivity extends AppCompatActivity implements TextWatcher,
        PoiSearch.OnPoiSearchListener, AdapterView.OnItemClickListener {

    public static final String CITY = "city";
    public static final String LATLNG = "latlng";
    public static final String RESULT = "result";

    private EditText search;
    private ListView listView;
    private PoiSearch poiSearch;
    private int currentPage = 1;
    private PoiSearch.Query query;
    private String city;
    private LatLonPoint lp;
    private List<PoiItem> pois;
    private PoiAdapter adapter;
    private String keyWord;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_addr);
        search = (EditText) findViewById(R.id.search_keyword);
        listView = (ListView) findViewById(R.id.search_listview);
        search.addTextChangedListener(this);
        city = getIntent().getStringExtra(CITY);
        LatLng latLng = getIntent().getParcelableExtra(LATLNG);
        if (latLng != null) lp = new LatLonPoint(latLng.latitude, latLng.longitude);
        pois = new ArrayList<>();
        adapter = new PoiAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }


    public void onClick(View v) {
        if (v.getId() == R.id.search_back) {
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        currentPage = 1;
        keyWord = s.toString().trim();
        pois.clear();
        doSearchQuery();
    }
    /**
     * 开始进行poi搜索
     */
    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        query = new PoiSearch.Query(keyWord, "", city);
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);// 设置查第一页
        if (lp != null) {
            poiSearch = new PoiSearch(this, query);
            poiSearch.setOnPoiSearchListener(this);
            poiSearch.setBound(new PoiSearch.SearchBound(lp, 5000, true));//
            // 设置搜索区域为以lp点为圆心，其周围5000米范围
            poiSearch.searchPOIAsyn();// 异步搜索
        }
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int rcode) {
        if (rcode != AMapException.CODE_AMAP_SUCCESS) return;
        pois.addAll(poiResult.getPois());
        /*
        PoiItem item = pois.get(0);
        if (item != null) {
            Class<? extends PoiItem> clz = item.getClass();
            Method[] methods = clz.getMethods();
            for (Method m : methods) {
                m.setAccessible(true);
                try {
                    String methodName = m.getName();
                    if (methodName.startsWith("get"))
                        System.out.println(m.getName() + "==>" + m.invoke(item));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        */
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PoiItem item = pois.get((int) id);
        Intent intent = new Intent();
        intent.putExtra(RESULT, item);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    class PoiAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return pois.size();
        }

        @Override
        public Object getItem(int position) {
            return pois.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            PoiItem item = pois.get(position);
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.item_search_poi, null, false);
                vh = new ViewHolder(convertView);
                convertView.setTag(convertView);
            } else vh = (ViewHolder) convertView.getTag();
            vh.addr.setText(item.getTitle());
            if (item.getCityName() != null)
                vh.city.setText(item.getAdName());
            if (pois.size() % 20 == 0 && position >= pois.size() - 5) {
                ++currentPage;
                doSearchQuery();
            }
            convertView.setTag(vh);
            return convertView;
        }
    }

    class ViewHolder {
        TextView addr, city;

        public ViewHolder(View v) {
            addr = (TextView) v.findViewById(R.id.addr);
            city = (TextView) v.findViewById(R.id.city);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
            currentPage = 1;
            keyWord = search.getText().toString().trim();
            pois.clear();
            doSearchQuery();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
