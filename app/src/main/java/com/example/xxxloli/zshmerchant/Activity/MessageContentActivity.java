package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageContentActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.type_text)
    TextView typeText;
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.content_text)
    TextView contentText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Intent intent = getIntent();
        String title= intent.getStringExtra("title");
        String content= intent.getStringExtra("content");
        if (isEmpty(title)) {
            Toast.makeText(this, "参数错误", Toast.LENGTH_SHORT).show();

        }
        if (isEmpty(content)) {
            Toast.makeText(this, "参数错误2", Toast.LENGTH_SHORT).show();
            return;
        }
        titleText.setText(content);
        contentText.setText(title);
    }


    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }

    protected boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        else return false;
    }
}
