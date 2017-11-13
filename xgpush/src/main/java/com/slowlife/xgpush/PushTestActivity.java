package com.slowlife.xgpush;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.tencent.android.tpush.XGPushTextMessage;

/**
 * Created by sgrape on 2017/5/8.
 * e-mail: sgrape1153@gmail.com
 */

public class PushTestActivity extends AppCompatActivity implements XgReceiver.PushCallback {
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_test);
        editText = (EditText) findViewById(R.id.push_test_edit);
    }

    public void onClick(View view) {
        editText.setText("");
    }

    @Override
    protected void onResume() {
        XgReceiver.PushObserver.regisiter(this);
        super.onResume();
    }

    @Override
    public void onPush(XGPushTextMessage message) {
        editText.append("\n\n");
        editText.append(message.getContent());
        System.out.println(message);
    }

    @Override
    public void onPush(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                editText.append("\n\n");
                editText.append(message);
            }
        });
        System.out.println(message);
    }

    @Override
    protected void onPause() {
        XgReceiver.PushObserver.unRegisiter(this);
        super.onPause();
    }
}
