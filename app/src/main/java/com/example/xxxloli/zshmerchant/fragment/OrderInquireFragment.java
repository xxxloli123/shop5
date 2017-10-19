package com.example.xxxloli.zshmerchant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.example.xxxloli.zshmerchant.BaseFragment;
import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/9/13.
 */

public class OrderInquireFragment extends BaseFragment {
    View v;
    @BindView(R.id.inquireRL)
    RelativeLayout inquireRL;
    @BindView(R.id.line_up_expense)
    RadioButton lineUpExpense;
    @BindView(R.id.line_down_expense)
    RadioButton lineDownExpense;
    @BindView(R.id.radio)
    RadioGroup radio;
    @BindView(R.id.menus_frame)
    FrameLayout menusFrame;
    Unbinder unbinder;

    private RadioButton[] rButton;
    private Button selectButton;

    private final static String TAG_KEY = "TAG_KEY";
    private final static String UP = "UP";
    private final static String DOWN = "DOWN";

    private LineUpExpenseFragment lineUpExpenseFragment;
    private LineDownExpenseFragment lineDownExpenseFragment;

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            button((Button) v);
        }
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_order_inquire, null);
        unbinder = ButterKnife.bind(this, v);
        init(savedInstanceState);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * 初始化控件
     */
    private void init(Bundle savedInstanceState) {
        lineUpExpense.setOnClickListener(onClickListener);
        lineDownExpense.setOnClickListener(onClickListener);
        rButton = new RadioButton[]{lineUpExpense, lineDownExpense};

        int index = 0;
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(TAG_KEY, 0);
        } else {
            Bundle bundle = getActivity().getIntent().getExtras();
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
        if (selectButton == lineUpExpense) {
            if (lineUpExpenseFragment == null) {
                lineUpExpenseFragment = addFragment(LineUpExpenseFragment.class, UP, bundle);
            }
            changeFragment(lineUpExpenseFragment);
        } else if (selectButton == lineDownExpense) {
            if (lineDownExpenseFragment == null) {
                lineDownExpenseFragment = addFragment(LineDownExpenseFragment.class, DOWN, bundle);
            }
            changeFragment(lineDownExpenseFragment);
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
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment == null) {
            fragment = Fragment.instantiate(getActivity(), fragmentClass.getName(), bundle);
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
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (lineUpExpenseFragment == fragment) {
            if (lineDownExpenseFragment != null && !lineDownExpenseFragment.isHidden())
                transaction.hide(lineDownExpenseFragment);
        } else {
            if (lineUpExpenseFragment != null && !lineUpExpenseFragment.isHidden())
                transaction.hide(lineUpExpenseFragment);
        }
        if (fragment.isDetached())
            transaction.attach(fragment);
        else if (fragment.isHidden()) transaction.show(fragment);
        transaction.commit();
    }

    @OnClick({R.id.inquireRL, R.id.line_up_expense, R.id.line_down_expense})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.inquireRL:
                break;
            case R.id.line_up_expense:
//                showFragment(lineUpExpenseFragment);
                break;
            case R.id.line_down_expense:
//                showFragment(lineDownExpenseFragment);
                break;
        }
    }

}
