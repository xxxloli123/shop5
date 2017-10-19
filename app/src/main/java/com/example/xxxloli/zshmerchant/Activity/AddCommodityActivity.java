package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.SelectClassifyAdapter;
import com.example.xxxloli.zshmerchant.adapter.UniversalClassifyAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.objectmodel.UniversalClassify;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.slowlife.lib.MD5;
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
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddCommodityActivity extends BaseActivity {


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

    private ListPopupWindow listPopupWindow;
    private ArrayList<UniversalClassify> universalClassifies1, universalClassifies2, universalClassifies3,
            universalClassifies4, universalClassifies5, universalClassifies6;
    private UniversalClassify universalClassify;
    private boolean isShow1 = true, isShow2 = true, isShow3 = true, isShow4 = true, isShow5 = true, isShow6 = true;
    private ArrayList<Classify> classifies1, classifies2;
    private Classify classify;
    private String productId;
    private String pictureLibraryId,sequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        listPopupWindow = new ListPopupWindow(this);
        Map<String, Object> params = new HashMap<>();
        params.put("fatherId", "");
        newCall(Config.Url.getUrl(Config.GET_UniversalClassify), params);
        Map<String, Object> params1 = new HashMap<>();
        params1.put("shopId", "402880e75f000ab6015f0043a1fc0004");
        newCall(Config.Url.getUrl(Config.GET_Classify_1), params1);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_commodity;
    }

    @OnClick({R.id.back_rl, R.id.show1, R.id.show2, R.id.show3, R.id.show4, R.id.show5, R.id.show6,
            R.id.classifyRL, R.id.priceRL, R.id.specificationRL, R.id.save_bt, R.id.commodity_img, R.id.sequenceRL})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.show1:
                if (isShow1) {
                    universalClassifies2 = new ArrayList<>();
                    selectType(firstText, show1, firstImg, secondText, show2, universalClassifies1, universalClassifies2);
                } else isShow1 = true;
                break;
            case R.id.show2:
                if (isShow2) {
                    universalClassifies3 = new ArrayList<>();
                    selectType(secondText, show2, secondImg, thirdlyText, show3, universalClassifies2, universalClassifies3);
                } else isShow2 = true;
                break;
            case R.id.show3:
                if (isShow3) {
                    universalClassifies4 = new ArrayList<>();
                    selectType(thirdlyText, show3, thirdlyImg, fourthlyText, show4, universalClassifies3, universalClassifies4);
                } else isShow3 = true;
                break;
            case R.id.show4:
                if (isShow4) {
                    universalClassifies5 = new ArrayList<>();
                    selectType(fourthlyText, show4, fifthImg, fifthText, show5, universalClassifies4, universalClassifies5);
                } else isShow4 = true;
                break;
            case R.id.show5:
                if (isShow5) {
                    universalClassifies6 = new ArrayList<>();
                    selectType(fifthText, show5, fifthImg, sixthText, show6, universalClassifies5, universalClassifies6);
                } else isShow5 = true;
                break;
            case R.id.show6:
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
                if (productId==null){
                    Toast.makeText(this, "请先选择先提交商品后再操作", Toast.LENGTH_LONG).show();
                    return;
                }
                intent = new Intent(this, SpecificationActivity.class);
                intent.putExtra("productId", productId);
                startActivity(intent);
                break;
            case R.id.save_bt:
                submitImg();
                break;
            case R.id.sequenceRL:
                EditSequence();
                break;
            case R.id.commodity_img:
                if (universalClassify == null) {
                    Toast.makeText(this, "请先选择图片上方的商品分类", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.e("classId", "丢了一个雷姆" + universalClassify.getId());
                intent = new Intent(this, SelectCommodityImgActivity.class);
                intent.putExtra("classId", universalClassify.getId());
                startActivityForResult(intent, 2333);
                break;
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
                if (isEmpty(sequence)){
                    Toast.makeText(AddCommodityActivity.this, "请填写序列号", Toast.LENGTH_SHORT).show();
                    return;
                }
                sequenceTV.setText(sequence);
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 2333:
                    if (data == null) {
                        Toast.makeText(this, "图片选择错误", Toast.LENGTH_LONG).show();
                        return;
                    }
                    pictureLibraryId = data.getStringExtra("pictureLibraryId");
                    Picasso.with(this).load(Config.Url.getUrl(Config.IMG)+data.getStringExtra("img")).into(commodityImg);
                    break;
            }
        }
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
                final ListPopupWindow listPopupWindow = new ListPopupWindow(AddCommodityActivity.this);
                SelectClassifyAdapter selectClassifyAdapter = new SelectClassifyAdapter(AddCommodityActivity.this, classifies1);
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
                        params.put("shopId", "402880e75f000ab6015f0043a1fc0004");
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
                    Toast.makeText(AddCommodityActivity.this, "选择的一级分类下并没有二级分类", Toast.LENGTH_SHORT).show();
                    return;
                }
                final ListPopupWindow listPopupWindow = new ListPopupWindow(AddCommodityActivity.this);
                SelectClassifyAdapter selectClassifyAdapter2 = new SelectClassifyAdapter(AddCommodityActivity.this, classifies2);
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
                    Toast.makeText(AddCommodityActivity.this, "请选择二级分类", Toast.LENGTH_SHORT).show();
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

    private void selectType(final TextView thisTV, final RelativeLayout thisRL, final ImageView thisImg,
                            final TextView nextTV, final RelativeLayout nextRL, final ArrayList<UniversalClassify> thisUniversalClassify,
                            final ArrayList<UniversalClassify> nextUniversalClassify) {
        switch (thisRL.getId()) {
            case R.id.show1:
                if (show3.getVisibility() == View.VISIBLE) show3.setVisibility(View.GONE);
                if (show4.getVisibility() == View.VISIBLE) show4.setVisibility(View.GONE);
                if (show5.getVisibility() == View.VISIBLE) show5.setVisibility(View.GONE);
                if (show6.getVisibility() == View.VISIBLE) show6.setVisibility(View.GONE);
                break;
            case R.id.show2:
                if (show4.getVisibility() == View.VISIBLE) show4.setVisibility(View.GONE);
                if (show5.getVisibility() == View.VISIBLE) show5.setVisibility(View.GONE);
                if (show6.getVisibility() == View.VISIBLE) show6.setVisibility(View.GONE);
                break;
            case R.id.show3:
                if (show5.getVisibility() == View.VISIBLE) show5.setVisibility(View.GONE);
                if (show6.getVisibility() == View.VISIBLE) show6.setVisibility(View.GONE);
                break;
            case R.id.show4:
                if (show6.getVisibility() == View.VISIBLE) show6.setVisibility(View.GONE);
                break;
        }
        nextTV.setText("请选择上一级分类");
        if (thisUniversalClassify.size() == 0) {
            Toast.makeText(this, "请选择上一级分类", Toast.LENGTH_SHORT).show();
            return;
        }
        UniversalClassifyAdapter universalClassifyAdapter = new UniversalClassifyAdapter(this, thisUniversalClassify);
        listPopupWindow.setAdapter(universalClassifyAdapter);
        listPopupWindow.setAnchorView(thisRL);
        listPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        listPopupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        thisImg.setRotation(180);
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                switch (thisRL.getId()) {
                    case R.id.show1:
                        isShow1 = false;
                        break;
                    case R.id.show2:
                        isShow2 = false;
                        break;
                    case R.id.show3:
                        isShow3 = false;
                        break;
                    case R.id.show4:
                        isShow4 = false;
                        break;
                    case R.id.show5:
                        isShow5 = false;
                        break;
                    case R.id.show6:
                        isShow6 = false;
                        break;
                }
                thisImg.setRotation(360);
            }
        });
        listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                // TODO Auto-generated method stub
                universalClassify = thisUniversalClassify.get(position);
                thisTV.setText(thisUniversalClassify.get(position).getGenericClassName());
                nextTV.setTag(thisUniversalClassify.get(position).getFatherId());
                RequestBody requestBody2 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("fatherId", thisUniversalClassify.get(position).getId()).build();
                Request request = new Request.Builder().url(Config.Url.getUrl(Config.GET_UniversalClassify)).post(requestBody2).build();
                new OkHttpClient().newCall(request).enqueue(new SimpleCallback(AddCommodityActivity.this) {
                    @Override
                    public void onSuccess(String tag, JSONObject json) throws JSONException {
                        Toast.makeText(AddCommodityActivity.this, json.getString("message"), Toast.LENGTH_SHORT).show();
                        JSONArray arr = json.getJSONArray("genericClassList");
                        if (arr.length() == 0) return;
                        Gson gson = new Gson();
                        for (int i = 0; i < arr.length(); i++) {
                            nextUniversalClassify.add(gson.fromJson(arr.getString(i), UniversalClassify.class));
                        }
                        nextRL.setVisibility(View.VISIBLE);
                        nextTV.setText(nextUniversalClassify.get(0).getGenericClassName() + "");
                    }
                });

                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

    private void submitImg() {
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
        if (isEmpty(sequence)){
            Toast.makeText(AddCommodityActivity.this, "请设置序列号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (universalClassify == null) {
            Toast.makeText(this, "请选择商品类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (classify == null) {
            Toast.makeText(this, "请选择分类", Toast.LENGTH_SHORT).show();
            return;
        }
        if (universalClassify == null) {
            Toast.makeText(this, "请选择商品类型", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pictureLibraryId == null) {
            Toast.makeText(this, "请选择图片", Toast.LENGTH_SHORT).show();
            return;
        }
//        添加商品不添加规格:::productStr[shopClassId 商家分类ID, shopClassName 商家分类名称, productName 商品名称,
// shopsort 排序号, details 商品描述,singlePrice 单价	genericClassId 通用分类ID, genericClassName 通用分类名称 ];
// shopId[商家id]；pictureLibraryId[选择的图片id]
//        参数：[productStr, shopId, pictureLibraryId]
        Map<String, Object> params = new HashMap<>();
        JSONObject productStr = new JSONObject();
        try {
            productStr.put("shopClassId", classify.getId());
            productStr.put("shopClassName", classify.getProductClassName());
            productStr.put("productName", name);
            productStr.put("shopsort", sequence);
            productStr.put("details", describe);
            productStr.put("singlePrice", price);
            productStr.put("genericClassId", universalClassify.getId());
            productStr.put("genericClassName", universalClassify.getGenericClassName());

            params.put("productStr", productStr);
            params.put("shopId", "402880e75f000ab6015f0043a1fc0004");
            params.put("pictureLibraryId", pictureLibraryId);
            newCall(Config.Url.getUrl(Config.ADD_Commodity), params);
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        switch (tag.toString()) {
            case Config.GET_UniversalClassify:
                JSONArray arr = json.getJSONArray("genericClassList");
//                if (arr.length() == 0) return;
                universalClassifies1 = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    universalClassifies1.add(gson.fromJson(arr.getString(i), UniversalClassify.class));
                }
                firstText.setText(universalClassifies1.get(0).getGenericClassName());
                break;
            case Config.GET_Classify_1:
                classifies1 = new ArrayList<>();
                JSONArray arr1 = json.getJSONArray("listclass");
                if (arr1.length() == 0) return;
                Gson gson1 = new Gson();
                for (int i = 0; i < arr1.length(); i++) {
                    classifies1.add(gson1.fromJson(arr1.getString(arr1.length() - i - 1), Classify.class));
                }
                Map<String, Object> params = new HashMap<>();
                params.put("shopId", "402880e75f000ab6015f0043a1fc0004");
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
                break;
            case Config.ADD_Commodity:
                Log.e("ADD_Commodity","丢了个雷姆"+json.getString("productId"));
                productId=json.getString("productId");
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
