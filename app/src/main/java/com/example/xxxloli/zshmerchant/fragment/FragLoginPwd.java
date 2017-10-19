package com.example.xxxloli.zshmerchant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.ResetPasswordActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManager;
import com.example.xxxloli.zshmerchant.greendao.User;
import com.example.xxxloli.zshmerchant.util.Common;
import com.example.xxxloli.zshmerchant.view.ShSwitchView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;
import com.slowlife.lib.MD5;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by sgrape on 2017/5/24.
 * e-mail: sgrape1153@gmail.com
 */

public class FragLoginPwd extends BaseFragment {
    @BindView(R.id.phone_edit)
    EditText phoneEdit;
    @BindView(R.id.verification_code_edit)
    EditText verificationCodeEdit;
    @BindView(R.id.login_bt)
    Button loginBt;
    @BindView(R.id.login_resetPwd)
    TextView loginResetPwd;
    @BindView(R.id.switch_view)
    ShSwitchView switchView;
    Unbinder unbinder;
    @BindView(R.id.show)
    TextView show;


    private String token;
    private DBManager dbManager;


    public FragLoginPwd() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.frag_login_pwd;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        switchView.setOn(false);
        switchView.setOnSwitchStateChangeListener(new ShSwitchView.OnSwitchStateChangeListener() {
            @Override
            public void onSwitchStateChange(boolean isOn) {
                if (isOn) {
                    //选中状态 显示明文--设置为可见的密码
                    verificationCodeEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    //默认状态显示密码--设置文本 要一起写才能起作用 InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD
                    verificationCodeEdit.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });
//        dbManager = DBManager.getInstance(getActivity());
//        User user;
//        if (dbManager.queryById((long) 2333).size()!=0) {
//            User user1=dbManager.queryById((long) 2333).get(0);
//            user=user1;
//            user.setWritId((long) 2333);
//            dbManager.insertUser(user);
//            dbManager.insertUser(user1);
//            show.setText(user1.getName()+user1.getType_value()+user1.getWritId());
//        }
/**
 *         //开启信鸽日志输出

 */

        XGPushConfig.enableDebug(getActivity(), true);

        //信鸽注册代码

        XGPushManager.registerPush(getActivity(), new XGIOperateCallback() {

            @Override
            public void onSuccess(Object data, int flag) {
                token = data.toString();
                Log.d("TPush", "注册成功，设备token为：" + data);

            }

            @Override
            public void onFail(Object data, int errCode, String msg) {

                Log.d("TPush", "注册失败，错误码：" + errCode + ",错误信息：" + msg);

            }
        });
    }

    @OnClick({R.id.login_bt, R.id.login_resetPwd})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_resetPwd:
                startActivity(new Intent(getContext(), ResetPasswordActivity.class));
                break;
            case R.id.login_bt:
                login();
                break;
        }
    }

    private void login() {
        String phone = phoneEdit.getText().toString().trim();
        String pwd = verificationCodeEdit.getText().toString().trim();
        if (!Common.matchePhone(phone)) {
            Toast.makeText(getContext(), "手机号格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(pwd)) {
            Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, Object> map = new HashMap<>();
        JSONObject user = new JSONObject();
        try {
//            userStr：包含phone,password,type,phoneType(Android-安卓 Ios-IOS),token(推送唯一标示)
            user.put("phone", phone);
            user.put("password", MD5.md5Pwd(pwd));
            user.put("type", "Shopkeeper");
            user.put("phoneType", "Android");
            user.put("token", token);
            map.put("userStr", user);
            newCall(Config.Url.getUrl(Config.LOGIN), map);
        } catch (JSONException e) {
            Toast.makeText(getContext(), "解析数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.LOGIN:
                Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
                if (json.getInt("statusCode") == 200) {
                    Log.e("信息", "丢了个雷姆" + json.toString());
                    show.setText(json.toString());
                    dbManager = DBManager.getInstance(getActivity());
                    User user = new Gson().fromJson(json.getString("user"), User.class);
                    user.setWritId((long) 2333);
//                    dbManager.insertUser(user);
//                    startActivity(new Intent(getContext(), MainActivity.class));
//                    if (getActivity() != null) getActivity().finish();
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
