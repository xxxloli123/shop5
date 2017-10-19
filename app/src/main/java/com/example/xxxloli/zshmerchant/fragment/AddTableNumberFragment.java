package com.example.xxxloli.zshmerchant.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.adapter.TableAdapter;
import com.example.xxxloli.zshmerchant.objectmodel.Table;
import com.example.xxxloli.zshmerchant.view.MyListView;
import com.google.gson.Gson;
import com.interfaceconfig.Config;
import com.sgrape.BaseFragment;

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_add_table_number;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onSuccess(Object tag, JSONObject json) throws JSONException {
        Toast.makeText(getActivity(), json.getString("message"), Toast.LENGTH_SHORT).show();
        switch (tag.toString()) {
            case Config.GET_TABLE:
                JSONArray arr = json.getJSONArray("listtable");
                if (arr.length() == 0) return;
                tables = new ArrayList<>();
                Gson gson = new Gson();
                for (int i = 0; i < arr.length(); i++) {
                    tables.add(gson.fromJson(arr.getString(i), Table.class));
                }
                tableAdapter = new TableAdapter(this, tables);
                tableShow.setAdapter(tableAdapter);
                tableAdapter.refresh(tables);
                break;
            case Config.ADD_TABLES:
            case Config.ADD_TABLE:
            case Config.DELETE_TABLES:
                initView();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        initView();

        return rootView;
    }

    private void initView() {
        Map<String, Object> params = new HashMap<>();
        params.put("shopId", "402880e75f000ab6015f0043a1fc0004");
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
                params.put("userId", "402880e75f000ab6015f0043a1210002");
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
                    shoptableStr.put("shopName", "asdhfkjhasd");
                    shoptableStr.put("shopId", "402880e75f000ab6015f0043a1fc0004");
                    shoptableStr.put("shopkeeperId", "402880e75f000ab6015f0043a1210002");
                    shoptableStr.put("shopkeeperName", "梁非凡");
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
        params.put("userId", "402880e75f000ab6015f0043a1210002");
        newCall(Config.Url.getUrl(Config.DELETE_TABLES), params);
    }

    public void saveQRcode(final int p) {
        //加入网络图片地址
        new Task().execute(Config.Url.getUrl(Config.QRCODE)+tables.get(p).getTableCode());
        SavaImage(bitmap, Environment.getExternalStorageDirectory().getPath()+"/掌生活/二维码图片",tables.get(p).getTableNumber()+".png");
        Toast.makeText(getActivity(), "图片保存到    "+Environment.getExternalStorageDirectory().getPath()+"/掌生活/二维码图片"
                , Toast.LENGTH_LONG).show();
    }

    Handler handler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0x123){
                show.setImageBitmap(bitmap);
            }
        };
    };


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
