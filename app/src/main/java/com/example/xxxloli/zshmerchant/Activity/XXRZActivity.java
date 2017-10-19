package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Info;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class XXRZActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.shop_name)
    EditText shopName;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.hint)
    TextView hint;
    @BindView(R.id.get_photo)
    ImageView getPhoto;
    @BindView(R.id.get_qualification)
    TextView getQualification;
    @BindView(R.id.get_identity)
    TextView getIdentity;
    @BindView(R.id.photo)
    RelativeLayout photo;
    @BindView(R.id.qualification)
    RelativeLayout qualification;
    @BindView(R.id.identity)
    RelativeLayout identity;
    @BindView(R.id.sure_bt)
    TextView sureBt;


    private File clippingBefore, clippingLater;
    private static final int CROP_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_xxrz);
        ButterKnife.bind(this);

        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xxrz;
    }

    private void initView() {
    }

    @OnClick({R.id.back_rl, R.id.photo, R.id.qualification, R.id.identity, R.id.sure_bt})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.photo:
                clippingBefore = new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".png");
                clippingLater = new File(getExternalCacheDir(), UUID.randomUUID().toString() + ".png");
                popwindowsshow();
                break;
            case R.id.qualification:
                intent = new Intent(this, QualificationAuthenticationActivity.class);
                startActivityForResult(intent, 666);
                break;
            case R.id.identity:
                intent = new Intent(this, IdentityAuthenticationActivity.class);
                startActivityForResult(intent, 2333);
                break;
            case R.id.sure_bt:
                submit();
                break;
        }
    }

    /**
     * 获取指定文件大小
     *
     * @param
     * @return
     * @throws Exception
     */
    public static float getFileSize(File file) throws Exception {
        float size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    private void submit() {
        if (isEmpty(shopName.getText().toString().trim())) {
            Toast.makeText(this, "请输入店名", Toast.LENGTH_SHORT).show();
        } else if (isEmpty(address.getText().toString().trim())) {
            Toast.makeText(this, "请输入地址", Toast.LENGTH_SHORT).show();
        } else if (getIdentity.getText().equals("未认证")) {
            Toast.makeText(this, "请认证个人身份", Toast.LENGTH_SHORT).show();
        } else if (getIdentity.getText().equals("未认证")) {
            Toast.makeText(this, "请认证营业资格", Toast.LENGTH_SHORT).show();
        }
        try {
            if (getFileSize(clippingLater) < 20) {
                Toast.makeText(this, "请上传店铺照片 ", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Map<String, Object> params = new HashMap<>();
                JSONObject shopStr = new JSONObject();
                try {
                    shopStr.put("id", "402880ea5ef0b3a4015ef0b48e6f0003");
                    shopStr.put("shopName", shopName.getText().toString().trim());
                    shopStr.put("houseNumber", address.getText().toString().trim());

                    params.put("shopStr", shopStr);
                    params.put("userId", "402880ea5ef0b3a4015ef0b48df80001");
                    newCall(Config.Url.getUrl(Config.EDIT_SHOP_INFO), params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.EDIT_SHOP_INFO:
                Toast.makeText(this, json.getString("message"), Toast.LENGTH_SHORT).show();
                if (json.getString("statusCode").equals("200"))
                    startActivity(new Intent(XXRZActivity.this, LoginActivity.class));
                break;
        }
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
//                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(clippingBefore));
                startActivityForResult(it, 1);
                pop.dismiss();
                popwindows.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {//从相册中选择
                Intent local = new Intent();
                local.setType("image/*");
                local.setAction(Intent.ACTION_GET_CONTENT);
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

    /**
     * 拍照上传
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 2:
                    if (data == null) {
                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
                        return;
                    } else {
//                    //用户从图库选择图片后会返回所选图片的Uri
                        Uri uri;
//                    //获取到用户所选图片的Uri
                        uri = data.getData();
//                    //返回的Uri为content类型的Uri,不能进行复制等操作,需要转换为文件Uri
                        uri = convertUri(uri);
//                    startImageZoom(uri);
                        startImageZoom(uri);
                    }
                    break;
                case 1:
//                    //用户点击了取消
//                    if (data == null) {
//                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
//                        return;
//                    } else {
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
                    Uri uri = data.getData();
                    if (uri == null) {
                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
                        return;
                    }
                    Bitmap img;
                    try {
                        img = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                        //启动图像裁剪
                        startImageZoom(uri);
                        getPhoto.setImageBitmap(img);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    break;
                case CROP_CODE:
                    if (data == null) {
                        return;
                    } else {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            //获取到裁剪后的图像
//                        Bitmap bm = extras.getParcelable("data");
                            String fils = clippingBefore.getAbsolutePath();
                            Bitmap bitmap1 = BitmapFactory.decodeFile(fils);
                            getPhoto.setImageBitmap(bitmap1);
                            compressPicture(fils, clippingLater);
                            hint.setVisibility(View.GONE);
                            submitImg();
                        }
                    }
                    break;
                case 666:
                    if (data.getBooleanExtra("smg", false)) getQualification.setText("待审核");
                    break;
                case 2333:
                    if (data.getBooleanExtra("smg", false)) getIdentity.setText("待审核");
                    break;
            }
        }
    }

    private void submitImg() {
        FileInputStream fis = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        JSONObject userStr = new JSONObject();
        try {
            userStr.put("id", "402880ea5ef0b3a4015ef0b48df80001");
            fis = new FileInputStream(clippingLater);
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
                    .addFormDataPart("pictures", clippingLater.getName(), fileRequest1).build();
            builder.method("POST", requestBody);
            new OkHttpClient().newCall(builder.build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(XXRZActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int responseCode = response.code();
                    final String responseResult = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode != 200) {
                                Toast.makeText(XXRZActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try {
                                JSONObject result = new JSONObject(responseResult);
                                System.out.println(result.toString());
                                if (result.getInt("statusCode") != 200) {
                                    Toast.makeText(XXRZActivity.this, "上传失败" + result.getJSONObject("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(XXRZActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(XXRZActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
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

    /**
     * 按照图片尺寸压缩：
     *
     * @param srcPath
     * @param desPath
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(clippingBefore));
        intent.putExtra("return-data", false);
        startActivityForResult(intent, CROP_CODE);
    }

    /**
     * 将content类型的Uri转化为文件类型的Uri
     *
     * @param uri
     * @return
     */
    private Uri convertUri(Uri uri) {
        InputStream is;
        try {
            //Uri ----> InputStream
            is = getContentResolver().openInputStream(uri);
            //InputStream ----> Bitmap
            Bitmap bm = BitmapFactory.decodeStream(is);
            //关闭流
            is.close();
            return saveBitmap(bm, "temp");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将Bitmap写入SD卡中的一个文件中,并返回写入文件的Uri
     *
     * @param bm
     * @param dirPath
     * @return
     */
    private Uri saveBitmap(Bitmap bm, String dirPath) {
        //新建文件夹用于存放裁剪后的图片
        File tmpDir = new File(Environment.getExternalStorageDirectory() + "/" + dirPath);
        if (!tmpDir.exists()) {
            tmpDir.mkdir();
        }

        //新建文件存储裁剪后的图片
        File img = new File(tmpDir.getAbsolutePath() + "/avator.png");
        try {
            //打开文件输出流
            FileOutputStream fos = new FileOutputStream(img);
            //将bitmap压缩后写入输出流(参数依次为图片格式、图片质量和输出流)
            bm.compress(Bitmap.CompressFormat.PNG, 100, fos);
            //刷新输出流
            fos.flush();
            //关闭输出流
            fos.close();
            //返回File类型的Uri
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
