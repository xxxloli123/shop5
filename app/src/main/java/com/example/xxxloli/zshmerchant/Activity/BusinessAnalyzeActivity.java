package com.example.xxxloli.zshmerchant.Activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessAnalyzeActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.start_data)
    TextView startData;
    @BindView(R.id.end_data)
    TextView endData;
    @BindView(R.id.business_money)
    TextView businessMoney;
    @BindView(R.id.expenditure)
    TextView expenditure;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.valid_number)
    TextView validNumber;

    private int year,month,day;
    private int startYear=0,startMonth=0,startDay=0;
    private int endYear=0,endMonth=0,endDay=0;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_analyze);
        ButterKnife.bind(this);
        getDate();
    }

    @OnClick({R.id.back_rl, R.id.start_data, R.id.end_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.start_data:
                selectStartData();
                break;
            case R.id.end_data:
                selectEndData();
                break;
        }
    }

    private void selectEndData() {
        DatePickerDialog.OnDateSetListener listener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day) {
                //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
                endData.setText(year+"."+(++month)+"."+day);
                endYear=year;
                endMonth=month;
                endDay=day;
            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog=new DatePickerDialog(BusinessAnalyzeActivity.this,0 ,listener,year,month,day);
        DatePicker datePicker=dialog.getDatePicker();
        long time = cal.getTimeInMillis();
        datePicker.setMaxDate(time);
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
                startData.setText(year+"."+(++month)+"."+day);
                startYear=year;
                startMonth=month;
                startDay=day;

            }
        };
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog=new DatePickerDialog(BusinessAnalyzeActivity.this,0 ,listener,year,month,day);
        DatePicker datePicker=dialog.getDatePicker();
        Calendar cal=Calendar.getInstance();
        if (endYear!=0)cal.set(endYear,endMonth-1,endDay);//获取到的月份是从0开始计数
        long time = cal.getTimeInMillis();
        datePicker.setMaxDate(time);
        dialog.show();
    }

}
