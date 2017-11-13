package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistributionServiceActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution_service);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }
}
