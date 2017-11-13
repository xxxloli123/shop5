package com.example.xxxloli.zshmerchant.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.SelectClassifyAdapter;
import com.example.xxxloli.zshmerchant.fragment.AlreadyBuildFragmet;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.ActivityCommodity;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditCommodityActivity extends BaseActivity {


    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.first_img)
    ImageView firstImg;
    @BindView(R.id.first_text)
    TextView firstText;
    @BindView(R.id.show1)
    RelativeLayout show1;
    @BindView(R.id.second_img)
    ImageView secondImg;
    @BindView(R.id.second_text)
    TextView secondText;
    @BindView(R.id.show2)
    RelativeLayout show2;
    @BindView(R.id.thirdly_img)
    ImageView thirdlyImg;
    @BindView(R.id.thirdly_text)
    TextView thirdlyText;
    @BindView(R.id.show3)
    RelativeLayout show3;
    @BindView(R.id.fourthly_img)
    ImageView fourthlyImg;
    @BindView(R.id.fourthly_text)
    TextView fourthlyText;
    @BindView(R.id.show4)
    RelativeLayout show4;
    @BindView(R.id.fifth_img)
    ImageView fifthImg;
    @BindView(R.id.fifth_text)
    TextView fifthText;
    @BindView(R.id.show5)
    RelativeLayout show5;
    @BindView(R.id.sixth_img)
    ImageView sixthImg;
    @BindView(R.id.sixth_text)
    TextView sixthText;
    @BindView(R.id.show6)
    RelativeLayout show6;

    @BindView(R.id.classify)
    TextView classifyTV;
    @BindView(R.id.i)
    ImageView i;
    @BindView(R.id.classifyRL)
    RelativeLayout classifyRL;
    @BindView(R.id.price)
    EditText priceET;
    @BindView(R.id.im)
    ImageView im;
    @BindView(R.id.priceRL)
    RelativeLayout priceRL;
    @BindView(R.id.specification)
    TextView specification;

    @BindView(R.id.specificationRL)
    RelativeLayout specificationRL;
    @BindView(R.id.save_bt)
    Button saveBt;
    @BindView(R.id.commodity_img)
    ImageView commodityImg;
    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.describe_ET)
    EditText describeET;
    @BindView(R.id.sequence_TV)
    TextView sequenceTV;
    @BindView(R.id.sequenceRL)
    RelativeLayout sequenceRL;
    @BindView(R.id.putawayORsold_out_bt)
    Button putawayORsoldOutBt;
    @BindView(R.id.delete_bt)
    Button deleteBt;
    @BindView(R.id.operationLL)
    LinearLayout operationLL;

    private ArrayList<Classify> classifies1, classifies2;
    private Classify classify;
    private String productId;
    private String pictureLibraryId, sequence;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    private Commodity commodity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        ButterKnife.bind(this);
        operationLL.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        if (intent.getSerializableExtra(CommodityActivity.EDIT_Commodity) != null) {
            commodity = (Commodity) intent.getSerializableExtra(CommodityActivity.EDIT_Commodity);
            initView();
        } else {
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void initView() {
        Map<String, Object> params1 = new HashMap<>();
        params1.put("shopId", shop.getId());
        newCall(Config.Url.getUrl(Config.GET_Classify_1), params1);
        Picasso.with(this).load(Config.Url.getUrl(Config.IMG_Commodity)+commodity.getSmallImg()).into(commodityImg);
        sequenceTV.setText(commodity.getShopsort() + "");
        nameET.setText(commodity.getProductName());
        describeET.setText(commodity.getDetails());
        classifyTV.setText(commodity.getShopClassName());
        classify=new Classify();
        classify.setProductClassName(commodity.getShopClassName());
        classify.setId(commodity.getShopClassId());
        priceET.setText(commodity.getSinglePrice()+"");
        firstText.setText(commodity.getGenericClassName());
        if (commodity.getStatus().equals("Normal")) putawayORsoldOutBt.setText("下架");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_commodity;
    }

    @OnClick({R.id.back_rl, R.id.show1, R.id.show2, R.id.show3, R.id.show4, R.id.show5, R.id.show6,
            R.id.classifyRL, R.id.priceRL, R.id.specificationRL, R.id.save_bt, R.id.commodity_img,
            R.id.sequenceRL,R.id.putawayORsold_out_bt, R.id.delete_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.classifyRL:
                selectClassify();
                break;
            case R.id.priceRL:
                priceET.setFocusable(true);
                priceET.setFocusableInTouchMode(true);
                priceET.requestFocus();
                hideKeyBoard();
                break;
            case R.id.specificationRL:
                Intent intent = new Intent(this, SpecificationActivity.class);
                intent.putExtra("productId", commodity.getId());
                startActivity(intent);
                break;
            case R.id.save_bt:
                submit();
                break;
            case R.id.sequenceRL:
                EditSequence();
                break;
            case R.id.putawayORsold_out_bt:
//                status 取值：：：{Wait_audit(未发布 ), Normal(已发布), Stop(已下架)};]
                if (putawayORsoldOutBt.getText().toString().equals("下架")){
                    putawayORsoldOut("Stop");
                    putawayORsoldOutBt.setText("上架");
                }
                else {
                    putawayORsoldOut("Normal");
                    putawayORsoldOutBt.setText("下架");
                }
                break;
            case R.id.delete_bt:
//                删除商品::productId 商品ID；userId 用户ID
//                参数：[productId, userId]
                Map<String, Object> params1 = new HashMap<>();
                params1.put("productId", commodity.getId());
                params1.put("userId", shop.getShopkeeperId());
                newCall(Config.Url.getUrl(Config.DELETE_Commodity), params1);
                break;
        }
    }

    private void putawayORsoldOut(String status) {
//        更改商品状态::productStr[id商品ID；status 取值：：：{Wait_audit(未发布 ), Normal(已发布), Stop(已下架)};]
//        参数：[productStr]
        Map<String, Object> params1 = new HashMap<>();
        JSONObject productStr = new JSONObject();
        try {
            productStr.put("id", commodity.getId());
            productStr.put("status", status);
            params1.put("productStr", productStr);
            newCall(Config.Url.getUrl(Config.EDIT_CommodityStatus), params1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void EditSequence() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_priority, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        TextView hint = view.findViewById(R.id.hint_text);
        final EditText text = view.findViewById(R.id.edit);
        hint.setText("序列号只能为0到999");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sequence = text.getText().toString().trim();
                if (isEmpty(sequence)) {
                    Toast.makeText(EditCommodityActivity.this, "请填写序列号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sequenceTV.setText(sequence);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void selectClassify() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_classify, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final TextView classify1Text = view.findViewById(R.id.classify_1_text);
        final TextView classify2Text = view.findViewById(R.id.classify_2_text);
        final ImageView classify2Img = view.findViewById(R.id.classify_2_img);
        final ImageView classify1Img = view.findViewById(R.id.classify_1_img);
        if (classifies1 != null) {
            if (classifies1.size() != 0) {
                classify1Text.setText(classifies1.get(0).getProductClassName());
            }
        } else return;
        if (classifies2 != null) {
            if (classifies2.size() != 0) {
                classify2Text.setText(classifies2.get(0).getProductClassName());
            }
        }

        classify1Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ListPopupWindow listPopupWindow = new ListPopupWindow(EditCommodityActivity.this);
                SelectClassifyAdapter selectClassifyAdapter = new SelectClassifyAdapter(EditCommodityActivity.this, classifies1);
                listPopupWindow.setAdapter(selectClassifyAdapter);
                listPopupWindow.setAnchorView(classify1Text);
                listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                listPopupWindow.setModal(true);
                classify1Img.setRotation(180);
                listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        classify1Img.setRotation(360);
                    }
                });
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        classify1Text.setText(classifies1.get(position).getProductClassName());
                        Map<String, Object> params = new HashMap<>();
                        params.put("shopId", shop.getId());
                        params.put("fatherId", classifies1.get(position).getId());
                        newCall(Config.Url.getUrl(Config.GET_Classify_2), params);
                        listPopupWindow.dismiss();
                    }
                });
                listPopupWindow.show();
            }
        });
        classify2Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (classifies2 == null) {
                    return;
                }
                if (classifies2.size() == 0) {
                    Toast.makeText(EditCommodityActivity.this, "选择的一级分类下并没有二级分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ListPopupWindow listPopupWindow = new ListPopupWindow(EditCommodityActivity.this);
                SelectClassifyAdapter selectClassifyAdapter2 = new SelectClassifyAdapter(EditCommodityActivity.this, classifies2);
                listPopupWindow.setAdapter(selectClassifyAdapter2);
                listPopupWindow.setAnchorView(classify2Text);
                listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
                listPopupWindow.setModal(true);
                classify2Img.setRotation(180);
                listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        classify2Img.setRotation(360);
                    }
                });
                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // TODO Auto-generated method stub
                        classify = classifies2.get(position);
                        classify2Text.setText(classifies2.get(position).getProductClassName());
                        listPopupWindow.dismiss();
                    }
                });
                listPopupWindow.show();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (classify2Text.getText().equals("二级分类")) {
                    Toast.makeText(EditCommodityActivity.this, "请选择二级分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                classifyTV.setText(classify2Text.getText());
                alertDialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void submit() {
        String name = nameET.getText().toString().trim();
        String describe = describeET.getText().toString().trim();
        String price = priceET.getText().toString().trim();
        if (isEmpty(describe)) {
            Toast.makeText(this, "请填写物品描述", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(price)) {
            Toast.makeText(this, "请填写单价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(describe)) {
            Toast.makeText(this, "请填写物品描述", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(name)) {
            Toast.makeText(this, "请填写物品名称", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(sequenceTV.getText().toString())) {
            Toast.makeText(EditCommodityActivity.this, "请设置序列号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (classify == null) {
            Toast.makeText(this, "请选择分类", Toast.LENGTH_SHORT).show();
            return;
        }
//        修改商品基本属性::productStr[id:商品id;shopClassId 商家分类ID, shopClassName 商家分类名称,
//          productName商品名称, shopsort 排序号, details 商品描述,singlePrice 单价	];
//        参数：[productStr, userId]
        Map<String, Object> params = new HashMap<>();
        JSONObject productStr = new JSONObject();
        try {
            productStr.put("id", commodity.getId());
            productStr.put("shopClassId", classify.getId());
            productStr.put("shopClassName", classify.getProductClassName());
            productStr.put("productName", name);
            productStr.put("shopsort", sequenceTV.getText().toString());
            productStr.put("details", describe);
            productStr.put("singlePrice", price);

            params.put("productStr", productStr);
            params.put("userId", shop.getShopkeeperId());
            newCall(Config.Url.getUrl(Config.EDIT_Commodity), params);
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {

        switch (tag.toString()) {
            case Config.GET_Classify_1:
                classifies1 = new ArrayList<>();
                JSONArray arr1 = json.getJSONArray("listclass");
                if (arr1.length() == 0) return;
                Gson gson1 = new Gson();
                for (int i = 0; i < arr1.length(); i++) {
                    classifies1.add(gson1.fromJson(arr1.getString(arr1.length() - i - 1), Classify.class));
                }
                Map<String, Object> params = new HashMap<>();
                params.put("shopId", shop.getId());
                params.put("fatherId", classifies1.get(0).getId());
                newCall(Config.Url.getUrl(Config.GET_Classify_2), params);
                break;
            case Config.GET_Classify_2:
                classifies2 = new ArrayList<>();
                JSONArray arr2 = json.getJSONArray("listclass");
                if (arr2.length() == 0) return;
                Gson gson2 = new Gson();
                for (int i = 0; i < arr2.length(); i++) {
                    classifies2.add(gson2.fromJson(arr2.getString(arr2.length() - i - 1), Classify.class));
                }
                classify = classifies2.get(0);
                break;
            case Config.EDIT_Commodity:
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
                finish();
                break;
            case Config.DELETE_Commodity:
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    // 隐藏键盘
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 得到InputMethodManager的实例
        if (imm.isActive()) {
            // 如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            // 关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }
}
