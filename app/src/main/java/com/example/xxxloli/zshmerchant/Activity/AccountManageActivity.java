package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.AccountListAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Info;
import com.example.xxxloli.zshmerchant.view.MyListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AccountManageActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.account_list)
    MyListView accountList;
    @BindView(R.id.add_registerLL)
    LinearLayout addRegisterLL;
    @BindView(R.id.log_out)
    Button logOut;

    private List<Info> infos;
    private AccountListAdapter accountListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_manage);
        ButterKnife.bind(this);
        intoView();
    }

    private void intoView() {
        if (infos==null)return;
        accountListAdapter=new AccountListAdapter(this,infos);
        accountList.setAdapter(accountListAdapter);
    }

    @OnClick({R.id.back_rl, R.id.add_registerLL, R.id.log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                break;
            case R.id.add_registerLL:
                addAcount();
                break;
            case R.id.log_out:
                break;
        }
    }

    private void addAcount() {
        for (int i=0;i<3;i++){
            Info info=new Info();
            info.setName("永不迟到的香蕉君");
            infos.add(info);
        }
        accountListAdapter=new AccountListAdapter(this,infos);
        accountList.setAdapter(accountListAdapter);
        Toast.makeText(this,"丢了个雷姆",Toast.LENGTH_SHORT).show();
    }
}
