package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_nameLL)
    LinearLayout userNameLL;
    @BindView(R.id.change_passwordLL)
    LinearLayout changePasswordLL;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.phoneLL)
    LinearLayout phoneLL;
    @BindView(R.id.manageLL)
    LinearLayout manageLL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back_rl, R.id.user_nameLL, R.id.change_passwordLL, R.id.phoneLL, R.id.manageLL})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.user_nameLL:
                changeName();
                break;
            case R.id.change_passwordLL:
                startActivity(new Intent(this, LoginPasswordActivity.class));
                break;
            case R.id.phoneLL:
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.manageLL:
                startActivity(new Intent(this, AccountManageActivity.class));
                break;
        }
    }

    private void changeName() {
        View view = LayoutInflater.from(MyAccountActivity.this).inflate(R.layout.dialog_shop_address, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(MyAccountActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(userName.getText())) {
            text.setText(userName.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName.setText(text.getText());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

}
