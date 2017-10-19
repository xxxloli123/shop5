package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.open_shop)
    Button openShop;
    @BindView(R.id.login)
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.open_shop, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_shop:
                startActivity(new Intent(FirstActivity.this, OpenShopActivity.class));
                break;
            case R.id.login:
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                break;
        }
    }
}
