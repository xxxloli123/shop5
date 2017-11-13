package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.BillCommodity;
import com.example.xxxloli.zshmerchant.objectmodel.CheckBill;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CheckBillAdapter extends BaseAdapter {

    private ArrayList<CheckBill> checkBills;
    private Context context;

    public CheckBillAdapter(Context context, ArrayList<CheckBill> checkBills) {
        this.checkBills = checkBills;
        this.context = context;
    }

    //刷新Adapter
    public void refresh(ArrayList<CheckBill> checkBills) {
        this.checkBills = checkBills;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return checkBills == null ? 0 : checkBills.size();
    }

    @Override
    public Object getItem(int i) {
        return checkBills.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_check_bill, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.typeTv.setText(checkBills.get(i).getType_value());
        holder.moneyTv.setText(checkBills.get(i).getCost()+"");
        holder.timeTv.setText(checkBills.get(i).getCreateDate()+"");
        holder.statusTv.setText(checkBills.get(i).getStatus_value()+"");
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.type_Tv)
        TextView typeTv;
        @BindView(R.id.money_Tv)
        TextView moneyTv;
        @BindView(R.id.time_Tv)
        TextView timeTv;
        @BindView(R.id.status_Tv)
        TextView statusTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
