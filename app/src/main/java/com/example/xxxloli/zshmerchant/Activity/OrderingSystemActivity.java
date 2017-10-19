package com.example.xxxloli.zshmerchant.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.fragment.AddExpenseCodeFragment;
import com.example.xxxloli.zshmerchant.fragment.AddTableNumberFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderingSystemActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.menus_frame)
    FrameLayout menusFrame;
    @BindView(R.id.add_table_number)
    RadioButton addTableNumber;
    @BindView(R.id.add_expense_code)
    RadioButton addExpenseCode;
    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.sure)
    Button sure;

    private RadioButton[] rButton;
    private Button selectButton;

    private final static String TAG_KEY = "TAG_KEY";
    private final static String NUMBER = "NUMBER";
    private final static String CODE = "CODE";

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            button((Button) v);
        }
    };

    private AddTableNumberFragment addTableNumberFragment;
    private AddExpenseCodeFragment addExpenseCodeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordering_system);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }

    /**
     * 初始化控件
     */
    private void init(Bundle savedInstanceState) {
        addTableNumber.setOnClickListener(onClickListener);
        addExpenseCode.setOnClickListener(onClickListener);
        rButton = new RadioButton[]{addTableNumber, addExpenseCode};

        int index = 0;
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(TAG_KEY, 0);
        } else {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                index = bundle.getInt(TAG_KEY, 0);
            }
        }

        if (index < 0 || index > rButton.length - 1) {
            index = 0;
        }
        button(rButton[index]);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG_KEY, getIndex());
    }

    private int getIndex() {
        int index = -1;
        for (int i = 0; i < rButton.length; i++) {
            if (selectButton == rButton[i]) {
                index = i;
                break;
            }
        }
        return index;
    }

    @OnClick({R.id.back_rl, R.id.add_table_number, R.id.add_expense_code, R.id.sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.add_table_number:
                break;
            case R.id.add_expense_code:
                break;
            case R.id.sure:
                break;
        }
    }

    /**
     * 点击时需要切换相应的页面
     *
     * @param b 当前需要传入的按钮
     */
    private void button(Button b) {
        if (selectButton != null && selectButton == b) {
            return;
        }
        selectButton = b;
        Bundle bundle = new Bundle();
        if (selectButton == addTableNumber) {
            if (addTableNumberFragment == null) {
                addTableNumberFragment = addFragment(AddTableNumberFragment.class, NUMBER, bundle);
            }
            changeFragment(addTableNumberFragment);
        } else if (selectButton == addExpenseCode) {
            if (addExpenseCodeFragment == null) {
                addExpenseCodeFragment = addFragment(AddExpenseCodeFragment.class, CODE, bundle);
            }
            changeFragment(addExpenseCodeFragment);
        }
    }

    /**
     * 添加管理fragment 并返回
     *
     * @param fragmentClass 传入的fragment类
     * @param tag           fragment标识
     * @param bundle
     * @return
     */
    private <T> T addFragment(Class<? extends Fragment> fragmentClass, String tag, Bundle bundle) {
        //Fragment 管理者
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentClass.getName(), bundle);
            if (bundle != null) {
                bundle.putString("fragment", fragmentClass.getName());
                fragment.setArguments(bundle);
            }

            transaction.add(R.id.menus_frame, fragment, tag);
            transaction.commit();
        }
        return (T) fragment;
    }

    /**
     * 切换fragment
     *
     * @param fragment 传入当前切换的fragment
     */
    private void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (addTableNumberFragment == fragment) {
            if (addExpenseCodeFragment != null && !addExpenseCodeFragment.isHidden())
                transaction.hide(addExpenseCodeFragment);
        } else {
            if (addTableNumberFragment != null && !addTableNumberFragment.isHidden())
                transaction.hide(addTableNumberFragment);
        }
        if (fragment.isDetached())
            transaction.attach(fragment);
        else if (fragment.isHidden()) transaction.show(fragment);
        transaction.commit();
    }

}
