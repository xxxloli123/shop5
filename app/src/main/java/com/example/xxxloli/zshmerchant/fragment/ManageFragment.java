package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.BuildactivityActivity;
import com.example.xxxloli.zshmerchant.Activity.BusinessAnalyzeActivity;
import com.example.xxxloli.zshmerchant.Activity.CommodityActivity;
import com.example.xxxloli.zshmerchant.Activity.DistributionActivity;
import com.example.xxxloli.zshmerchant.Activity.MyBillActivity;
import com.example.xxxloli.zshmerchant.Activity.NewMessageActivity;
import com.example.xxxloli.zshmerchant.Activity.OrderingEvaluateActivity;
import com.example.xxxloli.zshmerchant.Activity.PersonnelManageActivity;
import com.example.xxxloli.zshmerchant.Activity.PlatformActivitsActivity;
import com.example.xxxloli.zshmerchant.Activity.SearchBluetoothActivity;
import com.example.xxxloli.zshmerchant.Activity.SettingActivity;
import com.example.xxxloli.zshmerchant.Activity.ShopInfoActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.base.AppInfo;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/9/13.
 */

public class ManageFragment extends BaseFragment {

    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.open_shop_text)
    TextView openShopText;
    @BindView(R.id.printer_text)
    TextView printerText;
    @BindView(R.id.today_money)
    TextView todayMoney;
    @BindView(R.id.today_order)
    TextView todayOrder;
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
    @BindView(R.id.printer_img)
    ImageView printerImg;
    Unbinder unbinder;
    @BindView(R.id.shop_img)
    ImageView shopImg;
    @BindView(R.id.name_RL)
    RelativeLayout nameRL;
    @BindView(R.id.linear_personnelManage)
    LinearLayout linearPersonnelManage;
    Unbinder unbinder1;

    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void init() {
        dbManagerShop = DBManagerShop.getInstance(getContext());
        shop = dbManagerShop.queryById((long) 2333).get(0);
        initView();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);       //获取年月日时分秒
        int month = cal.get(Calendar.MONTH);   //获取到的月份是从0开始计数
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
        params.put("startDate", year + "-" + (++month) + "-" + day);
        params.put("endDate", year + "-" + month + "-" + day);
        newCall(Config.Url.getUrl(Config.GET_Data), params);
    }

    private void initView() {
        Picasso.with(getActivity()).load(Config.Url.getUrl(Config.IMG_Hear) + shop.getShopImage())
                .into(imageTx);
        imageTx = rootView.findViewById(R.id.image_tx);
        shopName = rootView.findViewById(R.id.shop_name);
        imageTx = rootView.findViewById(R.id.image_tx);
        todayOrder = rootView.findViewById(R.id.today_order);
        shopName.setText(shop.getShopName());
        if (!TextUtils.isEmpty(AppInfo.btAddress)) {
            printerText.setText("已连接");
            printerText.setTextColor(getActivity().getResources().getColor(R.color.blue));
            printerImg.setImageResource(R.drawable.printer4);
        }
        if (shop.getBusiness() == null || shop.getBusiness().equals("no")) {
            openShopText.setText("已关店");
            openShopText.setTextColor(getActivity().getResources().getColor(R.color.gray_2));
            shopImg.setImageResource(R.drawable.shop);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_manage;
    }

    @OnClick({R.id.image_tx, R.id.shop_name, R.id.open_shop_text, R.id.printer_text, R.id.commodityLL,
            R.id.evaluateLL, R.id.billLL, R.id.analyzeLL, R.id.shop_infoLL, R.id.distributionLL, R.id.self_buildLL,
            R.id.platformLL, R.id.linear_personnelManage, R.id.settingLL, R.id.message_number})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_tx:
                break;
            case R.id.shop_name:
                startActivity(new Intent(getActivity(), ShopInfoActivity.class));
                break;
            case R.id.open_shop_text:
                if (shop.getBusiness() != null && shop.getBusiness().equals("yes")) isCloseShop();
                else {
                    Map<String, Object> params = new HashMap<>();
                    JSONObject shopStr = new JSONObject();
                    try {
//                    business 是否营业中 yes no
                        shopStr.put("id", shop.getId());
                        shopStr.put("business", "yes");

                        params.put("shopStr", shopStr);
                        params.put("userId", shop.getShopkeeperId());
                        newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.printer_text:
                    startActivity(new Intent(getActivity(), SearchBluetoothActivity.class));
                break;
            case R.id.commodityLL:
                startActivity(new Intent(getActivity(), CommodityActivity.class));
                break;
            case R.id.evaluateLL:
                startActivity(new Intent(getActivity(), OrderingEvaluateActivity.class));
                break;
            case R.id.billLL:
                startActivity(new Intent(getActivity(), MyBillActivity.class));
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
                startActivity(new Intent(getActivity(), PlatformActivitsActivity.class));
                break;
            case R.id.settingLL:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.message_number:
                startActivity(new Intent(getActivity(), NewMessageActivity.class));
                break;
            case R.id.linear_personnelManage:
                startActivity(new Intent(getActivity(), PersonnelManageActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        init();
        super.onResume();
    }

    private void isCloseShop() {
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sure, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView title = view1.findViewById(R.id.title);
        Button sure = view1.findViewById(R.id.sure_bt);
        Button cancel = view1.findViewById(R.id.cancel_bt);
        title.setText("确认关店吗");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
//                    business 是否营业中 yes no
                    shopStr.put("id", shop.getId());
                    shopStr.put("business", "no");

                    params.put("shopStr", shopStr);
                    params.put("userId", shop.getShopkeeperId());
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    public void onClick(View v) {}

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_Data:
                todayOrder = rootView.findViewById(R.id.today_order);
                todayMoney=rootView.findViewById(R.id.today_money);
                if (json.getInt("statusCode") == 200) {
                    todayOrder.setText(json.getString("ordercount"));
                    if (json.getString("actualcost").equals("null"))
                        todayMoney.setText("0");
                    else todayMoney.setText(json.getString("actualcost"));
                }
                break;
            case Config.EDIT_SHOP_INFO:
                Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                if (json.getInt("statusCode") == 200) {
                    if (shop.getBusiness() != null &&shop.getBusiness().equals("yes")) {
                        openShopText.setText("已关店");
                        openShopText.setTextColor(getActivity().getResources().getColor(R.color.gray_2));
                        shopImg.setImageResource(R.drawable.shop);
                        shop.setBusiness("no");
                        dbManagerShop.updateShop(shop);
                    } else {
                        openShopText.setText("营业中");
                        openShopText.setTextColor(Color.parseColor("#02971F"));
                        shopImg.setImageResource(R.drawable.app_gl_sd);
                        shop.setBusiness("yes");
                        dbManagerShop.updateShop(shop);
                    }
                }
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder1.unbind();
    }

    @OnClick(R.id.name_RL)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), ShopInfoActivity.class));
    }
}
