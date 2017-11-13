package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.Activity.ShopQRcodeActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.TableAdapter;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.example.xxxloli.zshmerchant.objectmodel.Table;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;
import com.sgrape.dialog.LoadDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.xxxloli.zshmerchant.app.MyApplication.mContext;

/**
 * Created by Administrator on 2017/9/14.
 */

public class AddTableNumberFragment extends BaseFragment {

    @BindView(R.id.table_number)
    EditText tableNumber;
    @BindView(R.id.sure)
    Button sure;
    @BindView(R.id.add)
    TextView add;
    Unbinder unbinder;
    @BindView(R.id.table_show)
    MyListView tableShow;
    @BindView(R.id.show)
    ImageView show;

    private ArrayList<Table> tables;
    private TableAdapter tableAdapter;
    Bitmap bitmap;
    private DBManagerShop dbManagerShop;
    private Shop shop;
    private LoadDialog dialog;
    private boolean isAdd=false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_table_number;
    }

    @Override
    public void onClick(View view) {
    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        switch (tag.toString()) {
            case Config.GET_TABLE:
                JSONArray arr = json.getJSONArray("listtable");
                if (isAdd)dialog.dismiss();
                if (arr.length() == 0) return;
                tables = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    tables.add(gson.fromJson(arr.getString(i), Table.class));
                }
                if (tableAdapter!=null){
                    tableAdapter.refresh(tables);
                    return;
                }
                tableAdapter = new TableAdapter(this, tables);
                tableShow.setAdapter(tableAdapter);
                break;
            case Config.ADD_TABLES:
            case Config.ADD_TABLE:
            case Config.DELETE_TABLES:
                Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
                initView();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        dbManagerShop = DBManagerShop.getInstance(getContext());
        shop = dbManagerShop.queryById((long) 2333).get(0);
        initView();
        return rootView;
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", shop.getId());
        newCall(Config.Url.getUrl(Config.GET_TABLE), params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.sure, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sure:
                if (isEmpty(tableNumber.getText())) {
                    Toast.makeText(getActivity(), "请输入桌数添加", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialog = new LoadDialog(getActivity());
                dialog.show();
                isAdd=true;
                addTables();
                break;
            case R.id.add:
                addTable();
                break;
        }
    }

    private void addTables() {
        final int tablre_number = Integer.parseInt(String.valueOf(tableNumber.getText()));
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_sure, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView title = view.findViewById(R.id.title);
        Button sure = view.findViewById(R.id.sure_bt);
        Button cancel = view.findViewById(R.id.cancel_bt);
        title.setText("确认添加 " + tablre_number + " 桌吗");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Map<String, Object> params = new HashMap<>();
                params.put("userId", shop.getShopkeeperId());
                params.put("tableNumber", tablre_number);
                newCall(Config.Url.getUrl(Config.ADD_TABLES), params);
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

    private void addTable() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_table_number, null);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
        TextView add = view.findViewById(R.id.add);
        final EditText number = view.findViewById(R.id.edit);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(number.getText())) {
                    Toast.makeText(getActivity(), "请输入桌号添加", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> params = new HashMap<>();
                JSONObject shoptableStr = new JSONObject();
                try {
                    //【shopName 店名, shopId 店铺id, shopkeeperId 店主id, shopkeeperName 店主名称, tableNumber 桌号】
                    shoptableStr.put("shopName", shop.getShopName());
                    shoptableStr.put("shopId", shop.getId());
                    shoptableStr.put("shopkeeperId", shop.getShopkeeperId());
                    shoptableStr.put("shopkeeperName", shop.getShopkeeperName());
                    shoptableStr.put("tableNumber", number.getText().toString());

                    params.put("shoptableStr", shoptableStr);
                    newCall(Config.Url.getUrl(Config.ADD_TABLE), params);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();

    }

    public void deleteTable(int p) {
        Map<String, Object> params = new HashMap<>();
        params.put("shoptableId", tables.get(p).getId());
        params.put("userId", shop.getShopkeeperId());
        newCall(Config.Url.getUrl(Config.DELETE_TABLES), params);
    }

    public void saveQRcode( int p) {
        //加入网络图片地址
        new Task().execute(Config.Url.getUrl(Config.QRCODE)+tables.get(p).getTableCode());
    }

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x123){
//                show.setImageBitmap(bitmap);
                saveImageToGallery(getActivity(),bitmap);
                Toast.makeText(getActivity(), "保存成功" , Toast.LENGTH_LONG).show();
            }
        };
    };

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片

        File file = new File(getActivity().getExternalCacheDir(),  "桌号二维码");
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
                    file.getAbsolutePath(),  "桌号二维码", null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 最后通知图库更新
//        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(file.getPath()))));
    }

    /**
     * 异步线程下载图片
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
            connection.setConnectTimeout(100000); //超时设置
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
            bitmap.compress(Bitmap.CompressFormat.PNG, 100,fileOutputStream);
            fileOutputStream.close();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

}
