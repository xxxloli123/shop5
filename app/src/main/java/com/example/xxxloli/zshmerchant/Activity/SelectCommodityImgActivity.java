package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.SelectCommodityImgAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.SelectCommodityImg;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectCommodityImgActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.show_list)
    GridView showList;
    @BindView(R.id.scan_et)
    EditText scanEt;
    @BindView(R.id.scan_rl)
    RelativeLayout scanRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_commodity_img);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.getStringExtra("classId") != null) {
            submit("classId", intent.getStringExtra("classId"));
        } else if (intent.getStringExtra("number") != null) {
            submit("number", intent.getStringExtra("number"));
        } else if (intent.getStringExtra("scan") != null) {
            scanRl.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void submit(String type, String classId) {
        Map<String, Object> params = new HashMap<>();
        params.put(type, classId);
        newCall(Config.Url.getUrl(Config.GET_SelectCommodityImg), params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_commodity_img;
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        JSONArray arr = json.getJSONObject("PictureLibraryInfo").getJSONArray("aaData");
//                if (arr.length() == 0) return;
        final ArrayList<SelectCommodityImg> selectCommodityImgs = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 0; i < arr.length(); i++) {
            selectCommodityImgs.add(gson.fromJson(arr.getString(arr.length() - i - 1), SelectCommodityImg.class));
        }
        SelectCommodityImgAdapter selectCommodityImgAdapter = new SelectCommodityImgAdapter(this, selectCommodityImgs);
        showList.setAdapter(selectCommodityImgAdapter);
        showList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("pictureLibraryId", selectCommodityImgs.get(i).getId());
                intent.putExtra("img", selectCommodityImgs.get(i).getSmallImg());
                intent.putExtra("name", selectCommodityImgs.get(i).getImgName());
                intent.putExtra("describe", selectCommodityImgs.get(i).getImgTextDescription());
                intent.putExtra("SelectCommodityImg", selectCommodityImgs.get(i));
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }

    @OnClick({R.id.back_rl, R.id.scan_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.scan_bt:
                if (!scanEt.getText().toString().isEmpty()) {
                    submit("number",scanEt.getText().toString());
                }
                break;
        }
    }

}
