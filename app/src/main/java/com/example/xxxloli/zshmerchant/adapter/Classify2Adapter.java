package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/9/15.
 */

public class Classify2Adapter extends RecyclerView.Adapter<Classify2Adapter.ViewHolder> implements View.OnClickListener {

    private LayoutInflater mInflater;
    private ArrayList<Classify> classifies;
    int mSelect = 0;
    private Callback mCallback;
    private Context context;

    //响应按钮点击事件,调用子定义接口，并传入View
    @Override
    public void onClick(View v) {
        mCallback.click(v);
    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     *
     * @author Ivan Xu
     *         2014-11-26
     */
    public interface Callback {
        public void click(View v);
    }

    public Classify2Adapter(Context context, ArrayList<Classify> classifies,Callback callback) {
        mInflater = LayoutInflater.from(context);
        this.classifies = classifies;
        mCallback = callback;
        this.context=context;
    }

    //刷新Adapter
    public void refresh(ArrayList<Classify> classifies) {
        this.classifies = classifies;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    public void changeSelected(int positon) { //刷新方法
        if (positon != mSelect) {
            mSelect = positon;
            notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View arg0) {
            super(arg0);
        }

        TextView classifyName;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_classify2, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.classifyName=view.findViewById(R.id.classify_name);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.classifyName.setText(classifies.get(position).getProductClassName());
        if (mSelect == position) {
            holder.classifyName.setBackgroundResource(R.drawable.line_frame_blue);
            holder.classifyName.setTextColor(context.getResources().getColor(R.color.blue));
        }else {
            holder.classifyName.setTextColor(context.getResources().getColor(R.color.text_color));
            holder.classifyName.setBackgroundResource(R.drawable.line_frame);
        }
        holder.classifyName.setOnClickListener(this);
        holder.classifyName.setTag(position);
    }

    @Override
    public int getItemCount() {
        return classifies.size();
    }
}
