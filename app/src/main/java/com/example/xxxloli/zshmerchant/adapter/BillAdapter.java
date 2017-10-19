package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.BillCommodity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class BillAdapter extends BaseAdapter {

    private ArrayList<BillCommodity> billCommodities;
    private Context context;

    public BillAdapter(Context context, ArrayList<BillCommodity> billCommodities) {
        this.billCommodities = billCommodities;
        this.context = context;
    }


    @Override
    public int getCount() {
        return billCommodities == null ? 0 : billCommodities.size();
    }

    @Override
    public Object getItem(int i) {
        return billCommodities.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_bill, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(billCommodities.get(i).getGoodsName());
        holder.quantityText.setText("x"+billCommodities.get(i).getGoodsnum());
        holder.priceText.setText(billCommodities.get(i).getGoodsPrice()+"");
        return view;
    }

    static class ViewHolder {

        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.quantity_text)
        TextView quantityText;
        @BindView(R.id.price_text)
        TextView priceText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
