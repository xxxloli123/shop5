package com.example.xxxloli.zshmerchant.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.xxxloli.zshmerchant.Activity.UploadIMGActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.util.ToastUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 2017/10/20.
 * 分享,意见反馈
 */

public class ImageAdapter extends RecyclerView.Adapter {
    private Activity activity;
    private List<Uri> uris;
    private boolean isClick;

    public ImageAdapter(Activity activity, List<Uri> uris, boolean isClick) {
        this.activity = activity;
        this.uris = uris;
        this.isClick = isClick;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHv(LayoutInflater.from(activity).inflate(R.layout.image,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(activity).load(uris.get(position)).into(((ImageHv)holder).image);
        ((ImageHv)holder).image.setId(position);
    }

    @Override
    public int getItemCount() {
        return null == uris ? 0 : uris.size();
    }
    private class ImageHv extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        public ImageHv(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            if(isClick)
                image.setOnClickListener(this);
        }

        @Override
        public void onClick(final View v) {
            Log.d("ImageAdapter","total>"+uris.size()+" id>"+v.getId());
            if (uris.size() > 6){
                ToastUtil.showToast(activity, "最多只能上传六张图片");
                return;
            }
            if(uris.size() -1 == v.getId()){
                Intent local;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                    local = new Intent(Intent.ACTION_GET_CONTENT);
                    local.setType("image/*");
                }else
                    local = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activity.startActivityForResult(local, 1);
            }else
                new AlertDialog.Builder(activity).setTitle("确定删除").setMessage("是否删除!").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        uris.remove(v.getId());
                        notifyItemRemoved(v.getId());
                        notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
        }
    }
}
