package com.example.xxxloli.zshmerchant.Activity;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.DBManagerUser;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.greendao.User;
import com.example.xxxloli.zshmerchant.util.BitmapCompressUtils;
import com.interfaceconfig.Config;
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

public class IdentityAuthenticationActivity extends AppCompatActivity {


    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.id_number_edit)
    EditText idNumberEdit;
    @BindView(R.id.identity_front)
    ImageView identityFront;
    @BindView(R.id.upload_front)
    TextView uploadFront;
    @BindView(R.id.identity_opposite)
    ImageView identityOpposite;
    @BindView(R.id.upload_opposite)
    TextView uploadOpposite;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.sure_bt)
    TextView sureBt;
    @BindView(R.id.layout)
    LinearLayout layout;
    @BindView(R.id.head)
    ImageView head;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.identity)
    TextView identity;
    @BindView(R.id.authentication)
    LinearLayout authentication;

    private File file1, file2;
    private boolean tag;
    private DBManagerUser dbManagerUser;
    private User user;
    private String mImagePath = Environment.getExternalStorageDirectory()+"/meta/";
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity_authentication);
        ButterKnife.bind(this);
        dbManagerUser= DBManagerUser.getInstance(this);
        user = dbManagerUser.queryById((long) 2333).get(0);
        if (savedInstanceState != null) tag = savedInstanceState.getBoolean("tag");
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
                    Uri photoUri;
                    if (tag){
                        file1 = new File(mImagePath, UUID.randomUUID().toString()+".jpg");
                        photoUri = FileProvider.getUriForFile(IdentityAuthenticationActivity.this,
                                "com.example.xxxloli.zshmerchant" + ".provider", file1);
                    }else {
                        file2 = new File(mImagePath, UUID.randomUUID().toString()+".jpg");
                        photoUri = FileProvider.getUriForFile(IdentityAuthenticationActivity.this,
                                "com.example.xxxloli.zshmerchant" + ".provider", file2);
                    }
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
                Intent local =new  Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    @OnClick({R.id.back_rl, R.id.upload_front, R.id.upload_opposite, R.id.sure_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.upload_front:
                tag = true;
                popwindowsshow();
                break;
            case R.id.upload_opposite://上传身份证反面照
                tag = false;
                popwindowsshow();
                break;
            case R.id.sure_bt://确认提交
                submit();
                break;
        }
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
                    Uri uri = data.getData();
                    if (uri == null) {
                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        imagePath = null;
                        if (DocumentsContract.isDocumentUri(this, uri)) {
                            //如果是document类型的uri,则通过document id处理
                            String docId = DocumentsContract.getDocumentId(uri);
                            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                                String id = docId.split(":")[1];//解析出数字格式的id
                                String selection = MediaStore.Images.Media._ID + "=" + id;
                                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
                            } else if ("com.android.downloads.documents".equals(uri.getAuthority())) {
                                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                                imagePath = getImagePath(contentUri, null);
                            }
                        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                            //如果是content类型的Uri，则使用普通方式处理
                            imagePath = getImagePath(uri, null);
                        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                            //如果是file类型的Uri,直接获取图片路径即可
                            imagePath = uri.getPath();
                        }
                        Picasso.with(this).load(uri).into(tag?identityFront:identityOpposite);
                        if (tag) file1 = new File(imagePath);
                        else file2= new File(imagePath);
                    }
                    break;
                case 1:
                    File file = tag ? file1 : file2;
                    if (!file.exists()) {
                        Toast.makeText(this, "图片文件读取出错", Toast.LENGTH_LONG).show();
                        return;
                    }
//                    try {
//                        Log.e("大小", "丢了个雷姆" + getFileSize(file));
//                        Toast.makeText(IdentityAuthenticationActivity.this, "丢了个雷姆" + getFileSize(file), Toast.LENGTH_SHORT);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        Log.e("获取文件大小", "文件不存在!");
//                    }
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    compressPicture(file, file);
                    if (tag) {
                        identityFront.setImageBitmap(bitmap);
                    } else identityOpposite.setImageBitmap(bitmap);
                    break;
            }
        }
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        //通过Uri和selection老获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void submit() {
        if (isEmpty(idNumberEdit.getText().toString().trim())) {
            Toast.makeText(this, "请输入身份证号", Toast.LENGTH_SHORT).show();
        }else try {
            if (getFileSize(file1)<20||getFileSize(file2)<20) {
                Toast.makeText(this, "请添加身份证图片", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
    //            Info info = ((MyApplication) getApplication()).getInfo();
                try {
                    JSONObject userStr = new JSONObject();
    //                userStr.put("id", info.getId());
                    userStr.put("id", user.getId());
                    userStr.put("idNumber", idNumberEdit.getText().toString().trim());
                    Map<String, Object> params = new HashMap<>();
                    params.put("userStr", userStr);
                    FileInputStream fis = new FileInputStream(file1);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int len;
                    byte[] buf = new byte[512];
                    while ((len = fis.read(buf)) > 0) {
                        baos.write(buf, 0, len);
                        baos.flush();
                    }
                    byte[] file1Data = baos.toByteArray();
                    fis.close();
                    baos.close();
                    fis = new FileInputStream(file2);
                    baos = new ByteArrayOutputStream();
                    while ((len = fis.read(buf)) > 0) {
                        baos.write(buf, 0, len);
                        baos.flush();
                    }
                    byte[] file2Data = baos.toByteArray();
                    fis.close();
                    baos.close();
                    Request.Builder builder = new Request.Builder().url(Config.Url.getUrl(Config.XXRZ));
                    RequestBody fileRequest1 = RequestBody.create(MediaType.parse("application/octet-stream"), file1Data);
                    RequestBody fileRequest2 = RequestBody.create(MediaType.parse("application/octet-stream"), file2Data);
                    RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                            .addFormDataPart("userStr", userStr.toString())
                            .addFormDataPart("pictures", file1.getName(), fileRequest1)
                            .addFormDataPart("pictures", file2.getName(), fileRequest2).build();
                    builder.method("POST", requestBody);
                    Toast.makeText(this,"正在上传请耐心等待。。。",Toast.LENGTH_SHORT).show();
                    new OkHttpClient().newCall(builder.build()).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(IdentityAuthenticationActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                                        Toast.makeText(IdentityAuthenticationActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    try {
                                        JSONObject result = new JSONObject(responseResult);
                                        System.out.println(result.toString());
                                        if (result.getInt("statusCode") != 200) {
                                            Toast.makeText(IdentityAuthenticationActivity.this,
                                                    "上传失败" + result.getJSONObject("message"), Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(IdentityAuthenticationActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent();
                                            intent.putExtra("smg",true);
                                            setResult(Activity.RESULT_OK, intent);
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        Toast.makeText(IdentityAuthenticationActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                } catch (JSONException e) {
                    Toast.makeText(this, "解析数据失败", Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    Toast.makeText(this, "读取图片出错", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 按照图片尺寸压缩：
     */
    public void compressPicture(File srcPath, File desPath) {
        FileOutputStream fos = null;
        BitmapFactory.Options op = new BitmapFactory.Options();

        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        op.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath.getAbsolutePath(), op);
        op.inJustDecodeBounds = false;
        if (op.outWidth == 200f) return;
        // 缩放图片的尺寸
        float w = op.outWidth;
        float h = op.outHeight;
        // 最长宽度或高度1024
        float be = 1.0f;
        try {
            be = getFileSize(srcPath) / 1000 / 1000 / 1f;
//            int be1= (int) (getFileSize(file) / 1024);
//
            Log.e("大小", "丢了个雷姆" + be);
//            return;
        } catch (Exception e) {
//            Log.e("大小", "丢了个雷姆" + be);
            e.printStackTrace();
            return;
        }
//        if (w > h || w > ww) {
//            be = (float) (w / ww);
//        } else if (w < h || h > hh) {
//            be = (float) (h / hh);
//        }
//        if (h>hh  && w > ww) be = (float) (w / ww);
        if (be <= 1) {
            return;

        }
        op.inSampleSize = (int) be;// 设置缩放比例,这个数字越大,图片大小越小.
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        bitmap = BitmapFactory.decodeFile(srcPath.getAbsolutePath(), op);
        int desWidth = (int) (w / be);
        int desHeight = (int) (h / be);
        Log.e("大小", "丢了个雷姆" + desHeight+"  "+desWidth);

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

    /**
     * 获取指定文件大小
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

    protected boolean isEmpty(CharSequence str) {
        if (str == null || str.length() == 0) return true;
        else return false;
    }
}
