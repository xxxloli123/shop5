package com.example.xxxloli.zshmerchant.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.ImageAdapter;
import com.example.xxxloli.zshmerchant.adapter.UploadImgAdapter;
import com.example.xxxloli.zshmerchant.util.FileHelper;
import com.example.xxxloli.zshmerchant.util.SimpleCallback;
import com.example.xxxloli.zshmerchant.util.ToastUtil;
import com.interfaceconfig.Config;
import com.sgrape.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class UploadIMGActivity extends BaseActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.name_ET)
    EditText nameET;
    @BindView(R.id.image)
    RecyclerView image;

    private LinkedList<Uri> imgUris;
    private ImageAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upload_img;
    }

    @Override
    protected void init() {
        image.setLayoutManager(new GridLayoutManager(this,
                6, GridLayoutManager.VERTICAL, false));
        image.setItemAnimator(new DefaultItemAnimator());
        imgUris = new LinkedList<>();
        imgUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.icon_add));
        adapter = new ImageAdapter(this, imgUris, true);
        image.setAdapter(adapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data)
            imgUris.add(imgUris.size() - 1, data.getData());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        imgUris.clear();
        imgUris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.icon_add));
        adapter.notifyDataSetChanged();
        nameET.setText("");
    }

    @OnClick({R.id.back_rl, R.id.save_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.save_bt:
                commit();
                break;
        }
    }

    private void commit() {
        if (imgUris.size() < 1) {
            ToastUtil.showToast(this, "请选择图片");
            return;
        }
        if (isEmpty(nameET.getText().toString())) {
            ToastUtil.showToast(this, "请填写名称");
            return;
        }
//        描述：imgName;;pictures图片
//        参数：[imgName, pictures]
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        requestBody.addFormDataPart("imgName", nameET.getText().toString());
        for (int index = 0; index < imgUris.size() - 1; index++) {//Android okHttp上传单张或多张照片
            // http://blog.csdn.net/sanyang730/article/details/51317083
            String path = FileHelper.toImagePath(this, imgUris.get(index));
            requestBody.addFormDataPart("pictures", path.substring(path.lastIndexOf('/') + 1,
                    path.length()), RequestBody.create(MediaType.parse("image/png"),
                    FileHelper.compressFile(path, 100)));
        }
        Request.Builder request = new Request.Builder().url(Config.Url.getUrl(Config.Upload_Imgs));

        request.method("POST", requestBody.build());
        new OkHttpClient().newCall(request.build()).enqueue(new SimpleCallback(this) {
            @Override
            public void onSuccess(String tag, JSONObject json) throws JSONException {
                Toast.makeText(UploadIMGActivity.this, json.
                        getString("message"), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
