package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class SelectDateAdapter extends BaseAdapter {

    private String dates[];
    private Context context;

    public SelectDateAdapter(Context context, String dates[]) {
        this.dates = dates;
        this.context = context;
    }


    @Override
    public int getCount() {
        return dates == null ? 0 : dates.length;
    }

    @Override
    public Object getItem(int i) {
        return dates[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_date, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemText.setText(dates[i]);
        return view;
    }


    static class ViewHolder {
        @BindView(R.id.item_text)
        TextView itemText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
