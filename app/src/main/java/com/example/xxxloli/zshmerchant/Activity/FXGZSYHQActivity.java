package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FXGZSYHQActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.YHQ_name_ET)
    EditText YHQNameET;
    @BindView(R.id.priority_TV)
    TextView priorityTV;
    @BindView(R.id.explain_TV)
    TextView explainTV;
    @BindView(R.id.peoples_TV)
    TextView peoplesTV;
    @BindView(R.id.YHQ_money_TV)
    TextView YHQMoneyTV;
    @BindView(R.id.YHQ_restrict_TV)
    TextView YHQRestrictTV;
    @BindView(R.id.start_data_TV)
    TextView startDataTV;
    @BindView(R.id.end_data_TV)
    TextView endDataTV;
    @BindView(R.id.save_bt)
    Button saveBt;

    private int textNumer;
    private String priority, explain,money,restrict,peoples,id;
    private int year,month,day;
    private int startYear=0,startMonth=0,startDay=0;
    private int endYear=0,endMonth=0,endDay=0;
    private Calendar cal;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fxgzsyhq);
        ButterKnife.bind(this);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(AlreadyBuildFragmet.EDIT_Activites) != null) {
            Activites activites = (Activites) intent.getSerializableExtra(AlreadyBuildFragmet.EDIT_Activites);
            id=activites.getId();
            initView(activites);
        }
        getDate();
    }

    private void initView(Activites activites) {
        nameET.setText(activites.getActivityName());
        YHQNameET.setText(activites.getCouponName());
        priorityTV.setText(activites.getPriority() + "");
        YHQMoneyTV.setText(activites.getCost() + "");
        YHQRestrictTV.setText(activites.getLimitCost() + "");
        peoplesTV.setText(activites.getFollowNumber()+"");
        startDataTV.setText(activites.getStartTime().substring(0, 10));
        endDataTV.setText(activites.getEndTime().substring(0, 10));
        saveBt.setText("修改");
    }

    private void submit() {
        if (isEmpty(priorityTV.getText().toString())) {
            Toast.makeText(this, "请设置优先级", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(explain)) {
            Toast.makeText(this, "请设置活动说明", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(nameET.getText().toString())) {
            Toast.makeText(this, "请填写活动名称 ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(YHQNameET.getText().toString())) {
            Toast.makeText(this, "请填写优惠券名称 ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(YHQMoneyTV.getText().toString())) {
            Toast.makeText(this, "请设置优惠券金额 ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(YHQRestrictTV.getText().toString())) {
            Toast.makeText(this, "请设置优惠券使用限制金额 ", Toast.LENGTH_SHORT).show();
            return;
        }
//        "priority优先级 越大优先1-99", "shopId", "shopName",
//		     * "activityName 活动名称", "activityRemarks 活动备注说明"
//         * "type"{FullCut("满减活动"),Collection("成为会员送优惠券"),ShareFollow("分享关注送优惠券"),
//          followNumber :分享关注人数
//        limitCost 优惠券使用限制金额（如满20可以使用） 优惠券金额 :cost 优惠券名称 :couponName
//         优惠券使用开始时间 startTime 优惠券使用结束时间 :endTime , ,
//        参数：[shopactivityStr, userId]
        Map<String, Object> params = new HashMap<>();
        JSONObject shopactivityStr = new JSONObject();
        try {
            if (id!=null)shopactivityStr.put("id", id);
            shopactivityStr.put("priority", priorityTV.getText().toString());
            shopactivityStr.put("shopId", shop.getId());
            shopactivityStr.put("shopName", shop.getShopName());
            shopactivityStr.put("activityName", nameET.getText().toString());
            shopactivityStr.put("activityRemarks", explain);

            shopactivityStr.put("type", "Collection");
            shopactivityStr.put("followNumber", peoplesTV.getText().toString());
            shopactivityStr.put("limitCost", YHQRestrictTV.getText().toString());
            shopactivityStr.put("cost", YHQMoneyTV.getText().toString());
            shopactivityStr.put("couponName", YHQNameET.getText().toString());
            shopactivityStr.put("startTime", startDataTV.getText().toString());
            shopactivityStr.put("endTime", endDataTV.getText().toString());

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
                hint.setText("还能输入" + (150 - textNumer) + "个字符");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                explain = text.getText().toString().trim();
                if (isEmpty(explain)) {
                    Toast.makeText(FXGZSYHQActivity.this, "请填写活动说明", Toast.LENGTH_SHORT).show();
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
        if (!isEmpty(priorityTV.getText()) && !priorityTV.getText().equals("设置优先级")) {
            text.setText(priorityTV.getText());
        }
        hint.setText("优先级只能为1到99的数字，数字越大越优先");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                priority = text.getText().toString().trim();
                if (isEmpty(priority)) {
                    Toast.makeText(FXGZSYHQActivity.this, "请填写优先级", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (text.getText().toString().length() == 3) {
                    Toast.makeText(FXGZSYHQActivity.this, "优先级只能为1到99的数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                priorityTV.setText(priority);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditYHQ_Money() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ordering_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        TextView hint = view.findViewById(R.id.hint_text);
        hint.setVisibility(View.GONE);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(YHQMoneyTV.getText()) && !YHQMoneyTV.getText().equals("设置优惠券金额")) {
            text.setText(YHQMoneyTV.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money = text.getText().toString().trim();
                if (isEmpty(money)) {
                    Toast.makeText(FXGZSYHQActivity.this, "请填写优惠券金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                YHQMoneyTV.setText(money);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditYHQ_Restrict() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ordering_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        TextView hint = view.findViewById(R.id.hint_text);
        hint.setVisibility(View.GONE);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(YHQRestrictTV.getText()) && !YHQRestrictTV.getText().equals("设置优惠券使用限制金额")) {
            text.setText(YHQRestrictTV.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restrict = text.getText().toString().trim();
                if (isEmpty(restrict)) {
                    Toast.makeText(FXGZSYHQActivity.this, "请填写优惠券金额", Toast.LENGTH_SHORT).show();
                    return;
                }
                YHQRestrictTV.setText(restrict);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditPeoples() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_ordering_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        TextView hint = view.findViewById(R.id.hint_text);
        hint.setVisibility(View.GONE);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(peoplesTV.getText()) && !peoplesTV.getText().equals("设置分享关注人数")) {
            text.setText(peoplesTV.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peoples = text.getText().toString().trim();
                if (isEmpty(peoples)) {
                    Toast.makeText(FXGZSYHQActivity.this, "请填写分享关注人数", Toast.LENGTH_SHORT).show();
                    return;
                }
                peoplesTV.setText(peoples);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_fxgzsyhq;
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        if (json.getInt("statusCode") == 200) {
            Toast.makeText(this, "创建活动成功", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @OnClick({R.id.back_rl, R.id.priority_TV, R.id.explain_TV, R.id.YHQ_money_TV, R.id.peoples_TV,
            R.id.YHQ_restrict_TV, R.id.start_data_TV, R.id.end_data_TV, R.id.save_bt})
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
            case R.id.YHQ_money_TV:
                EditYHQ_Money();
                break;
            case R.id.YHQ_restrict_TV:
                EditYHQ_Restrict();
                break;
            case R.id.start_data_TV:
                selectStartData();
                break;
            case R.id.end_data_TV:
                selectEndData();
                break;
            case R.id.save_bt:
                submit();
                break;
            case R.id.peoples_TV:
                EditPeoples();
                break;
        }
    }

    private void selectEndData() {
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                endYear=year;
                endMonth=month;
                endDay=day;
                String m=(endMonth<10)? "0"+endMonth:""+endMonth;
                String d=(endDay<10)? "0"+endDay:""+endDay;
                endDataTV.setText(year+"-"+m+"-"+d);
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog=new DatePickerDialog(this,0 ,listener,year,month,day);
        DatePicker datePicker=dialog.getDatePicker();
        long time = cal.getTimeInMillis();
        datePicker.setMinDate(time);
        dialog.show();
    }

    //获取当前日期
    private void getDate() {
        cal=Calendar.getInstance();
        year=cal.get(Calendar.YEAR);       //获取年月日时分秒
        Log.i("wxy","year"+year);
        month=cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        day=cal.get(Calendar.DAY_OF_MONTH);
    }

    private void selectStartData() {
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, final int year, int month, int day) {
                //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                startYear=year;
                startMonth=month;
                startDay=day;
                String m=(startMonth<10)? "0"+startMonth:""+startMonth;
                String d=(startDay<10)? "0"+startDay:""+startDay;
                startDataTV.setText(year + "-" + m + "-" + d);
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog=new DatePickerDialog(this,0 ,listener,year,month,day);
        DatePicker datePicker=dialog.getDatePicker();
        Calendar cal=Calendar.getInstance();
        long time = cal.getTimeInMillis();
        datePicker.setMinDate(time);
        dialog.show();
    }

}
