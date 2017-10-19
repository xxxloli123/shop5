package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DistributionActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.is_huidi)
    TextView isHuidi;
    @BindView(R.id.distribution_service)
    RelativeLayout distributionService;
    @BindView(R.id.img)
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distribution);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_rl, R.id.distribution_service})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.distribution_service:
                startActivity(new Intent(this, DistributionServiceActivity.class));
                break;
        }
    }
}
