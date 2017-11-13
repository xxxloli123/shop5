package com.example.xxxloli.zshmerchant.Activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.DBManagerUser;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.greendao.User;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessTimeActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.save_bt)
    Button saveBt;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.hint)
    TextView hint;

    private DBManagerShop dbManagerShop;
    private Shop shop;

    private int  startHour , startMinute  , endHour , endMinute;
    private String sH,sM,eH,eM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_business_time);
        dbManagerShop=DBManagerShop.getInstance(this);
        shop=dbManagerShop.queryById((long) 2333).get(0);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        sH= shop.getStartDate().substring(0,2);
        sM  = shop.getStartDate().substring(3,5);
        eH= shop.getEndDate().substring(0,2);
        eM= shop.getEndDate().substring(3,5);
        startHour= Integer.parseInt(sH);
        startMinute= Integer.parseInt(sM);
        endHour= Integer.parseInt(eH);
        endMinute= Integer.parseInt(eM);
        startTime.setText(sH+" : "+sM);
        endTime.setText(eH+" : "+eM);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_business_time;
    }


    @OnClick({R.id.back_rl, R.id.save_bt, R.id.start_time, R.id.end_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.save_bt:
                submit();
                break;
            case R.id.start_time:
                selectTime(true);
                break;
            case R.id.end_time:
                selectTime(false);
                break;
        }
    }

    private void submit() {
        if (startHour>endHour){
            Toast.makeText(this,"请输入正确的营业时间",Toast.LENGTH_SHORT).show();
            return;
        }
        if (startHour==endHour&&startMinute>=endMinute){
            Toast.makeText(this,"请输入正确的营业时间",Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, Object> params = new HashMap<>();
        JSONObject shopStr = new JSONObject();
        try {
            shopStr.put("id", shop.getId());
            shopStr.put("startDate", sH+"-"+sM);
            shopStr.put("endDate", eH+"-"+eM);

            params.put("shopStr", shopStr);
            params.put("userId", shop.getShopkeeperId());
            newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void selectTime(final boolean isStart) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                if (isStart) {
                    startHour = i;
                    startMinute = i1;
                    sH=(startHour<10)?"0"+startHour:""+startHour;
                    sM=(startMinute<10)?"0"+startMinute:""+startMinute;
                    startTime.setText(sH+" : "+sM);
                } else {
                    endHour = i;
                    endMinute = i1;
                    eH=(endHour<10)?"0"+endHour:""+endHour;
                    eM=(endMinute<10)?"0"+endMinute:""+endMinute;
                    endTime.setText(eH+" : "+eM);
                }
            }
        }, 0, 0, true);
        timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (startHour>endHour) hint.setVisibility(View.VISIBLE);
                else if (startHour==endHour&&startMinute>=endMinute)hint.setVisibility(View.VISIBLE);
                else hint.setVisibility(View.GONE);
            }
        });
        timePickerDialog.show();
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        shop.setStartDate(sH+":"+sM);
        shop.setEndDate(eH+":"+eM);
        dbManagerShop.updateShop(shop);
        finish();
    }
}
