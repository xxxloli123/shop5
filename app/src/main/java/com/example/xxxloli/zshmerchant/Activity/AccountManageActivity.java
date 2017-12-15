package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.AccountListAdapter;
import com.example.xxxloli.zshmerchant.fragment.FragLoginPwd;
import com.example.xxxloli.zshmerchant.fragment.OrderHandleFragment;
import com.example.xxxloli.zshmerchant.greendao.Account;
import com.example.xxxloli.zshmerchant.greendao.DBManagerAccount;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.DBManagerUser;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.greendao.User;
import com.example.xxxloli.zshmerchant.util.ToastUtil;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.slowlife.lib.MD5;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountManageActivity extends BaseActivity {
    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.account_list)
    MyListView accountList;
    @BindView(R.id.add_registerLL)
    LinearLayout addRegisterLL;
    @BindView(R.id.log_out)
    Button logOut;
    @BindView(R.id.txttitle)
    TextView txttitle;
    @BindView(R.id.pgbar)
    ProgressBar pgbar;

    private DBManagerAccount dbManagerAccount;
    private List<Account> accounts;
    private DBManagerShop dbManagerShop;
    private DBManagerUser dbManagerUser;
    private AccountListAdapter accountListAdapter;
    private String token;
    private Account account, switchoverEd;
    private static final String TAG = FragLoginPwd.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        dbManagerAccount = DBManagerAccount.getInstance(this);
        dbManagerShop = DBManagerShop.getInstance(this);
        dbManagerUser = DBManagerUser.getInstance(this);
        accounts = dbManagerAccount.queryAccountList();

/**
 *         //开启信鸽日志输出
 */
        XGPushConfig.enableDebug(this, true);

        //信鸽注册代码

        XGPushManager.registerPush(this, new XGIOperateCallback() {

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
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_manage;
    }

    private void initView() {
        if (accounts == null) return;
        accountListAdapter = new AccountListAdapter(this, accounts);
        accountList.setAdapter(accountListAdapter);
        accountList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (accounts.get(position).getWritId() != 2333) {
                    if (accounts.get(position).getPwd() == null) {
                        switchover();
                        return;
                    }
                    switchoverEd = accounts.get(position);
                    account = dbManagerAccount.queryById((long) 2333).get(0);
                    Map<String, Object> map = new HashMap<>();
                    JSONObject user = new JSONObject();
                    try {
//            userStr：包含phone,password,type,phoneType(Android-安卓 Ios-IOS),token(推送唯一标示)
                        user.put("phone", accounts.get(position).getPhone());
                        user.put("password", MD5.md5Pwd(accounts.get(position).getPwd()));
                        user.put("type", "Shopkeeper");
                        user.put("phoneType", "Android");
                        user.put("token", token);
                        map.put("userStr", user);
                        newCall(Config.Url.getUrl(Config.LOGIN), map);
                    } catch (JSONException e) {
                        Toast.makeText(AccountManageActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void switchover() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_revise_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        final Button sure = view.findViewById(R.id.sure_bt);
        TextView title = view.findViewById(R.id.title);
        title.setText("由于此账号是短信登录方式,要切换此账号必须重新登录\n确认切换吗?");
        Button cancel = view.findViewById(R.id.cancel_bt);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountManageActivity.this, LoginActivity.class));
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @OnClick({R.id.back_rl, R.id.add_registerLL, R.id.log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.add_registerLL:
                Intent intent = new Intent();
                intent.putExtra("add",true);
                intent.setClass(this,FirstActivity.class);
                startActivity( intent);
                break;
            case R.id.log_out:
                isLogOut();
//                MyAsyncTask myTask = new MyAsyncTask(txttitle,pgbar,this);
//                myTask.execute(1000);
                break;
        }
    }

    private void isLogOut() {
        View view1 = LayoutInflater.from(this).inflate(R.layout.dialog_sure, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView title = view1.findViewById(R.id.title);
        Button sure = view1.findViewById(R.id.sure_bt);
        Button cancel = view1.findViewById(R.id.cancel_bt);
        title.setText("确认退出当前账号吗");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderHandleFragment.stopTimer();
                dbManagerAccount.deleteById((long) 2333);
                dbManagerShop.deleteById((long) 2333);
                dbManagerUser.deleteById((long) 2333);
                Intent intent = new Intent(AccountManageActivity.this, FirstActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view1);
        alertDialog.show();
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(getContext(), json.getString("message"), Toast.LENGTH_SHORT).show();
        if (json.getInt("statusCode") == 200) {
            OrderHandleFragment.stopTimer();
            Log.e("LOGIN","丢了个雷姆"+json);
            account.setWritId(null);
            dbManagerAccount.insertAccount(account);
            dbManagerAccount.deleteById((long) 2333);
            dbManagerShop.deleteById((long) 2333);
            dbManagerUser.deleteById((long) 2333);
            dbManagerAccount.deleteById(switchoverEd.getWritId());
            User user = new Gson().fromJson(json.getString("user"), User.class);
            user.setWritId((long) 2333);
            Shop shop = new Gson().fromJson(json.getString("shop"), Shop.class);
            shop.setWritId((long) 2333);
            dbManagerUser.insertUser(user);
            dbManagerShop.insertShop(shop);
            switchoverEd.setWritId((long) 2333);
            dbManagerAccount.insertAccount(switchoverEd);
//            //OpenIM 开始登录
//            String userid = shop.getShopkeeperId();
//            LoginSampleHelper loginHelper=LoginSampleHelper.getInstance();
//            init2(userid,"24663803");
//            loginHelper.login_Sample(userid, userid, "24663803", new IWxCallback() {
//
//                @Override
//                public void onSuccess(Object... arg0) {
//                    ToastUtil.showToast(AccountManageActivity.this,"login success");
//                    YWLog.i(TAG, "login success!");
//
////						YWIMKit mKit = LoginSampleHelper.getInstance().getIMKit();
////						EServiceContact contact = new EServiceContact("qn店铺测试账号001:找鱼");
////						LoginActivity.this.startActivity(mKit.getChattingActivityIntent(contact));
////                        mockConversationForDemo();
//                }
//
//                @Override
//                public void onProgress(int arg0) {
//
//                }
//
//                @Override
//                public void onError(int errorCode, String errorMessage) {
//                    if (errorCode == YWLoginCode.LOGON_FAIL_INVALIDUSER) { //若用户不存在，则提示使用游客方式登陆
//                        ToastUtil.showToast(AccountManageActivity.this,"则提示使用游客方式登陆");
//                    } else {
//                        YWLog.w(TAG, "登录失败，错误码：" + errorCode + "  错误信息：" + errorMessage);
//                        ToastUtil.showToast(AccountManageActivity.this,errorMessage);
//                    }
//                }
//            });
//            finish();
        }
    }

//    private void init2(String userId, String appKey){
//        //初始化imkit
//        LoginSampleHelper.getInstance().initIMKit(userId, appKey);
//        //通知栏相关的初始化
//        NotificationInitSampleHelper.init();
//    }
}
