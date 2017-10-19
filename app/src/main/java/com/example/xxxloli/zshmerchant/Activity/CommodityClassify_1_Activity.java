package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.ClassifyExamineAdapter;
import com.example.xxxloli.zshmerchant.adapter.TableAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.objectmodel.Table;
import com.example.xxxloli.zshmerchant.view.MyListView;
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

public class CommodityClassify_1_Activity extends BaseActivity implements ClassifyExamineAdapter.Callback{


    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.classify_show)
    MyListView classifyShow;
    @BindView(R.id.add)
    TextView add;
    @BindView(R.id.sure)
    Button sure;

    private ArrayList<Classify> classifies;
    private ClassifyExamineAdapter classifyExamineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_commodity_classify_1_);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", "402880e75f000ab6015f0043a1fc0004");
        newCall(Config.Url.getUrl(Config.GET_Classify_1), params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_commodity_classify_1_;
    }

    @OnClick({R.id.back_rl, R.id.add, R.id.sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                break;
            case R.id.add:
                addOrEditClassify(false,0);
                break;
            case R.id.sure:
                break;
        }
    }

    private void addOrEditClassify(final boolean isEdit, final int p) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_classify_1, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final EditText classify_name = view.findViewById(R.id.classify_name);
        final EditText number_ET = view.findViewById(R.id.number_ET);
        if (isEdit){
            TextView title=view.findViewById(R.id.title);
            title.setText("编辑分类");
            classify_name.setText(classifies.get(p).getProductClassName());
            number_ET.setText(classifies.get(p).getSort()+"");
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(classify_name.getText().toString())){
                    Toast.makeText(CommodityClassify_1_Activity.this,"请输入正确的分类名",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isEmpty(number_ET.getText().toString())){
                    Toast.makeText(CommodityClassify_1_Activity.this,"请输入正确的序列号",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                JSONObject productClassStr = new JSONObject();
                try {

//                    productClassStr [ shopName 店名, shopId 店铺id, shopkeeperId 店主id, shopkeeperName 店主名称, sort 排序号,
//                    productClassName 分类名称, fatherId 父级id,一级分类填写字符串“0”,id：修改时分类的id必填]
                    productClassStr.put("shopName", "asdhfkjhasd");

                    productClassStr.put("shopId", "402880e75f000ab6015f0043a1fc0004");

                    productClassStr.put("shopkeeperId", "402880e75f000ab6015f0043a1210002");

                    productClassStr.put("shopkeeperName", "梁非凡");

                    productClassStr.put("sort", number_ET.getText().toString());
                    productClassStr.put("productClassName", classify_name.getText().toString());

                    if (isEdit){
                        productClassStr.put("id", classifies.get(p).getId());
                    }
                    productClassStr.put("fatherId", "0");

                    params.put("productClassStr", productClassStr);
                    newCall(Config.Url.getUrl(Config.ADD_Classify), params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
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
        switch (tag.toString()) {
            case Config.DELETE_Classify:
            case Config.ADD_Classify:
                initView();
                break;
            case Config.GET_Classify_1:
                JSONArray arr = json.getJSONArray("listclass");
                if (arr.length() == 0) return;
                classifies = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    classifies.add(gson.fromJson(arr.getString(arr.length()-i-1), Classify.class));
                }
                classifyExamineAdapter = new ClassifyExamineAdapter(this, classifies,this);
                classifyShow.setAdapter(classifyExamineAdapter);
                classifyExamineAdapter.refresh(classifies);
                break;
        }
    }
    @Override
    public void click(final View v) {
        switch (v.getId()){
            case R.id.delete_bt:
//                Toast.makeText(this, "listview的内部的按钮被点击了！，位置是-->" + (Integer) v.getTag() + ",内容是-->"
//                        + classifies.get((Integer) v.getTag()), Toast.LENGTH_SHORT).show();
                View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_sure, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
                TextView title = view1.findViewById(R.id.title);
                Button sure = view1.findViewById(R.id.sure_bt);
                Button cancel = view1.findViewById(R.id.cancel_bt);
                title.setText("确认删除吗");
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> params = new HashMap<>();
//                productClassId 分类ID， userId 用户id
                        params.put("productClassId", classifies.get((Integer) v.getTag()).getId());
                        params.put("userId", "402880e75f000ab6015f0043a1210002");
                        newCall(Config.Url.getUrl(Config.DELETE_Classify), params);
                        alertDialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(view1);
                alertDialog.show();
                break;
            case R.id.edit_bt:
                addOrEditClassify(true, (Integer) v.getTag());
                break;
        }

    }
}
