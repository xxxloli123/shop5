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

public class MJActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.priority_TV)
    TextView priorityTV;
    @BindView(R.id.explain_TV)
    TextView explainTV;
    @BindView(R.id.man_ET)
    EditText manET;
    @BindView(R.id.minus_ET)
    EditText minusET;
    @BindView(R.id.save_bt)
    Button saveBt;

    private int textNumer;
    private String priority,explain,id;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mj);
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
        explain=activites.getActivityRemarks();
        nameET.setText(activites.getActivityName());
        priorityTV.setText(activites.getPriority() + "");
        manET.setText(activites.getFullFee()+"");
        minusET.setText(activites.getCutFee()+"");
        saveBt.setText("修改");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mj;
    }

    @OnClick({R.id.back_rl, R.id.priority_TV, R.id.explain_TV, R.id.save_bt})
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
        }
    }

    private void submit() {
        if (isEmpty(priorityTV.getText().toString())){
            Toast.makeText(MJActivity.this, "请填写优先级", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(explainTV.getText().toString())){
            Toast.makeText(MJActivity.this, "请填写活动说明", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(nameET.getText().toString())){
                Toast.makeText(MJActivity.this, "请填写活动名称 ", Toast.LENGTH_SHORT).show();
                return;
        }
        if (isEmpty(manET.getText().toString())){
            Toast.makeText(MJActivity.this, "请填写满多少 ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(minusET.getText().toString())){
            Toast.makeText(this, "请填写减多少 ", Toast.LENGTH_SHORT).show();
            return;
        }
//        "priority优先级 越大优先1-99", "shopId", "shopName",
//		     * "activityName 活动名称", "activityRemarks 活动备注说明"
//         * "type"{FullCut("满减活动"),Collection("成为会员送优惠券"),ShareFollow("分享关注送优惠券"),
//        参数：[shopactivityStr, userId]
//        满多少元 :fullFee 减多少元:cutFee
        Map<String, Object> params = new HashMap<>();
        JSONObject shopactivityStr = new JSONObject();
        try {
            if (id!=null)shopactivityStr.put("id", id);
            shopactivityStr.put("priority", priorityTV.getText().toString());
            shopactivityStr.put("shopId", shop.getId());
            shopactivityStr.put("shopName", shop.getShopName());
            shopactivityStr.put("activityName", nameET.getText().toString());
            shopactivityStr.put("activityRemarks", explain);
            shopactivityStr.put("type", "FullCut");
            shopactivityStr.put("fullFee", manET.getText().toString());
            shopactivityStr.put("cutFee", minusET.getText().toString());

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
                    Toast.makeText(MJActivity.this, "请填写活动说明", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(MJActivity.this, "请填写优先级", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text.getText().toString().length()==3){
                    Toast.makeText(MJActivity.this, "优先级只能为1到99的数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                priorityTV.setText(priority);
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
