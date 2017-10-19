package com.example.xxxloli.zshmerchant.Activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.util.Common;
import com.example.xxxloli.zshmerchant.view.TimeButton;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.slowlife.lib.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/2/21 0021.
 */

public class ChangePhoneActivity extends BaseActivity {
    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.send_code_text)
    TimeButton sendCodeText;
    @BindView(R.id.verification_code)
    EditText verificationCode;
    @BindView(R.id.ensure_bt)
    Button ensureBt;
    @BindView(R.id.verification_pwd)
    EditText pwd;
    @BindView(R.id.phone1)
    EditText phone1;
    private String p;
    private String smsId;

    @Override
    protected void init() {
    }

    @OnClick({R.id.back_rl, R.id.send_code_text, R.id.ensure_bt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.send_code_text:
                if (!Common.matchePhone(phone.getText().toString().trim())) {
                    Toast.makeText(this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendCodeText.setTextAfters("重发验证码（").setTextAfter("）").setTextBefore("发送验证码").setLenght(60 * 1000);
                sendCodeText.setEnabled(false);
                sendCodeText.start();
                Map<String, Object> params = Common.getCode(phone.getText().toString().trim(), "2");
                newCall(Config.Url.getUrl(Config.SMS_CODE), params);
                break;
            case R.id.ensure_bt:
                submit();
                break;
        }
    }

    private void submit() {
        String phone1 = ((TextView) findViewById(R.id.phone1)).getText().toString().trim();
        if (isEmpty(phone1)) {
            Toast.makeText(this, "请填写旧手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(smsId)) {
            Toast.makeText(this, "请先获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Common.matchePhone(p)) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(verificationCode.getText().toString().trim())) {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pwd.getText().toString().trim())) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
        }
        try {
//            password smsStr:id,code,phone
            Map<String, Object> params = new HashMap<>();
            JSONObject userJson = new JSONObject();
//            userJson.put("id", info.getId());
            userJson.put("phone", p);
            userJson.put("password", MD5.md5Pwd(pwd.getText().toString().trim()));
            JSONObject smsJson = new JSONObject();
            smsJson.put("id", smsId);
            smsJson.put("code", verificationCode.getText().toString().trim());
            smsJson.put("phone", p);
            params.put("userStr", userJson);
            params.put("smsStr", smsJson);
            params.put("oldphone", phone1);
//            info.setPhone(p);
//            newCall(Config.Url.getUrl(Config.SHOP_TYPE), params);
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void initListener() {
        phone.addTextChangedListener(textWatcher);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    /**
     * 电话号码监听事件editText监听事件
     */
    private TextWatcher textWatcher = new TextWatcher() {
        //text  输入框中改变后的字符串信息
        //start 输入框中改变后的字符串的起始位置
        //before 输入框中改变前的字符串的位置 默认为0
        //count 输入框中改变后的一共输入字符串的数量
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(final Editable s) {
            //s输入结束呈现在输入框中的信息
            if (Common.matchePhone(s.toString())) {
                sendCodeText.setEnabled(true);
            } else {
                sendCodeText.setEnabled(false);
            }
        }
    };

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.SMS_CODE:
                Toast.makeText(this, "验证码已发送", Toast.LENGTH_SHORT).show();
                JSONObject sms = json.getJSONObject("sms");
                smsId = sms.getString("id");
                p = sms.getString("phone");
                break;
//            case Config.SHOP_TYPE:
//                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
////                ((MyApplication) getApplication()).setInfo(info);
//                setResult(Activity.RESULT_OK);
//                finish();
//                break;
        }
    }
}
