package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.fragment.AlreadyBuildFragmet;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Activites;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MZActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.priority_TV)
    TextView priorityTV;
    @BindView(R.id.explain_TV)
    TextView explainTV;
    @BindView(R.id.man_TV)
    TextView manTV;
    @BindView(R.id.commodity_name_TV)
    TextView commodityNameTV;
    @BindView(R.id.save_bt)
    Button saveBt;

    private int textNumer;
    private String priority,explain,man,commodityName,id;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mz);
        ButterKnife.bind(this);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(AlreadyBuildFragmet.EDIT_Activites) != null) {
            Activites activites = (Activites) intent.getSerializableExtra(AlreadyBuildFragmet.EDIT_Activites);
            id=activites.getId();
            initView(activites);
        }
    }

    private void initView(Activites activites) {
        nameET.setText(activites.getActivityName());
        priorityTV.setText(activites.getPriority() + "");
        manTV.setText(activites.getFullFee()+"");
        explain=activites.getActivityRemarks();
        saveBt.setText("修改");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mz;
    }

    @OnClick({R.id.back_rl, R.id.priority_TV, R.id.explain_TV, R.id.man_TV, R.id.commodity_name_TV, R.id.save_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.priority_TV:
                EditPriority();
                break;
            case R.id.explain_TV:
                EditExplain();
                break;
            case R.id.save_bt:
                submit();
                break;
            case R.id.man_TV:
                EditMan();
                break;
            case R.id.commodity_name_TV:
                EditCommodityName();
                break;
        }
    }

    private void EditCommodityName() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_shop_name, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(commodityNameTV.getText()) && !commodityNameTV.getText().equals("设置赠送商品名称")) {
            text.setText(commodityNameTV.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                commodityName = text.getText().toString().trim();
                if (isEmpty(commodityName)){
                    Toast.makeText(MZActivity.this, "请填写优先级", Toast.LENGTH_SHORT).show();
                    return;
                }
                commodityNameTV.setText(text.getText());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void submit() {
        if (isEmpty(priorityTV.getText().toString())){
            Toast.makeText(this, "请填写优先级", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(explain)){
            Toast.makeText(this, "请填写活动说明", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(nameET.getText().toString())){
            Toast.makeText(this, "请填写活动名称 ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(manTV.getText().toString())){
            Toast.makeText(this, "请设置满多少 ", Toast.LENGTH_SHORT).show();
            return;
        }
//        "priority优先级 越大优先1-99", "shopId", "shopName",
//		     * "activityName 活动名称", "activityRemarks 活动备注说明"
//         * "type"{FullGive("满赠活动"),FreeShipping("满免配送费活动")};
//        参数：[shopactivityStr, userId]
//        满多少元 :fullFee 减多少元:cutFee
        Map<String, Object> params = new HashMap<>();
        try {
            JSONObject shopactivityStr = new JSONObject();
            if (id!=null)shopactivityStr.put("id", id);
            shopactivityStr.put("priority", priorityTV.getText().toString());
            shopactivityStr.put("shopId", shop.getId());
            shopactivityStr.put("shopName", shop.getShopName());
            shopactivityStr.put("activityName", nameET.getText().toString());
            shopactivityStr.put("activityRemarks", explain);
            shopactivityStr.put("type", "FreeShipping");
            shopactivityStr.put("fullFee", manTV.getText().toString());

            params.put("shopactivityStr", shopactivityStr);
            params.put("userId", shop.getShopkeeperId());
            newCall(Config.Url.getUrl(Config.ADD_EDIT_Activity), params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void EditExplain() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_shop_notice, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(explain)) {
            text.setText(explain);
        }
        final TextView hint = view.findViewById(R.id.hint_text);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textNumer = charSequence.length();
                hint.setText("还能输入" + (140 - textNumer) + "个字符");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explain = text.getText().toString().trim();
                if (isEmpty(explain)){
                    Toast.makeText(MZActivity.this, "请填写活动说明", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditPriority() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_priority, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        TextView hint = view.findViewById(R.id.hint_text);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(priorityTV.getText())&&!priorityTV.getText().equals("设置优先级")) {
            text.setText(priorityTV.getText());
        }
        hint.setText("优先级只能为1到99的数字，数字越大越优先");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = text.getText().toString().trim();
                if (isEmpty(priority)){
                    Toast.makeText(MZActivity.this, "请填写优先级", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text.getText().toString().length()==3){
                    Toast.makeText(MZActivity.this, "优先级只能为1到99的数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                priorityTV.setText(priority);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditMan() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ordering_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        TextView hint = view.findViewById(R.id.hint_text);
        hint.setVisibility(View.GONE);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(manTV.getText()) && !manTV.getText().equals("设置满多少元")) {
            text.setText(manTV.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                man = text.getText().toString().trim();
                if (isEmpty(man)) {
                    Toast.makeText(MZActivity.this, "请填写优惠券金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                manTV.setText(man);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        if (json.getInt("statusCode") == 200) {
            Toast.makeText(this, "创建活动成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
