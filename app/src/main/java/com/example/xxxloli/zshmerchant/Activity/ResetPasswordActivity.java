package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.util.CacheActivity;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.example.xxxloli.zshmerchant.view.TimeButton;
import com.interfaceconfig.Config;
import com.slowlife.lib.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/1/21 0021.
 * <p>
 * <p>
 * TODO 找回密码
 */

public class ResetPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private Button nextStep;
    private RelativeLayout back;
    @BindView(R.id.verification_code)
    TimeButton verificationCode;
    @BindView(R.id.phone_edit)
    EditText phone;
    @BindView(R.id.reset_pwd)
    EditText password;
    @BindView(R.id.verification_code_edit)
    EditText codeEdit;
    JSONObject smsJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CacheActivity.activityList.contains(this)) {
            CacheActivity.addActivity(this);
        }
        setContentView(R.layout.activity_reset_password);
        ButterKnife.bind(this);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        nextStep = (Button) findViewById(R.id.next_step_bt);
        nextStep.setOnClickListener(this);
        back = (RelativeLayout) findViewById(R.id.back_rl);
        back.setOnClickListener(this);
        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) verificationCode.setEnabled(true);
                else verificationCode.setEnabled(false);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next_step_bt:
                setPwd();
                break;
            case R.id.back_rl:
                finish();
                break;
            case R.id.verification_code:
                getCode();
                break;
        }
    }

    private void getCode() {
        verificationCode.setTextAfters("").setTextAfter("秒后重发").setTextBefore("获取验证码").setLenght(60 * 1000);
        verificationCode.start();
        String phone = this.phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, this.phone.getHint(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone.matches("^1[3|5|7|8]\\d{9}$")) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = Config.Url.getUrl(Config.SMS_CODE);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("phone", phone)
                .addFormDataPart("codeType", "1")
                .build();

        final Request request = new Request.Builder()
                .url(url)
                .tag(Config.SMS_CODE)
                .post(requestBody)
                .build();
        OkHttpClient okhttp = new OkHttpClient();
        okhttp.newCall(request).enqueue(new SimpleCallback() {
            @Override
            public void onSuccess(String tag, JSONObject json) throws JSONException {
                switch (tag) {
                    case Config.SMS_CODE:
                        smsJson = new JSONObject();
                        smsJson.put("id", json.getJSONObject("sms").getString("id"));
//                        smsJson.put("phone", json.getJSONObject("sms").getString("phone"));
                        Toast.makeText(ResetPasswordActivity.this, "获取成功", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }

    private void setPwd() {
        if (smsJson == null) {
            Toast.makeText(this, "请先获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        String phone = this.phone.getText().toString().trim();
        String code = codeEdit.getText().toString().trim();
        String pwd = password.getText().toString().trim();
        if (isEmpty(phone)) {
            Toast.makeText(this, this.phone.getHint(), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!phone.matches("^1[3|5|7|8]\\d{9}$")) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(code)) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pwd) || !pwd.matches("^[0-9a-zA-Z_\\*\\.\\?\\-\\+]{6,20}$")) {
            Toast.makeText(ResetPasswordActivity.this, "密码格式不正确,请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            //userStr：包含phone,type,password(新密码)   smsStr:id(必填)、code(必填)、phone(必填)

            smsJson.put("code", code);
            smsJson.put("phone", phone);
            JSONObject userJson = new JSONObject();
            userJson.put("phone", phone);
            userJson.put("type", "Shopkeeper");
            userJson.put("password", MD5.md5Pwd(pwd));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("userStr", userJson.toString())
                    .addFormDataPart("smsStr", smsJson.toString())
                    .build();

            final Request request = new Request.Builder()
                    .url(Config.Url.getUrl(Config.SEARPWD))
                    .tag(Config.SEARPWD)
                    .post(requestBody)
                    .build();
            OkHttpClient okhttp = new OkHttpClient();
            okhttp.newCall(request).enqueue(new SimpleCallback() {
                @Override
                public void onSuccess(String tag, JSONObject json) throws JSONException {
                    Toast.makeText(ResetPasswordActivity.this, json.toString(), Toast.LENGTH_SHORT).show();
                    if (json.getInt("statusCode")==200) finish();
                }
            });
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }
}
