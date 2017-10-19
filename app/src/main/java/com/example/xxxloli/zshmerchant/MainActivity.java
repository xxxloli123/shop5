package com.example.xxxloli.zshmerchant;

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

import com.example.xxxloli.zshmerchant.fragment.ManageFragment;
import com.example.xxxloli.zshmerchant.fragment.OrderHandleFragment;
import com.example.xxxloli.zshmerchant.fragment.OrderInquireFragment;
import com.example.xxxloli.zshmerchant.fragment.ShopFragment;
import com.example.xxxloli.zshmerchant.util.CacheActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.menus_frame)
    FrameLayout menusFrame;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.order_handle)
    RadioButton orderHandle;
    @BindView(R.id.order_inquire)
    RadioButton orderInquire;
    @BindView(R.id.manage)
    RadioButton manage;
    @BindView(R.id.shop)
    RadioButton shop;
    @BindView(R.id.group)
    RadioGroup group;


    private Button selectButton;
    private RadioButton[] rButton;

    private final static String TAG_KEY = "TAG_KEY";
    private final static String HANDLE = "HANDLE";
    private final static String INQUIRE = "INQUIRE";
    private final static String MANAGE = "MANAGE";
    private final static String SHOP = "SHOP";

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            button((Button) v);
        }
    };

    private OrderHandleFragment orderHandleFragment;
    private OrderInquireFragment orderInquireFragment;
    private ManageFragment manageFragment;
    private ShopFragment shopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!CacheActivity.activityList.contains(this)) {
            CacheActivity.addActivity(this);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init(savedInstanceState);
    }



    /*
     * onSaveInstanceState方法会在什么时候被执行，有这么几种情况：
    1、当用户按下HOME键时。
    这是显而易见的，系统不知道你按下HOME后要运行多少其他的程序，自然也不知道activity A是否会被销毁，故系统会调用onSaveInstanceState，让用户有机会保存某些非永久性的数据。以下几种情况的分析都遵循该原则
    2、长按HOME键，选择运行其他的程序时。
    3、按下电源按键（关闭屏幕显示）时。
    4、从activity A中启动一个新的activity时。
    5、屏幕方向切换时，例如从竖屏切换到横屏时。
    onSaveInstanceState的调用遵循一个重要原则，即当系统“未经你许可”时销毁了你的activity，则onSaveInstanceState会被系统调用，这是系统的责任，因为它必须要提供一个机会让你保存你的数据
     */
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

    /**
     * 初始化控件
     */
    private void init(Bundle savedInstanceState) {
        orderHandle.setOnClickListener(onClickListener);
        orderInquire.setOnClickListener(onClickListener);
        manage.setOnClickListener(onClickListener);
        shop.setOnClickListener(onClickListener);
        rButton = new RadioButton[]{orderHandle, orderInquire,manage,shop};

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
        if (selectButton == orderHandle) {
            if (orderHandleFragment == null) {
                orderHandleFragment = addFragment(OrderHandleFragment.class, HANDLE, bundle);
            }
            changeFragment(orderHandleFragment);
        }

        else if (selectButton == orderInquire) {
            if (orderInquireFragment == null) {
                orderInquireFragment = addFragment(OrderInquireFragment.class, INQUIRE, bundle);
            }
            changeFragment(orderInquireFragment);
        }

        else if (selectButton == manage) {
            if (manageFragment == null) {
                manageFragment = addFragment(ManageFragment.class, MANAGE, bundle);
            }
            changeFragment(manageFragment);
        }

        else if (selectButton == shop) {
            if (shopFragment == null) {
                shopFragment = addFragment(ShopFragment.class, SHOP, bundle);
            }
            changeFragment(shopFragment);
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

        Fragment homeFragment = manager.findFragmentByTag(HANDLE);
        if (homeFragment != null && homeFragment != fragment) {
            transaction.hide(homeFragment);
            homeFragment.setUserVisibleHint(false);
        }

        Fragment findFragment = manager.findFragmentByTag(INQUIRE);
        if (findFragment != null && findFragment != fragment) {
            transaction.detach(findFragment);
            findFragment.setUserVisibleHint(false);
        }

        Fragment orderFragment = manager.findFragmentByTag(MANAGE);
        if (orderFragment != null && orderFragment != fragment) {
            transaction.detach(orderFragment);
            orderFragment.setUserVisibleHint(false);
        }

        Fragment myFragment = manager.findFragmentByTag(SHOP);
        if (myFragment != null && myFragment != fragment) {
            transaction.detach(myFragment);
            myFragment.setUserVisibleHint(false);
        }

        if (fragment != null) {
            if (fragment != homeFragment && fragment.isDetached()) {
                transaction.attach(fragment);
            } else if (fragment == homeFragment && fragment.isHidden()) {
                transaction.show(fragment);
            }
            fragment.setUserVisibleHint(true);
        }
        transaction.commit();
    }



    @OnClick({R.id.order_handle, R.id.order_inquire, R.id.manage, R.id.shop})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.order_handle:
                break;
            case R.id.order_inquire:
                break;
            case R.id.manage:
                break;
            case R.id.shop:
                break;
        }
    }
}
