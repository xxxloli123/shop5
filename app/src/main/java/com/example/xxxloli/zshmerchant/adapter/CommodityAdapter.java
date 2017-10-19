package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.Activity.QualificationAuthenticationActivity;
import com.example.xxxloli.zshmerchant.Activity.XXRZActivity;
import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Commodity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CommodityAdapter extends BaseAdapter implements View.OnClickListener{

    private ArrayList<Commodity> commodities;
    private Context context;
    private Callback mCallback;

    public CommodityAdapter(Context context, ArrayList<Commodity> commodities,Callback callback) {
        this.commodities = commodities;
        this.context = context;
        mCallback = callback;
    }

    @Override
    public int getCount() {
        return commodities == null ? 0 : commodities.size();
    }

    @Override
    public Object getItem(int i) {
        return commodities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_commodity, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.commodityName.setText(commodities.get(i).getProductName() + "");
        holder.editBt.setOnClickListener(this);
        return view;
    }

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
    static class ViewHolder {
        @BindView(R.id.commodity_img)
        ImageView commodityImg;
        @BindView(R.id.commodity_name)
        TextView commodityName;
        @BindView(R.id.edit_bt)
        Button editBt;
        @BindView(R.id.suspension_of_sale)
        Button suspensionOfSale;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
