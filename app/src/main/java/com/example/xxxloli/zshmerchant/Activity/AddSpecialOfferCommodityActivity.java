package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.ListPopupWindow;
import android.util.Log;
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
import com.example.xxxloli.zshmerchant.adapter.UniversalClassifyAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;
import com.example.xxxloli.zshmerchant.objectmodel.UniversalClassify;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.sgrape.dialog.LoadDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddSpecialOfferCommodityActivity extends BaseActivity {


    @BindView(R.id.back_rl)
    RelativeLayout backRl;
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
    @BindView(R.id.commodity_img)
    ImageView commodityImg;
    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.describe_ET)
    EditText describeET;
    @BindView(R.id.original_priceET)
    EditText originalPriceET;
    @BindView(R.id.original_priceRL)
    RelativeLayout originalPriceRL;
    @BindView(R.id.special_offerET)
    EditText specialOfferET;
    @BindView(R.id.special_offerRL)
    RelativeLayout specialOfferRL;
    @BindView(R.id.inventory_ET)
    EditText inventoryET;
    @BindView(R.id.inventoryRL)
    RelativeLayout inventoryRL;
    @BindView(R.id.save_bt)
    Button saveBt;
    @BindView(R.id.first_img)
    ImageView firstImg;
    @BindView(R.id.time_ET)
    EditText timeET;
    @BindView(R.id.timeRL)
    RelativeLayout timeRL;

    private boolean isSeckill = false;
    private ListPopupWindow listPopupWindow;
    private ArrayList<UniversalClassify> universalClassifies1, universalClassifies2, universalClassifies3,
            universalClassifies4, universalClassifies5, universalClassifies6;
    private UniversalClassify universalClassify;
    private boolean isShow1 = true, isShow2 = true, isShow3 = true, isShow4 = true, isShow5 = true,
            isShow6 = true;
    private String pictureLibraryId;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    private LoadDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_special_offer_commodity);
        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        Intent intent = getIntent();
        if (intent.getStringExtra("type") != null) {
            isSeckill = true;
            timeRL.setVisibility(View.VISIBLE);
        }
        initView();
    }

    private void initView() {
        listPopupWindow = new ListPopupWindow(this);
        Map<String, Object> params = new HashMap<>();
        params.put("fatherId", "");
        newCall(Config.Url.getUrl(Config.GET_UniversalClassify), params);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_special_offer_commodity;
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
            case Config.ADD_ActivityCommodity:
                finish();
                break;
        }
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
                    Picasso.with(this).load(Config.Url.getUrl(Config.IMG_Commodity) + data.getStringExtra("img")).into(commodityImg);
                    nameET.setText(data.getStringExtra("name"));
                    describeET.setText(data.getStringExtra("describe"));
                    break;
            }
        }
    }

    @OnClick({R.id.back_rl, R.id.show1, R.id.show2, R.id.show3, R.id.show4, R.id.show5, R.id.show6, R.id.commodity_img, R.id.original_priceRL, R.id.special_offerRL, R.id.inventoryRL, R.id.save_bt})
    public void onViewClicked(View view) {
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
            case R.id.commodity_img:
                if (universalClassify == null) {
                    Toast.makeText(this, "请先选择图片上方的商品分类", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.e("classId", "丢了一个雷姆" + universalClassify.getId());
                Intent intent = new Intent(this, SelectCommodityImgActivity.class);
                intent.putExtra("classId", universalClassify.getId());
                startActivityForResult(intent, 2333);
                break;
            case R.id.original_priceRL:
                editContent(originalPriceET);
                break;
            case R.id.special_offerRL:
                editContent(specialOfferET);
                break;
            case R.id.inventoryRL:
                editContent(inventoryET);
                break;
            case R.id.save_bt:
                submit();
                break;
        }
    }
    @OnClick(R.id.timeRL)
    public void onViewClicked() {
        editContent(timeET);
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
        if (thisUniversalClassify.isEmpty()) {
            Toast.makeText(this, "请选择上一级分类", Toast.LENGTH_SHORT).show();
            return;
        }
        UniversalClassifyAdapter universalClassifyAdapter = new UniversalClassifyAdapter(this, thisUniversalClassify);
        listPopupWindow.setAdapter(universalClassifyAdapter);
        Log.e("ViewGroup.", "丢了个雷姆" + ViewGroup.LayoutParams.WRAP_CONTENT);
//        if (thisRL.getId() == show2.getId()) listPopupWindow.setWidth(666);
//        else
        listPopupWindow.setWidth((ViewGroup.LayoutParams.WRAP_CONTENT));
        listPopupWindow.setAnchorView(thisRL);
        listPopupWindow.setHeight(1333);
        thisImg.setRotation(180);
        final ArrayList<UniversalClassify> init = new ArrayList<UniversalClassify>();
        listPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                switch (thisRL.getId()) {
                    case R.id.show1:
                        isShow1 = true;
                        universalClassifies2 = init;
                        break;
                    case R.id.show2:
                        isShow2 = true;
                        universalClassifies3 = init;
                        break;
                    case R.id.show3:
                        isShow3 = true;
                        universalClassifies4 = init;
                        break;
                    case R.id.show4:
                        isShow4 = true;
                        universalClassifies5 = init;
                        break;
                    case R.id.show5:
                        isShow5 = true;
                        universalClassifies6 = init;
                        break;
                    case R.id.show6:
                        isShow6 = true;
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
                dialog = new LoadDialog(AddSpecialOfferCommodityActivity.this);
                dialog.show();
                universalClassify = thisUniversalClassify.get(position);
                thisTV.setText(thisUniversalClassify.get(position).getGenericClassName());
                nextTV.setTag(thisUniversalClassify.get(position).getFatherId());
                RequestBody requestBody2 = new MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("fatherId", thisUniversalClassify.get(position).getId()).build();
                Request request = new Request.Builder().url(Config.Url.getUrl(Config.GET_UniversalClassify)).post(requestBody2).build();
                new OkHttpClient().newCall(request).enqueue(new SimpleCallback(AddSpecialOfferCommodityActivity.this) {
                    @Override
                    public void onSuccess(String tag, JSONObject json) throws JSONException {
                        JSONArray arr = json.getJSONArray("genericClassList");
                        if (arr.length() == 0){
                            dialog.dismiss();
                            return;
                        }
                        Gson gson = new Gson();
                        for (int i = 0; i < arr.length(); i++) {
                            init.add(gson.fromJson(arr.getString(i), UniversalClassify.class));
                        }
                        universalClassify = init.get(0);
                        nextRL.setVisibility(View.VISIBLE);
                        nextTV.setText(init.get(0).getGenericClassName() + "");
                        dialog.dismiss();
                    }
                });

                listPopupWindow.dismiss();
            }
        });
        listPopupWindow.show();
    }

    private void submit() {
        String name = nameET.getText().toString().trim();
        String describe = describeET.getText().toString().trim();
        String originalPrice = originalPriceET.getText().toString().trim();
        String specialOffer = specialOfferET.getText().toString().trim();
        String inventory = inventoryET.getText().toString().trim();
        if (isEmpty(describe)) {
            Toast.makeText(this, "请填写物品描述", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(originalPrice)) {
            Toast.makeText(this, "请填写商品原价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(describe)) {
            Toast.makeText(this, "请填写商品描述", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(specialOffer)) {
            Toast.makeText(this, "请填写特价", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isEmpty(inventory)) {
            Toast.makeText(this, "请填写库存", Toast.LENGTH_SHORT).show();
            return;
        }
        if (universalClassify == null) {
            Toast.makeText(this, "请选择商品类型", Toast.LENGTH_SHORT).show();
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
        int time ;
        if (isEmpty(timeET.getText().toString().trim())) time=0;
        else time= Integer.parseInt(timeET.getText().toString().trim());
        if (isSeckill&&time==0){
            Toast.makeText(this, "请填写秒杀时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isSeckill&&time<9){
            Toast.makeText(this, "请填写正确秒杀时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (isSeckill&&time>16){
            Toast.makeText(this, "请填写正确秒杀时间", Toast.LENGTH_SHORT).show();
            return;
        }
        // applyId:用户id, applyName:用户姓名, shopId：商家ID, genericClassId 通用分类ID,
        // genericClassName 通用分类名称,// productName 商品名称, bargainPrice 产品特价价格, originalPrice 产品原价, inventory 库存,
        // details 商品的简单描述,// pictureLibraryId 产品图片id// seckillTime 整点秒杀时间9-16
        Map<String, Object> params = new HashMap<>();
        JSONObject productStr = new JSONObject();
        try {
            productStr.put("applyId", shop.getShopkeeperId());
            productStr.put("applyName", shop.getShopkeeperName());
            productStr.put("shopId", shop.getId());
            productStr.put("genericClassId", universalClassify.getId());
            productStr.put("genericClassName", universalClassify.getGenericClassName());
            productStr.put("productName", name);
            productStr.put("bargainPrice", specialOffer);
            productStr.put("originalPrice", originalPrice);
            productStr.put("inventory", inventory);
            productStr.put("details", describe);
            productStr.put("pictureLibraryId", pictureLibraryId);
            if (isSeckill)productStr.put("seckillTime", time);

//            参数：[bargaingoodStr, type];type[Bargain(特价商品), Seckill(整点秒杀),Free(免费试用);]
            params.put("bargaingoodStr", productStr);
            if (isSeckill)params.put("type", "Seckill");
            else params.put("type", "Bargain");
            newCall(Config.Url.getUrl(Config.ADD_ActivityCommodity), params);
        } catch (JSONException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void editContent(EditText content) {
        content.setFocusable(true);
        content.setFocusableInTouchMode(true);
        content.requestFocus();
        hideKeyBoard();
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
