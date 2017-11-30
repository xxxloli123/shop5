package com.example.xxxloli.zshmerchant.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.greendao.DBManagerShop;
import com.example.xxxloli.zshmerchant.greendao.Shop;
import com.interfaceconfig.Config;
import com.slowlife.lib.MD5;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/10/26.
 */

public class PersonnelManageAdapter extends RecyclerView.Adapter {
    private String type;
    private Activity activity;
    private List<String[]> datas;
    private DBManagerShop dbManagerShop;
    private Shop shop;

    public PersonnelManageAdapter(Activity activity, String type, List<String[]> datas) {
        this.activity = activity;
        this.type = type;
        this.datas = datas;
        dbManagerShop = DBManagerShop.getInstance(activity);
        shop = dbManagerShop.queryById((long) 2333).get(0);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PmViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_personnel_manage ,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(1 < datas.get(position).length) {
            ((PmViewHolder) holder).photo.setVisibility(View.VISIBLE);
            ((PmViewHolder) holder).name.setVisibility(View.VISIBLE);
            ((PmViewHolder) holder).phone.setVisibility(View.VISIBLE);
            ((PmViewHolder) holder).disable.setVisibility(View.VISIBLE);
            ((PmViewHolder) holder).delete.setVisibility(View.VISIBLE);
            ((PmViewHolder) holder).add.setVisibility(View.GONE);
            ((PmViewHolder) holder).name.setText(datas.get(position)[0]);
            ((PmViewHolder) holder).phone.setText(datas.get(position)[1]);
            ((PmViewHolder) holder).disable.setText(activity.getString(datas.get(position)[3].equals("Normal")? R.string.disable : R.string.enabled));
            ((PmViewHolder) holder).disable.setTag(position);
            ((PmViewHolder) holder).delete.setTag(position);
            ((PmViewHolder) holder).disable.setOnClickListener(((PmViewHolder) holder).listener);
            ((PmViewHolder) holder).delete.setOnClickListener(((PmViewHolder) holder).listener);
        }else{
            ((PmViewHolder) holder).photo.setVisibility(View.GONE);
            ((PmViewHolder) holder).name.setVisibility(View.GONE);
            ((PmViewHolder) holder).phone.setVisibility(View.GONE);
            ((PmViewHolder) holder).disable.setVisibility(View.GONE);
            ((PmViewHolder) holder).delete.setVisibility(View.GONE);
            ((PmViewHolder) holder).add.setVisibility(View.VISIBLE);
            ((PmViewHolder) holder).add.setOnClickListener(((PmViewHolder) holder).listener);
        }
    }

    @Override
    public int getItemCount() {
        return null == datas? 0 :datas.size();
    }
    private class PmViewHolder extends RecyclerView.ViewHolder{
        ImageView photo;
        TextView name,phone,disable,delete,add;
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.text_addPersonnel:
                        final LinearLayout linear = new LinearLayout(activity);
                        linear.setOrientation(LinearLayout.VERTICAL);
                        linear.setBackgroundResource(R.color.background);
                        linear.addView(setEditText(R.string.inName,false));
                        linear.addView(setEditText(R.string.inPhone,true));
                        linear.addView(setEditText(R.string.inPassword,false));
                        setMargin(linear.getChildAt(1));
                        setMargin(linear.getChildAt(2));
                        new AlertDialog.Builder(activity).setTitle(activity.getString(R.string.addPersonnel)).setView(linear).setPositiveButton(activity.getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for(int i = 0;i<linear.getChildCount();i++)
                                    if(((EditText)linear.getChildAt(i)).getText().toString().trim().equals(""))
                                        return;
                                String[] info = new String[]{((EditText) linear.getChildAt(0)).getText().toString().trim(), ((EditText) linear.getChildAt(1)).getText().toString().trim(), ((EditText) linear.getChildAt(2)).getText().toString().trim(), "Normal"};
                                linkNet(Config.addStaff,datas.size() - 1,info,new MultipartBody.Builder().setType(MultipartBody.FORM).
                                        addFormDataPart("shopkeeperId", shop.getShopkeeperId()).
                                        addFormDataPart("shopId",shop.getId()).
                                        addFormDataPart("userName",info[0]).addFormDataPart("phone",info[1]).
                                        addFormDataPart("password", MD5.md5Pwd(info[2])).
                                        addFormDataPart("type",type));
                            }
                        }).setNegativeButton(activity.getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        }).show();
                        break;
                    case R.id.text_disable:
                        String state = datas.get((int)v.getTag())[3].equals("Stop")? "Normal" : "Stop";
                        linkNet(Config.alterStaff,(int)v.getTag(),new String[]{state},new MultipartBody.Builder().setType(MultipartBody.FORM).
                                addFormDataPart("shopkeeperId", shop.getShopkeeperId()).
                                addFormDataPart("shopId",shop.getId()).
                                addFormDataPart("userId",datas.get((int)v.getTag())[2]).addFormDataPart("status",state).
                                addFormDataPart("type","update"));
                        break;
                    case R.id.text_delete:
                        linkNet(Config.alterStaff,(int)v.getTag(),new String[]{"delete"},new MultipartBody.Builder().setType(MultipartBody.FORM).
                                addFormDataPart("shopkeeperId", shop.getShopkeeperId()).
                                addFormDataPart("shopId",shop.getId()).
                                addFormDataPart("userId",datas.get((int)v.getTag())[2]).addFormDataPart("status",datas.get((int)v.getTag())[3]).
                                addFormDataPart("type","delete"));
                        break;
                }
            }
        };
        public PmViewHolder(View itemView) {
            super(itemView);
            photo = itemView.findViewById(R.id.image_photo);
            name = itemView.findViewById(R.id.text_name);
            phone = itemView.findViewById(R.id.text_phone);
            disable = itemView.findViewById(R.id.text_disable);
            delete = itemView.findViewById(R.id.text_delete);
            add = itemView.findViewById(R.id.text_addPersonnel);
            disable.setOnClickListener(listener);
            delete.setOnClickListener(listener);
            add.setOnClickListener(listener);
        }
        private EditText setEditText(@StringRes int stringResId, boolean isDigit){
            EditText editText = new EditText(activity);
            if(isDigit){
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
            }
            //editText.setTransformationMethod(PasswordTransformationMethod.getInstance());//设密码
            editText.setTop((int)activity.getResources().getDimension(R.dimen.line1));
            editText.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
            editText.setPadding((int)activity.getResources().getDimension(R.dimen.margin16),0,0,0);
            editText.setHintTextColor(ContextCompat.getColor(activity, R.color.gray));
            editText.setHint(activity.getString(stringResId));
            return editText;
        }
        private void setMargin(View view){
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
            int divider = (int)activity.getResources().getDimension(R.dimen.line1);
            params.setMargins(0,divider,0,divider);
            view.setLayoutParams(params);
        }
        private void linkNet(String url,final int position,final String[] info,MultipartBody.Builder requestBody){
            Request.Builder request = new Request.Builder().url(Config.Url.getUrl(url));
            request.method("POST", requestBody.build());
            new OkHttpClient().newCall(request.build()).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(activity, "上传失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final int responseCode = response.code();
                    final String responseResult = response.body().string();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (responseCode != 200) {
                                Toast.makeText(activity, "上传失败", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Log.e("responseResult",""+responseResult);
                            try {
                                JSONObject result = new JSONObject(responseResult);
                                System.out.println(result.toString());
                                if (result.getInt("statusCode") != 200) {
                                    Toast.makeText(activity, "上传失败" + result.getJSONObject("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    if("delete".equals(info[0])) {
                                        notifyItemRemoved(position);
                                        datas.remove(position);
                                    }else if("Normal".equals(info[0]) || "Stop".equals(info[0]))
                                        datas.get(position)[3] = info[0];
                                    else
                                        datas.add(position, info);
                                    notifyItemChanged(position);
                                    Toast.makeText(activity, "上传成功", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(activity, "" + responseResult, Toast.LENGTH_SHORT).show();
                                Toast.makeText(activity, "解析数据失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
        }
    }
}
