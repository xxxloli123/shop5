package com.example.xxxloli.zshmerchant.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.Activity.BuildactivityActivity;
import com.example.xxxloli.zshmerchant.Activity.BusinessAnalyzeActivity;
import com.example.xxxloli.zshmerchant.Activity.CommodityActivity;
import com.example.xxxloli.zshmerchant.Activity.DistributionActivity;
import com.example.xxxloli.zshmerchant.Activity.NewMessageActivity;
import com.example.xxxloli.zshmerchant.Activity.OrderingEvaluateActivity;
import com.example.xxxloli.zshmerchant.Activity.SettingActivity;
import com.example.xxxloli.zshmerchant.Activity.ShopInfoActivity;
import com.example.xxxloli.zshmerchant.BaseFragment;
import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ManageFragment extends BaseFragment {
    View v;

    Unbinder unbinder;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.i)
    ImageView i;
    @BindView(R.id.open_shop_text)
    TextView openShopText;
    @BindView(R.id.printer_text)
    TextView printerText;
    @BindView(R.id.today_money)
    TextView todayMoney;
    @BindView(R.id.yesterday_money)
    TextView yesterdayMoney;
    @BindView(R.id.today_order)
    TextView todayOrder;
    @BindView(R.id.yesterday_order)
    TextView yesterdayOrder;
    @BindView(R.id.message_number)
    TextView messageNumber;
    @BindView(R.id.commodityLL)
    LinearLayout commodityLL;
    @BindView(R.id.evaluateLL)
    LinearLayout evaluateLL;
    @BindView(R.id.billLL)
    LinearLayout billLL;
    @BindView(R.id.analyzeLL)
    LinearLayout analyzeLL;
    @BindView(R.id.shop_infoLL)
    LinearLayout shopInfoLL;
    @BindView(R.id.distributionLL)
    LinearLayout distributionLL;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.self_buildLL)
    LinearLayout selfBuildLL;
    @BindView(R.id.platformLL)
    LinearLayout platformLL;
    @BindView(R.id.settingLL)
    LinearLayout settingLL;
    @BindView(R.id.image_tx)
    CircleImageView imageTx;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_manage, null);
        unbinder = ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.image_tx, R.id.shop_name, R.id.open_shop_text, R.id.printer_text, R.id.commodityLL,
            R.id.evaluateLL, R.id.billLL, R.id.analyzeLL, R.id.shop_infoLL, R.id.distributionLL, R.id.self_buildLL,
            R.id.platformLL, R.id.settingLL, R.id.message_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_tx:
                break;
            case R.id.shop_name:
                break;
            case R.id.open_shop_text:
                break;
            case R.id.printer_text:
                break;
            case R.id.commodityLL:
                startActivity(new Intent(getActivity(), CommodityActivity.class));
                break;
            case R.id.evaluateLL:
                startActivity(new Intent(getActivity(), OrderingEvaluateActivity.class));
                break;
            case R.id.billLL:
                break;
            case R.id.analyzeLL:
                startActivity(new Intent(getActivity(), BusinessAnalyzeActivity.class));
                break;
            case R.id.shop_infoLL:
                startActivity(new Intent(getActivity(), ShopInfoActivity.class));
                break;
            case R.id.distributionLL:
                startActivity(new Intent(getActivity(), DistributionActivity.class));
                break;
            case R.id.self_buildLL:
                startActivity(new Intent(getActivity(), BuildactivityActivity.class));
                break;
            case R.id.platformLL:
                break;
            case R.id.settingLL:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.message_number:
                startActivity(new Intent(getActivity(), NewMessageActivity.class));
                break;
        }
    }
}
