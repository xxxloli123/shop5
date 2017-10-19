package com.example.xxxloli.zshmerchant.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


import com.example.xxxloli.zshmerchant.R;

import java.util.Timer;
import java.util.TimerTask;


public class TimeButton extends Button implements OnClickListener {
    private long lenght = 60 * 1000;//倒计时长度,这里给了默认60秒
    private String textafters = "重发验证码（";
    private String textafter = "）";
    private String textbefore = "发送验证码";
    private final String TIME = "time";
    private final String CTIME = "ctime";
    private OnClickListener mOnclickListener;
    private Timer t;
    private TimerTask tt;
    private long time=-1;

    public TimeButton(Context context) {
        super(context);
        super.setOnClickListener(this);

    }

    public TimeButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        super.setOnClickListener(this);
    }

    @SuppressLint("HandlerLeak")
    Handler han = new Handler() {
        public void handleMessage(android.os.Message msg) {
            TimeButton.this.setText(textafters + time / 1000 + textafter);
            time -= 1000;
            if (time < 0) {
                setEnabled(true);
                TimeButton.this.setText(textbefore);
                TimeButton.this.setTextColor(getResources().getColor(R.color.white));
                clearTimer();
            }
        }

        ;
    };

    private void initTimer() {
        time = lenght;
        t = new Timer();
        tt = new TimerTask() {

            @Override
            public void run() {
                han.sendEmptyMessage(0x01);
            }
        };
    }

    private void clearTimer() {
        if (tt != null) {
            tt.cancel();
            tt = null;
        }
        if (t != null)
            t.cancel();
        t = null;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        this.mOnclickListener = l;
    }

    @Override
    public void onClick(View v) {
        if (mOnclickListener != null)
            mOnclickListener.onClick(v);
        // t.scheduleAtFixedRate(task, delay, period);
    }

    public void start(){
        initTimer();
        this.setText(textafters + time / 1000 + textafter);
        super.setEnabled(false);
        t.schedule(tt, 0, 1000);
    }
    /**
     * ��activity��onDestroy()����ͬ��
     */
    public void onDestroy() {
        clearTimer();
    }

    /**
     * ��activity��onCreate()����ͬ��
     */
    public void onCreate(Bundle bundle) {
        long time = System.currentTimeMillis();
        if (time > 0)
            return;
        else {
            initTimer();
            this.time = Math.abs(time);
            t.schedule(tt, 0, 1000);
            this.setText(textafters + time + textafter);
            this.setEnabled(false);
        }
    }

    public TimeButton setTextAfter(String text1) {
        this.textafter = text1;
        return this;
    }

    public TimeButton setTextAfters(String text1) {
        this.textafters = text1;
        return this;
    }

    public TimeButton setTextBefore(String text0) {
        this.textbefore = text0;
        this.setText(textbefore);
        return this;
    }

    public TimeButton setLenght(long lenght) {
        this.lenght = lenght;
        return this;
    }

    public long getTime() {
        return time;
    }

    @Override
    public void setEnabled(boolean enabled) {
        if (time <= 0) super.setEnabled(enabled);
    }
}