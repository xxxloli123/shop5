package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.my_accountLL)
    LinearLayout myAccountLL;
    @BindView(R.id.notificationLL)
    LinearLayout notificationLL;
    @BindView(R.id.helpLL)
    LinearLayout helpLL;
    @BindView(R.id.printerLL)
    LinearLayout printerLL;
    @BindView(R.id.complainLL)
    LinearLayout complainLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_rl, R.id.my_accountLL, R.id.notificationLL, R.id.helpLL, R.id.printerLL, R.id.complainLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.my_accountLL:
                startActivity(new Intent(this, MyAccountActivity.class));
                break;
            case R.id.notificationLL:
                break;
            case R.id.helpLL:
                break;
            case R.id.printerLL:
                break;
            case R.id.complainLL:
                startActivity(new Intent(this, ComplainActivity.class));
                break;
        }
    }
}
