package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Classify;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SelectClassifyAdapter extends BaseAdapter {

    private ArrayList<Classify> classifies;
    private Context mContext;
//    private int  selectItem=-1;
    int mSelect = 0;

    public SelectClassifyAdapter(Context mContext, ArrayList<Classify> classifies) {
        this.classifies = classifies;
        this.mContext = mContext;
    }

    //刷新Adapter
    public void refresh(ArrayList<Classify> classifies) {
        this.classifies = classifies;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
}


    @Override
    public int getCount() {
        return classifies == null ? 0 : classifies.size();
    }

    @Override
    public Object getItem(int i) {
        return classifies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_classify, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.hint.setVisibility(View.GONE);
        holder.classifyText.setText(classifies.get(i).getProductClassName());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.hint)
        TextView hint;
        @BindView(R.id.classify_text)
        TextView classifyText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
