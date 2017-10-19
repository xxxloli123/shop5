package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.SelectCommodityImgAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.SelectCommodityImg;
import com.example.xxxloli.zshmerchant.objectmodel.UniversalClassify;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_select_commodity_img);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        if (intent.getStringExtra("classId")==null){
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        params.put("classId", intent.getStringExtra("classId"));
        newCall(Config.Url.getUrl(Config.GET_SelectCommodityImg), params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_commodity_img;
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
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
        SelectCommodityImgAdapter selectCommodityImgAdapter=new SelectCommodityImgAdapter(this,selectCommodityImgs);
        showList.setAdapter(selectCommodityImgAdapter);
        showList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra("pictureLibraryId",selectCommodityImgs.get(i).getId());
                intent.putExtra("img",selectCommodityImgs.get(i).getSmallImg());
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }
}
