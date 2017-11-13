package com.example.xxxloli.zshmerchant.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.fragment.AddTableNumberFragment;
import com.interfaceconfig.Config;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class ShopQRcodeActivity extends AppCompatActivity {

    @BindView(R.id.back_rl)
    RelativeLayout backRl;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.QR_code)
    ImageView QRCode;
    @BindView(R.id.save_bt)
    Button saveBt;

    private String url;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_qrcode);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        if (intent.getStringExtra("QR_code")==null){
            Toast.makeText(this, "数据读取错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        url=intent.getStringExtra("QR_code");
        Picasso.with(this).load(Config.Url.getUrl(Config.QRCODE)+intent.getStringExtra("QR_code")).into(QRCode);
    }

    @OnClick({R.id.back_rl, R.id.save_bt})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_rl:
                finish();
                break;
            case R.id.save_bt:
                saveQRcode();
                break;
        }
    }
    public void saveQRcode() {
        //加入网络图片地址
        new Task().execute(Config.Url.getUrl(Config.QRCODE)+url);
    }

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x123){
//                QRCode.setImageBitmap(bitmap);

                // 其次把文件插入到系统图库
               saveImageToGallery(ShopQRcodeActivity.this,bitmap);
//                SavaImage(bitmap,Environment.getExternalStorageDirectory().getPath()+"/掌生活","店铺二维码.jpg");
                Toast.makeText(ShopQRcodeActivity.this, "保存成功", Toast.LENGTH_LONG).show();
            }
        };
    };

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片

        File file = new File(getExternalCacheDir(),  "店铺二维码");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 其次把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    file.getAbsolutePath(),  "店铺二维码", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }

    /**
     * 异步线程下载图片
     *
     */
    class Task extends AsyncTask<String, Integer, Void> {

        protected Void doInBackground(String... params) {
            bitmap=GetImageInputStream((String)params[0]);
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Message message=new Message();
            message.what=0x123;
            handler.sendMessage(message);
        }

    }

    /**
     * 获取网络图片
     * @param imageurl 图片网络地址
     * @return Bitmap 返回位图
     */
    public Bitmap GetImageInputStream(String imageurl){
        URL url;
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            url = new URL(imageurl);
            connection=(HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(6000); //超时设置
            connection.setDoInput(true);
            connection.setUseCaches(false); //设置不使用缓存
            InputStream inputStream=connection.getInputStream();
            bitmap= BitmapFactory.decodeStream(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public void SavaImage(Bitmap bitmap, String path,String name){
        File file=new File(path);
        FileOutputStream fileOutputStream=null;
        //文件夹不存在，则创建它
        if(!file.exists()){
            file.mkdir();
        }
        try {
            fileOutputStream=new FileOutputStream(path+"/"+name);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();
            //保存图片后发送广播通知更新数据库

            this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                    Uri.fromFile(new File(file.getParent()))));
//            MediaScannerConnection msc=new MediaScannerConnection(context,new MediaScannerConnection.MediaScannerConnectionClient(){
//                @Override
//                public void onMediaScannerConnected() {
//// TODO Auto-generated method stub
//
//                }
//                @Override
//                public void onScanCompleted(String path, Uri uri) {
//// TODO Auto-generated method stub
//
//                }
//            });
//            msc.connect();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//// TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            URL url = null;
//            try {
//                url = file.toURL();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            }
//
//            MimeTypeMap mtm=MimeTypeMap.getSingleton();
//
//            msc.scanFile(file.toString(), mtm.getMimeTypeFromExtension(mtm.getFileExtensionFromUrl(url.toString())));
//             msc.scanFile(file.getAbsolutePath(), null);
//            msc.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
