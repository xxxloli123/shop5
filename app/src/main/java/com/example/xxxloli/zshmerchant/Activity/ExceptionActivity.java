package com.example.xxxloli.zshmerchant.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.ImageAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.util.FileHelper;
import com.interfaceconfig.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.LinkedList;

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

/**
 * Created by Administrator on 2017/10/20.
 */

public class ExceptionActivity extends AppCompatActivity {
    @BindView(R.id.text_sumbit)
    TextView sumbit;
    @BindView(R.id.edit_feedback)
    EditText feedback;
    @BindView(R.id.recycler_images)
    RecyclerView images;
    private ImageAdapter adapter;
    private LinkedList<Uri> uris;

    private DBManagerShop dbManagerShop;
    private Shop shop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exception);
        ButterKnife.bind(this);

        dbManagerShop = DBManagerShop.getInstance(this);
        shop = dbManagerShop.queryById((long) 2333).get(0);

        ((TextView) findViewById(R.id.text_title)).setText(getString(getIntent().getIntExtra(getString(R.string.title), R.string.terraceFunctionException)));
        images.setLayoutManager(new GridLayoutManager(this, 6, GridLayoutManager.VERTICAL, false));
        images.setItemAnimator(new DefaultItemAnimator());
        uris = new LinkedList<>();
        uris.add(Uri.parse("android.resource://" + getPackageName() + "/" + R.drawable.icon_add));
        adapter = new ImageAdapter(this, uris, true);
        images.setAdapter(adapter);
    }

    @OnClick({ R.id.text_sumbit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_sumbit:
//                商家添加意见反馈opinionStr{title:标题;opinionContent:内容;type:[	FunctionException(平台功能异常 ),
//                    DistributionProblem配送问题,ProductSuggestion(产品建议)];userId:反馈者id;userName:反馈者姓名;userPhone:
//            反馈者电话};;pictures图片 参数：[opinionStr, pictures]
                Request.Builder request = new Request.Builder().url(Config.Url.getUrl(Config.platformException));
                StringBuilder jsonSb = new StringBuilder("{\"type\":\"" + getString(getIntent().
                        getIntExtra(getString(R.string.txceptionType), R.string.functionException)));
                jsonSb.append("\",\"title\":\"").append(getString(getIntent().getIntExtra(getString(R.string.title), R.string.terraceFunctionException))).
                        append("\",\"opinionContent\":\"").append(feedback.getText().toString().trim()).
                        append("\",\"userName\":\"").append(shop.getShopkeeperName()).
                        append("\",\"userPhone\":\"").append(shop.getShopkeeperPhone()).
                        append("\",\"userId\":\"").append(shop.getShopkeeperId()).append("\"}");
                Log.i("ExceptionActivity", "json->" + jsonSb.toString());
                MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
                requestBody.addFormDataPart("opinionStr", jsonSb.toString());
                for (int index = 0; index < uris.size() - 1; index++) {//Android okHttp上传单张或多张照片 http://blog.csdn.net/sanyang730/article/details/51317083
                    String path = FileHelper.toImagePath(this, uris.get(index));
                    requestBody.addFormDataPart("pictures", path.substring(path.lastIndexOf('/') + 1, path.length()), RequestBody.create(MediaType.parse("image/png"), FileHelper.compressFile(path, 100)));
                }
                request.method("POST", requestBody.build());
                new OkHttpClient().newCall(request.build()).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ExceptionActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
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
                                    Toast.makeText(ExceptionActivity.this, "上传失败", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                try {
                                    JSONObject result = new JSONObject(responseResult);
                                    System.out.println(result.toString());
                                    if (result.getInt("statusCode") != 200) {
                                        Toast.makeText(ExceptionActivity.this, "上传失败" + result.getJSONObject("message"), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(ExceptionActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(ExceptionActivity.this, "解析数据失败", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data)
            uris.add(uris.size() - 1, data.getData());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.back_rl)
    public void onViewClicked() {
        finish();
    }
}
