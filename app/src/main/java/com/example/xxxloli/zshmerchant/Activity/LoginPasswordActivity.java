package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.BaseActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Info;
import com.example.xxxloli.zshmerchant.util.CacheActivity;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.interfaceconfig.Config;
import com.slowlife.lib.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/1/31 0031.
 * <p>
 * TODO   设置登录密码
 */

public class LoginPasswordActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout go_back, delete;
    private EditText password, pwd;
    private Button ensure;
    private Info info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CacheActivity.activityList.contains(this)) {
            CacheActivity.addActivity(this);
        }
        setContentView(R.layout.activity_login_password);
        initView();
    }

    /**
     * 初始化控件
     */
    private void initView() {
        go_back = (RelativeLayout) findViewById(R.id.back_rl);
        go_back.setOnClickListener(this);
        ensure = (Button) findViewById(R.id.ensure_bt);
        ensure.setOnClickListener(this);
        delete = (RelativeLayout) findViewById(R.id.delete);
        delete.setOnClickListener(this);
        password = (EditText) findViewById(R.id.password);
        pwd = findView(R.id.pwd);
        password.addTextChangedListener(new TextWatcher() {
            //text  输入框中改变后的字符串信息
            //start 输入框中改变后的字符串的起始位置
            //before 输入框中改变前的字符串的位置 默认为0
            //count 输入框中改变后的一共输入字符串的数量
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable edit) {
                //edit  输入结束呈现在输入框中的信息
                if (password.length() >= 1) {
                    delete.setVisibility(View.VISIBLE);
                } else {
                    delete.setVisibility(View.GONE);
                }
                if (edit.toString().trim().matches("^[0-9a-zA-Z_\\*\\.\\?\\-\\+]{6,20}$")) {
                    ensure.setBackgroundResource(R.drawable.login_corners_bgall);
                } else {
                    ensure.setBackgroundResource(R.drawable.login1_corners_bgall);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.back_rl://返回
                finish();
                break;
            case R.id.ensure_bt:
                delgeteDialog();
                break;
            case R.id.delete:
                password.setText("");
                break;
        }
    }

    /**
     * dialog窗口
     */
    private void delgeteDialog() {

        if (info == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            return;
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        LinearLayout mDialog = (LinearLayout) inflater.inflate(R.layout.dialog_revise_phone, null);
        final Dialog dialog = new AlertDialog.Builder(this).create();
        TextView textView = (TextView) mDialog.findViewById(R.id.title);
        textView.setText("确认修改密码");
        Button mYes = (Button) mDialog.findViewById(R.id.sure_bt);
        mYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify();
                dialog.dismiss();
            }
        });
        Button mNo = (Button) mDialog.findViewById(R.id.cancel_bt);
        mNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setContentView(mDialog);
    }

    private void modify() {
        String pass = pwd.getText().toString().trim();
        String pass1 = password.getText().toString().trim();
        if (!pass1.matches("^[0-9a-zA-Z_\\*\\.\\?\\-\\+]{6,20}$")) {
            Toast.makeText(this, "密码格式不正确,请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }
        //userStr：包含id,phone,password ;oldPassword原密码
        try {
            JSONObject userJson = new JSONObject();
            userJson.put("id", info.getId());
            userJson.put("phone", info.getPhone());
            userJson.put("password", MD5.md5Pwd(pass1));
            userJson.put("oldPassword", MD5.md5Pwd(pass));
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("userStr", userJson.toString())
                    .addFormDataPart("oldPassword", MD5.md5Pwd(pass))
                    .build();
            Request request = new Request
                    .Builder().post(requestBody)
                    .url(Config.Url.getUrl(Config.SHOP_TYPE))
                    .build();
            new OkHttpClient().newCall(request).enqueue(new SimpleCallback(this) {
                @Override
                public void onSuccess(String tag, JSONObject json) throws JSONException {
                    Toast.makeText(LoginPasswordActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败,请重试", Toast.LENGTH_SHORT).show();
        }
    }
}
