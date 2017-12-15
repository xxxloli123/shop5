package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.BuildConfig;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.DBManagerUser;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.greendao.User;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;
import com.sgrape.dialog.LoadDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopInfoActivity extends BaseActivity {


    @BindView(R.id.back_rl)
    RelativeLayout backRl;

    @BindView(R.id.head_rl)
    RelativeLayout headRl;
    @BindView(R.id.get_name)
    TextView getName;
    @BindView(R.id.name)
    RelativeLayout name;
    @BindView(R.id.get_number)
    TextView getNumber;
    @BindView(R.id.number)
    RelativeLayout number;
    @BindView(R.id.get_address)
    TextView getAddress;
    @BindView(R.id.address)
    RelativeLayout address;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.QR_code)
    RelativeLayout QRCode;
    @BindView(R.id.get_notice)
    TextView getNotice;
    @BindView(R.id.shop_notice)
    RelativeLayout shopNotice;
    @BindView(R.id.get_time)
    TextView getTime;
    @BindView(R.id.business_time)
    RelativeLayout businessTime;
    @BindView(R.id.get_phone)
    TextView getPhone;
    @BindView(R.id.phone)
    RelativeLayout phone;
    @BindView(R.id.ordering_service)
    RelativeLayout orderingService;
    @BindView(R.id.ordering_system)
    RelativeLayout orderingSystem;
    @BindView(R.id.selective_dissemination)
    RelativeLayout selectiveDissemination;
    @BindView(R.id.head)
    CircleImageView head;
    @BindView(R.id.get_qualification)
    TextView getQualification;
    @BindView(R.id.qualification)
    RelativeLayout qualification;
    @BindView(R.id.get_identity)
    TextView getIdentity;
    @BindView(R.id.identity)
    RelativeLayout identity;

    private int textNumer;
    private File camera, clipping;
    private static final int CROP_CODE = 3;
    private DBManagerShop dbManagerShop;
    private DBManagerUser dbManagerUser;
    private User user;
    private Shop shop;
    private LoadDialog dialog;
    private String mImagePath = Environment.getExternalStorageDirectory() + "/meta/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_shop_info);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_info;
    }

    private void initView() {
        dbManagerShop = DBManagerShop.getInstance(this);
        dbManagerUser = DBManagerUser.getInstance(this);
        user = dbManagerUser.queryById((long) 2333).get(0);
        shop = dbManagerShop.queryById((long) 2333).get(0);
        Picasso.with(this).load(Config.Url.getUrl(Config.IMG_Hear) + shop.getShopImage())
                .into(head);
        getName.setText(shop.getShopName());
        getNumber.setText(shop.getShopNumber());
        getAddress.setText(shop.getHouseNumber());
        getNotice.setText(shop.getShopNotices());
        String startHour = shop.getStartDate().substring(0, 2);
        String startMinute = shop.getStartDate().substring(3, 5);
        String endHour = shop.getEndDate().substring(0, 2);
        String endMinute = shop.getEndDate().substring(3, 5);
        getTime.setText(startHour + ":" + startMinute + "—" + endHour + ":" + endMinute);
        getPhone.setText(shop.getTelePhone());
        getQualification.setText(user.getShopBusinessConfirm_value());
        getIdentity.setText(user.getIdConfirm_value());
    }

    @OnClick({R.id.back_rl, R.id.head_rl, R.id.name, R.id.number, R.id.address, R.id.QR_code, R.id.shop_notice,
            R.id.business_time, R.id.phone, R.id.ordering_service, R.id.ordering_system, R.id.selective_dissemination,
            R.id.qualification, R.id.identity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.head_rl:
                clipping = new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".png");
                popwindowsshow();
                break;
            case R.id.name:
                EditName();
                break;
            case R.id.number:
                break;
            case R.id.address:
                EditAddress();
                break;
            case R.id.QR_code:
                Intent intent = new Intent();
                intent.putExtra("QR_code", shop.getCollectMoneyCode());
                intent.setClass(this, ShopQRcodeActivity.class);
                startActivity(intent);
                break;
            case R.id.shop_notice:
                EditNotice();
                break;
            case R.id.business_time:
                startActivity(new Intent(ShopInfoActivity.this, BusinessTimeActivity.class));
                break;
            case R.id.phone:
                EditPhone();
                break;
            case R.id.ordering_service:
                OrderingService();
                break;
            case R.id.ordering_system:
                startActivity(new Intent(ShopInfoActivity.this, OrderingSystemActivity.class));
                break;
//            case R.id.selective_dissemination:
//                SelectiveDissemination();
//                break;
//            noUploaded("未上传"), Wait_audit("认证中 "), pass("认证通过"), unPass("认证未通过");
            case R.id.qualification:
//                if (user.getShopBusinessConfirm().equals("unPass"))
                    startActivity(new Intent(ShopInfoActivity.this, QualificationAuthenticationActivity.class));
//                else Toast.makeText(this, "认证未通过才会再次认证", Toast.LENGTH_SHORT).show();
                break;
            case R.id.identity:
//                if (user.getIdConfirm().equals("unPass"))
                    startActivity(new Intent(ShopInfoActivity.this, IdentityAuthenticationActivity.class));
//                else Toast.makeText(this, "认证未通过才会再次认证", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onRestart() {
        initView();
        super.onRestart();
    }

    /**
     * 弹出窗口
     */
    private void popwindowsshow() {
        final PopupWindow pop = new PopupWindow(this);
        View view = getLayoutInflater().inflate(R.layout.popwindows_head, null);
        final LinearLayout popwindows = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                pop.dismiss();
                popwindows.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//拍照
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (it.resolveActivity(getPackageManager()) != null) {
                    File path = new File(mImagePath);
                    if (!path.exists()) {
                        path.mkdir();
                    }
                    camera = new File(mImagePath, UUID.randomUUID().toString() + ".jpg");
                    Uri photoUri = FileProvider.getUriForFile(ShopInfoActivity.this,
                            "com.example.xxxloli.zshmerchant" + ".provider", camera);

                    it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                    startActivityForResult(it, 1);
                }
                pop.dismiss();
                popwindows.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//从相册中选择
                Intent local = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                local.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(local, 2);
                pop.dismiss();
                popwindows.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                popwindows.clearAnimation();
            }
        });
        pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

    private void OrderingService() {
        View view = LayoutInflater.from(ShopInfoActivity.this).inflate(R.layout.dialog_ordering_service, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ShopInfoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        final CheckBox jsyyd = view.findViewById(R.id.jsyyd);
        final CheckBox ddxf = view.findViewById(R.id.ddxf);
        final CheckBox pinzhuo = view.findViewById(R.id.pin_zhuo);
        if (shop.getBookingOrder() != null) {
            if (shop.getBookingOrder().equals("yes")) jsyyd.setChecked(true);
            else jsyyd.setChecked(false);
        }
        if (shop.getLineConsume() != null) {
            if (shop.getLineConsume().equals("yes")) ddxf.setChecked(true);
            else ddxf.setChecked(false);
        }
        if (shop.getTogetherTable() != null) {
            if (shop.getTogetherTable().equals("yes")) pinzhuo.setChecked(true);
            else pinzhuo.setChecked(false);
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //bookingOrder 接受预定单yes/no,lineConsume 支持到店(线下)消费yes/no,togetherTable 支持拼桌yes/no
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
                    shopStr.put("id", shop.getId());
                    String bookingOrder = jsyyd.isChecked() ? "yes" : "no";
                    String lineConsume = ddxf.isChecked() ? "yes" : "no";
                    String togetherTable = pinzhuo.isChecked() ? "yes" : "no";
//                    Toast.makeText(ShopInfoActivity.this,bookingOrder,Toast.LENGTH_SHORT).show();
                    Log.e("bookingOrder", "丢了个雷姆" + bookingOrder);
                    shopStr.put("bookingOrder", bookingOrder);
                    shopStr.put("lineConsume", lineConsume);
                    shopStr.put("togetherTable", togetherTable);

                    params.put("shopStr", shopStr);
                    params.put("userId", shop.getShopkeeperId());
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                    synchronization();
                    shop.setBookingOrder(bookingOrder);
                    shop.setLineConsume(lineConsume);
                    shop.setTogetherTable(togetherTable);
                    alertDialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void EditPhone() {
        View view = LayoutInflater.from(ShopInfoActivity.this).inflate(R.layout.dialog_ordering_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ShopInfoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(getPhone.getText())) {
            text.setText(getPhone.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(text.getText().toString().trim())) {
                    Toast.makeText(ShopInfoActivity.this, "请填写内容", Toast.LENGTH_SHORT).show();
                }
                getPhone.setText(text.getText().toString());
                submitInfo("telePhone", text.getText().toString());
                synchronization();
                shop.setTelePhone(text.getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditNotice() {
        View view = LayoutInflater.from(ShopInfoActivity.this).inflate(R.layout.dialog_shop_notice, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ShopInfoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(getNotice.getText())) {
            text.setText(getNotice.getText());
        }
        final TextView hint = view.findViewById(R.id.hint_text);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textNumer = charSequence.length();
                hint.setText("还能输入" + (140 - textNumer) + "个字符");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNotice.setText(text.getText());
                submitInfo("shopNotices", text.getText().toString());
                synchronization();
                shop.setShopNotices(text.getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void EditAddress() {
        View view = LayoutInflater.from(ShopInfoActivity.this).inflate(R.layout.dialog_shop_address, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ShopInfoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(getAddress.getText())) {
            text.setText(getAddress.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress.setText(text.getText());
                submitInfo("houseNumber", text.getText().toString());
                synchronization();
                shop.setHouseNumber(text.getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void synchronization() {
        shop = dbManagerShop.queryById((long) 2333).get(0);
    }

    private void EditName() {
        View view = LayoutInflater.from(ShopInfoActivity.this).inflate(R.layout.dialog_shop_name, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ShopInfoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        if (!isEmpty(getName.getText())) {
            text.setText(getName.getText());
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getName.setText(text.getText());
                alertDialog.dismiss();
                submitInfo("shopName", text.getText().toString());
                synchronization();
                shop.setShopName(text.getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }

    private void submitInfo(String type, String submit) {
        Map<String, Object> params = new HashMap<>();
        JSONObject shopStr = new JSONObject();
        try {
            shopStr.put("id", shop.getId());
            shopStr.put(type, submit);

            params.put("shopStr", shopStr);
            params.put("userId", shop.getShopkeeperId());
            newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void submitImg() {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JSONObject userStr = new JSONObject();
        try {
            userStr.put("id", shop.getShopkeeperId());
            fis = new FileInputStream(clipping);
            int len;
            byte[] buf = new byte[512];
            while ((len = fis.read(buf)) > 0) {
                baos.write(buf, 0, len);
                baos.flush();
            }
            byte[] fileData = baos.toByteArray();
            fis.close();
            baos.close();
            Request.Builder builder = new Request.Builder().url(Config.Url.getUrl(Config.XXRZ));
            RequestBody fileRequest1 = RequestBody.create(MediaType.parse("application/octet-stream"), fileData);
            RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("userStr", userStr.toString())
                    .addFormDataPart("pictures", clipping.getName(), fileRequest1).build();
            builder.method("POST", requestBody);
            new OkHttpClient().newCall(builder.build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ShopInfoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final int responseCode = response.code();
                    final String responseResult = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode != 200) {
                                Toast.makeText(ShopInfoActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONObject result = new JSONObject(responseResult);
                                Log.e("IMG", "丢了个雷姆" + result.toString());
                                if (result.getInt("statusCode") != 200) {
                                    Toast.makeText(ShopInfoActivity.this, "上传失败" + result.getJSONObject("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ShopInfoActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                    synchronization();
                                    shop.setShopImage(result.getJSONObject("shop").getString("shopImage"));
                                    dbManagerShop.updateShop(shop);
                                    Picasso.with(ShopInfoActivity.this).load(Config.Url.getUrl(Config.IMG_Hear) + shop.getShopImage())
                                            .into(head);
                                }
                            } catch (JSONException e) {
                                Toast.makeText(ShopInfoActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this, "读取图片出错", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 2:
                    Uri uri = data.getData();
                    if (uri == null) {
                        Toast.makeText(this, "选择图片文件读取出错", Toast.LENGTH_LONG).show();
                        return;
                    }
                    startImageZoom(uri);
//                    if (data == null) {
//                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
//                        return;
//                    } else {
////                    //用户从图库选择图片后会返回所选图片的Uri
//                        Uri uri;
////                    //获取到用户所选图片的Uri
//                        uri = data.getData();
////                    //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
//                        uri = convertUri(uri);
//                        startImageZoom(uri);
//                    }
                    break;
                case 1:
//                     else {
//                        Bundle extras = data.getExtras();
//                        if (extras != null) {
//                            //获得拍的照片
//                            Bitmap bm = extras.getParcelable("data");
//                            //将Bitmap转化为uri
//                            Uri uri1 = saveBitmap(bm, "temp");
//                            //启动图像裁剪
//                            startImageZoom(uri1);
//                            getPhoto.setImageBitmap(bm);
//                        }
//                    }
                    //用户点击了取消
//                    if (data == null) {
//                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
//                        return;
//                    }
                    startImageZoom(FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
                            ".provider", camera));
                    break;
                case CROP_CODE:
////                    if (data == null) {
////                        return;
////                    } else {
//                    if (!clippingLater.exists()) {
//                        Toast.makeText(this, "图片文件剪裁出错", Toast.LENGTH_LONG).show();
//                        return;
//                    }
////                        Toast.makeText(this, "拍照后图片文件读取出错", Toast.LENGTH_LONG).show();
////                        Bundle extras = data.getExtras();
////                        if (extras != null) {
//                            //获取到裁剪后的图像
////                        Bitmap bm = extras.getParcelable("data");
//                            String fils = clippingBefore.getAbsolutePath();
//                            Bitmap bitmap1 = BitmapFactory.decodeFile(fils);
//                            getPhoto.setImageBitmap(bitmap1);
//                            compressPicture(fils, clippingLater);
//                            hint.setVisibility(View.GONE);
////                            submitImg();
////                        }
////                    }
                    if (data != null) {
                        try {
                            String fils = clipping.getAbsolutePath();
                            compressPicture(fils, clipping.getAbsoluteFile());
                            submitImg();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else Toast.makeText(this, "图片文件剪裁出错", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    /**
     * 按照图片尺寸压缩：
     */
    public static void compressPicture(String srcPath, File desPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, op);
        op.inJustDecodeBounds = false;
        if (op.outWidth == 200f) return;
        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        float hh = 400f;//
        float ww = 400f;//
        // 最长宽度或高度1024
        float be = 1.0f;
//        if (w > h || w > ww) {
//            be = (float) (w / ww);
//        } else if (w < h || h > hh) {
//            be = (float) (h / hh);
//        }
        if (h > hh && w > ww) be = (float) (w / ww);
        if (be <= 0) {
            be = 1.0f;
        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath, op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        bitmap = Bitmap.createScaledBitmap(bitmap, desWidth, desHeight, true);
        try {
            fos = new FileOutputStream(desPath);
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startImageZoom(Uri uri) {
        //构建隐式Intent来启动裁剪程序
        Intent intent = new Intent("com.android.camera.action.CROP");
        //添加这一句表示对目标应用临时授权该Uri所代表的文件
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        //设置数据uri和类型为图片类型
        intent.setDataAndType(uri, "image/*");
        //显示View为可裁剪的
        intent.putExtra("crop", true);
        //裁剪的宽高的比例为1:1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        //输出图片的宽高均为150
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        //裁剪之后的数据是通过Intent返回
//        Uri clippingUri = FileProvider.getUriForFile(XXRZActivity.this,
//                getPackageName() + ".provider",
//                clipping);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(clipping));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_CODE);
    }

    protected boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
        switch (tag.toString()) {
            case Config.EDIT_SHOP_INFO:
                dbManagerShop.updateShop(shop);
                break;
        }
    }

    @OnClick(R.id.packaging_rl)
    public void onViewClicked() {
        EditPackaging();
    }

    private void EditPackaging() {
        View view = LayoutInflater.from(ShopInfoActivity.this).inflate(R.layout.dialog_ordering_phone, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(ShopInfoActivity.this, R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView save = view.findViewById(R.id.save);
        final EditText text = view.findViewById(R.id.edit);
        text.setText(shop.getPackingprice()+"");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(text.getText().toString().trim())) {
                    Toast.makeText(ShopInfoActivity.this, "请填写内容", Toast.LENGTH_SHORT).show();
                    return;
                }
                submitInfo("packingprice", text.getText().toString());
                synchronization();
                shop.setPackingprice(text.getText().toString());
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();
    }
}
