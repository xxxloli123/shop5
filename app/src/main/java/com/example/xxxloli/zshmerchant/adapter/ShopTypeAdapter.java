package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.ShopType;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class ShopTypeAdapter extends BaseAdapter {

    private ArrayList<ShopType> shopTypes;
    private Context mContext;
//    private int  selectItem=-1;
    int mSelect = 0;

    public ShopTypeAdapter(Context mContext, ArrayList<ShopType> shopTypes) {
        this.shopTypes = shopTypes;
        this.mContext = mContext;
    }

    public void changeSelected(int positon){ //刷新方法
        if(positon != mSelect){
            mSelect = positon;
            notifyDataSetChanged();
        }
    }


    @Override
    public int getCount() {
        return shopTypes == null ? 0 : shopTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return shopTypes.get(i);
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
        holder.classifyText.setText(shopTypes.get(i).getGenericClassName());
        holder.hint.setVisibility(View.GONE);
        holder.classifyText.setBackgroundColor(Color.parseColor("#e9e9e9"));
        if (mSelect == i) {
            holder.classifyText.setBackgroundColor(Color.parseColor("#FAFAFA"));
            holder.hint.setVisibility(View.VISIBLE);
        }
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
