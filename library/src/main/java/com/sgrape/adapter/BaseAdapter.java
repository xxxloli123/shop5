package com.sgrape.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by sgrape on 2017/5/23.
 * e-mail: sgrape1153@gmail.com
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    protected List<T> list;
    protected LayoutInflater inflater;

    public BaseAdapter(Context context, List<T> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    public BaseAdapter(Context context) {
        this(context, null);
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            if (getLayoutId() <= 0) {
                view = getView();
            } else {
                view = inflater.inflate(getLayoutId(), null, false);
            }
            VH vh = newViewHolder(view);
            if (vh != null) view.setTag(newViewHolder(view));
        }
        setData(view, list.get(position),position);
        return view;
    }

    protected View getView() {
        return null;
    }

    protected abstract void setData(View view, T t,int position);

    protected abstract VH newViewHolder(View v);

    protected abstract
    @LayoutRes
    int getLayoutId();

    public void notifyDataSetChanged(List<T> list) {
        this.list = list;
        super.notifyDataSetChanged();
    }

    public List<T> getData() {
        return list;
    }

    public static class VH {
        public VH(View v) {
            ButterKnife.bind(this, v);
        }
    }
}
