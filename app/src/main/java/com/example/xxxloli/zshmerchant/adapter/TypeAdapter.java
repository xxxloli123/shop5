package com.example.xxxloli.zshmerchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.xxxloli.zshmerchant.R;
import com.example.xxxloli.zshmerchant.objectmodel.Type;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/9/15.
 */

public class TypeAdapter extends BaseAdapter {

    private ArrayList<Type> types;
    private Context context;

    public TypeAdapter(Context context, ArrayList<Type> types) {
        this.types = types;
        this.context = context;
    }

    @Override
    public int getCount() {
        return types == null ? 0 : types.size();
    }

    @Override
    public Object getItem(int i) {
        return types.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_type, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.itemText.setText(types.get(i).getGenericClassName());
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
