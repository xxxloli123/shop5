package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.fragment.FragMessageList;
import com.example.xxxloli.zshmerchant.objectmodel.Message;

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
    @BindView(R.id.time_TV)
    TextView timeTV;

    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_content);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(FragMessageList.READ_Message) != null) {
            message = (Message) intent.getSerializableExtra(FragMessageList.READ_Message);
            initView();
        } else {
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        typeText.setText(message.getType_value());
        titleText.setText(message.getTitle());
        contentText.setText("   "+message.getContent());
        timeTV.setText(message.getCreateDate());
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
