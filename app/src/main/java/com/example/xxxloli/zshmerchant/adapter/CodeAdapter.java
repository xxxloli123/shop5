package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.ExpenseCode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class CodeAdapter extends BaseAdapter {

    private ArrayList<ExpenseCode> codes;
    private Context mContext;

    public CodeAdapter(Context mContext, ArrayList<ExpenseCode> codes) {
        this.codes = codes;
        this.mContext = mContext;
    }

    //刷新Adapter
    public void refresh(ArrayList<ExpenseCode> codes) {
        this.codes = codes;//传入list，然后调用notifyDataSetChanged方法
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return codes == null ? 0 : codes.size();
    }

    @Override
    public Object getItem(int i) {
        return codes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_code, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (i == codes.size() - 1) {
            holder.code.setVisibility(View.GONE);
            holder.addImg.setVisibility(View.VISIBLE);
        } else {
            holder.code.setVisibility(View.VISIBLE);
            holder.addImg.setVisibility(View.GONE);
        }
        holder.code.setText("" + codes.get(i).getConsumeCode());
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.code)
        TextView code;
        @BindView(R.id.add_img)
        TextView addImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
