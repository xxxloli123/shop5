package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
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
    private String priority,explain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mj);
        ButterKnife.bind(this);
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
        if (isEmpty(priority)){
            Toast.makeText(MJActivity.this, "请填写优先级", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(explain)){
            Toast.makeText(MJActivity.this, "请填写活动说明", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(nameET.getText())){
                Toast.makeText(MJActivity.this, "请填写活动名称 ", Toast.LENGTH_SHORT).show();
                return;
        }
//        "priority优先级 越大优先1-99", "shopId", "shopName",
//		     * "activityName 活动名称", "activityRemarks活动备注说明"
//         * "type"{FullCut("满减活动"),Collection("成为会员送优惠券"),ShareFollow("分享关注送优惠券"),
        Map<String, Object> params = new HashMap<>();
        JSONObject shopactivityStr = new JSONObject();
        try {
            shopactivityStr.put("priority", priority);
            shopactivityStr.put("shopId", "402880e75f000ab6015f0043a1fc0004");
            shopactivityStr.put("shopName", "asdhfkjhasd");
            shopactivityStr.put("activityName", "asdhfkjhasd");

            params.put("shopStr", shopStr);
            params.put("userId", "402880e75f000ab6015f0043a1210002");
            newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
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
        if (!isEmpty(priorityTV.getText())) {
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
        if (json.getInt("statusCode") == 200) {}
    }
}
