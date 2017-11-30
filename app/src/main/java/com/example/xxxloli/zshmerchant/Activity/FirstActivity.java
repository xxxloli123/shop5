package com.example.xxxloli.zshmerchant.Activity;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.MainActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.DaoUtil;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.interfaceconfig.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstActivity extends AppCompatActivity {

    @BindView(R.id.open_shop)
    Button openShop;
    @BindView(R.id.login)
    Button login;

    private static String PERMISSIONS[] = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            if(lacksPermissions(PERMISSIONS))
                ActivityCompat.requestPermissions(this,PERMISSIONS,PERMISSIONS.length);
            else
                setResult(PERMISSIONS.length);
        }
        if (!DaoUtil.isSaveShop(this)&&DaoUtil.isSaveUser(this))
            startActivity(new Intent(FirstActivity.this, XXRZActivity.class));
            if (DaoUtil.isSaveShop(this)) {
                startActivity(new Intent(FirstActivity.this, MainActivity.class));
                finish();
            }else update(this,getVersionCode(this));
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void update(final Context context, final int versionCode) {
        Request req = new Request.Builder()
                .tag("")
                .url(Config.Url.getUrl(Config.UPDATE)).build();
        new OkHttpClient().newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    int ver = json.getJSONObject("appVersion").getInt("version");
                    Log.e("versionCode","丢了个雷姆  "+versionCode+"  ver"+ver+"json"+json);
                    if (ver > versionCode) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "检测到新的版本，自动为您下载。。。", Toast.LENGTH_SHORT).show();
                                //创建下载任务,downloadUrl就是下载链接
                                DownloadManager.Request request = new DownloadManager.Request(
                                        Uri.parse(Config.Url.getUrl("/slowlife/share/appdownload?type=android_zsh_shop")));
                                //指定下载路径和下载文件名
                                request.setDestinationInExternalPublicDir("/sdcard/Android/" + context.getPackageName(), "掌升活(商家端).apk");
                                request.setDescription("掌升活(商家端)新版本下载");
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setMimeType("application/vnd.android.package-archive");
                                // 设置为可被媒体扫描器找到
                                request.allowScanningByMediaScanner();
                                // 设置为可见和可管理
                                request.setVisibleInDownloadsUi(true);
                                //获取下载管理器
                                DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                                //将下载任务加入下载队列，否则不会进行下载
                                downloadManager.enqueue(request);
                            }
                        });
                    }
//                    else {
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context, "已安装最新版", Toast.LENGTH_SHORT).show();
//                            }
//                        });
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    private static Handler handler = new Handler();

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions)
            if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_DENIED)// 判断是否缺少权限
                return true;
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean bool = false;
        for(int result : grantResults)
            if(result != PackageManager.PERMISSION_GRANTED) {
                bool = true;
                break;
            }
        if(requestCode == permissions.length && bool){
            Log.d("AdActivity", "--onRequestPermissionsResult>>>权限没开完");
            Toast.makeText(this, "请开启必要的权限", Toast.LENGTH_SHORT).show();
            finish();
        }else setResult(PERMISSIONS.length);
    }

    @OnClick({R.id.open_shop, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.open_shop:
                startActivity(new Intent(FirstActivity.this, OpenShopActivity.class));
                break;
            case R.id.login:
                startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                break;
        }
    }
}
