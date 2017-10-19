package com.example.xxxloli.zshmerchant.Activity;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
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


    private String  startHour ="2333", startMinute ="2333" , endHour ="2333", endMinute ="2333";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_business_time);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
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
        int start= Integer.parseInt(startHour);
        int end= Integer.parseInt(endHour);
        if (start>end){
            Toast.makeText(this,"请输入正确的营业时间",Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> params = new HashMap<>();
        JSONObject shopStr = new JSONObject();
        try {
            shopStr.put("id", "402880e75f000ab6015f0043a1fc0004");
            shopStr.put("startDate", startHour+"-"+startMinute);
            shopStr.put("endDate", endHour+"-"+endMinute);

            params.put("shopStr", shopStr);
            params.put("userId", "402880e75f000ab6015f0043a1210002");
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
                    startHour = String.valueOf(i);
                    startMinute = String.valueOf(i1);
                    if (startHour.length()==1){
                        StringBuilder  sb = new StringBuilder (startHour);
                        sb.insert(0,"0");
                        startHour=sb.toString();
                    }
                    if (startMinute.length()==1){
                        StringBuilder  sb = new StringBuilder (startMinute);
                        sb.insert(0,"0");
                        startMinute=sb.toString();
                    }
                    startTime.setText(startHour+" : "+startMinute);
                } else {
                    endHour = String.valueOf(i);
                    endMinute = String.valueOf(i1);
                    if (endHour.length()==1){
                        StringBuilder  sb = new StringBuilder (endHour);
                        sb.insert(0,"0");
                        endHour=sb.toString();
                    }
                    if (endMinute.length()==1){
                        StringBuilder  sb = new StringBuilder (endMinute);
                        sb.insert(0,"0");
                        endMinute=sb.toString();
                    }
                    endTime.setText(endHour+" : "+endMinute);
                }
            }
        }, 0, 0, true);
        timePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (startHour.equals("2333")&&endHour.equals("2333"))return;
                int start= Integer.parseInt(startHour);
                int end= Integer.parseInt(endHour);
                if (start>end){
                    hint.setVisibility(View.VISIBLE);
                    return;
                }else {
                    hint.setVisibility(View.GONE);
                }
            }
        });
        timePickerDialog.show();
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
    }
}
