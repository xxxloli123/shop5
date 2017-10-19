package com.example.xxxloli.zshmerchant.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.fragment.AddTableNumberFragment;
import com.example.xxxloli.zshmerchant.objectmodel.Table;
import com.interfaceconfig.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class TableAdapter extends BaseAdapter {

    private ArrayList<Table> tables;
    private AddTableNumberFragment mContext;

    public TableAdapter(AddTableNumberFragment mContext, ArrayList<Table> tables) {
        this.tables = tables;
        this.mContext = mContext;
    }

    //刷新Adapter
    public void refresh(ArrayList<Table> tables) {
        this.tables = tables;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tables == null ? 0 : tables.size();
    }

    @Override
    public Object getItem(int i) {
        return tables.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext.getActivity()).inflate(R.layout.item_table, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tableNumber.setText("" + tables.get(i).getTableNumber());
        holder.deleteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View view1 = LayoutInflater.from(mContext.getActivity()).inflate(R.layout.dialog_sure, null);
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext.getActivity(), R.style.Theme_AppCompat_DayNight_Dialog).create();
                TextView title = view1.findViewById(R.id.title);
                Button sure = view1.findViewById(R.id.sure_bt);
                Button cancel = view1.findViewById(R.id.cancel_bt);
                title.setText("确认删除吗");
                sure.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mContext.deleteTable(i);
                        alertDialog.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.setView(view1);
                alertDialog.show();
            }
        });
        holder.saveBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 mContext.saveQRcode(i);
            }
        });

        return view;
    }

     static class ViewHolder {
        @BindView(R.id.table_number)
        TextView tableNumber;
        @BindView(R.id.delete_bt)
        Button deleteBt;
        @BindView(R.id.save_bt)
        Button saveBt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
